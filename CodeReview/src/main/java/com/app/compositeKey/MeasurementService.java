package com.app.compositeKey;

import java.time.Instant;
import java.util.*;

/**
 * Сервис принимает измерения температуры от устройств.
 *
 * Требования:
 * - Измерение определяется парой deviceId + measurementId. Повторная отправка идентичного измерения ничего не меняет.
 * - Последнее измерение устройства определяется по максимальному measuredAt: сообщения могут приходить с задержкой.
 * - Получение истории не должно позволять вызывающему коду изменять состояние сервиса.
 * Для этого задания все поля заполнены, идентификаторы могут содержать любые символы.
 * Сервис используется одним потоком, хранение только в памяти допустимо.
 * При одинаковом measuredAt можно вернуть любое из соответствующих измерений.
 */

public class MeasurementService {

    public record Measurement(
            String deviceId,
            String measurementId,
            Instant measuredAt,
            double temperature
    ) {}

    private final Map<String, Measurement> measurements = new HashMap<>(); // Map<MeasurementKey, Measurement>
    private final List<Measurement> history = new ArrayList<>();

    // Склеивание идентификаторов через ":" может создавать одинаковые ключи
    // для разных пар. Из-за этого новое измерение будет отброшено как дубликат.
    // Предлагаю использовать составной ключ MeasurementKey.
    public void accept(Measurement measurement) {
        String key = measurement.deviceId()
                + ":" + measurement.measurementId(); // WARNING. FIX record MeasurementKey(String deviceId, String measurementId) {}

        if (measurements.containsKey(key)) {
            return;
        }

        measurements.put(key, measurement);
        history.add(measurement);
    }

    public Optional<Measurement> findLatest(String deviceId) {
        Measurement latest = null;

        // == сравнивает ссылки, а нам нужно сравнить содержимое идентификаторов.
        // Например, одинаковые идентификаторы могут прийти в разных объектах
        // Из-за этого findLatest() может вернуть Optional.empty(), хотя измерения устройства есть.
        for (Measurement measurement : history) {
            // аждое следующее измерение устройства перезаписывает latest. Получается последнее по порядку добавления,
            // а нужно последнее по времени измерения measuredAt
            if (measurement.deviceId() == deviceId) { // WARNING. FIX measurement.deviceId().equals(deviceId
                latest = measurement;
            }
//            FIX :
//            if (measurement.deviceId().equals(deviceId)
//                    && (latest == null
//                    || measurement.measuredAt().isAfter(latest.measuredAt()))) {
//                latest = measurement;
//            }
        }

        return Optional.ofNullable(latest);
    }

    // history очистится, а measurements останется заполненной. findLatest() перестанет находить измерения,
    // но повторная отправка уже принятого измерения будет отброшена как дубликат.
    public List<Measurement> getHistory() {
        return history;
    }

//    FIX:
//    public List<Measurement> getHistory() {
//        return List.copyOf(history);
//    }
//
//    Альтернатива — изменяемая копия:
//     return new ArrayList<>(history);
//
//    Её можно очищать и дополнять, но это не повлияет на список сервиса.
//    Оба варианта удовлетворяют нашим требованиям;
//    List.copyOf() дополнительно явно запрещает изменение результата.
//    Вызывающий код получит неизменяемый снимок списка:
//    var result = service.getHistory();
//    result.clear(); // UnsupportedOperationException
}

/**
 * HashMap сравнивает ключи с помощью hashCode() и equals().
 * У String оба метода уже реализованы по содержимому,
 * поэтому две разные строки с одинаковым текстом
 * считаются одинаковыми ключами
 */

/**
 * разные пары идентификаторов могут превратиться в одинаковый строковый ключ,
 * потому что ":" разрешён внутри самих идентификаторов.
 *
 * Посмотри на две разные пары:
 * 	deviceId	measurementId	Получившийся ключ
 * Первое измерение	"sensor:1"	"25"	"sensor:1:25"
 * Второе измерение	"sensor"	"1:25"	"sensor:1:25"
 *
 *
 * Исходные пары разные, но после склеивания границы между полями потерялись.
 *
 * В нашем методе:
 * if (measurements.containsKey(key)) {
 *     return;
 * }
 *
 * После сохранения первого измерения второе будет ошибочно принято за повтор и потеряется.
 *
 * Это не коллизия хеш-кодов: здесь сами ключи одинаковы по equals(). HashMap работает правильно — ошибка
 * в способе формирования ключа.
 * Как исправить
 * Хранить пару полей как отдельный объект:
 * record MeasurementKey(String deviceId, String measurementId) {}
 *
 * private final Map<MeasurementKey, Measurement> measurements =
 *         new HashMap<>();
 *
 * Тогда в accept():
 * MeasurementKey key = new MeasurementKey(
 *         measurement.deviceId(),
 *         measurement.measurementId()
 * );
 *
 * if (measurements.containsKey(key)) {
 *     return;
 * }
 *
 * measurements.put(key, measurement);
 * history.add(measurement);
 */

/**
 * на строковых литералах ошибка может скрываться, поскольку Java использует пул строк:
 * String first = "sensor-1";
 * String second = "sensor-1";
 *
 * first == second; // true
 */


/**
 * В историю последовательно поступили два измерения одного устройства:
 * 1. Измерение за 12:10.
 * 2. Измерение за 12:05, пришедшее с задержкой.
 * Какое из них вернёт текущий findLatest() и соответствует ли это требованиям?
 *
 * Метод вернёт 12:05, потому что каждое следующее измерение устройства перезаписывает
 * latest. Получается последнее по порядку добавления, а нужно последнее по времени измерения measuredAt
 */

/**
 * Теперь рассмотрим другой метод:
 * public List<Measurement> getHistory() {
 *     return history;
 * }
 *
 * Что произойдёт с внутренней историей сервиса, если вызывающий код выполнит:
 * service.getHistory().clear();
 *
 * Спасает ли нас то, что поле history объявлено как private final?
 *
 * private final не спасает — внутренняя история очистится.
 * List<Measurement> result = service.getHistory();
 * result.clear();
 *
 * result и поле history ссылаются на один и тот же список. Метод не создаёт копию, а отдаёт ссылку на внутренний объект.
 *
 * Что означают модификаторы:
 * - private запрещает прямой доступ service.history, но метод может раскрыть ссылку на этот объект.
 * - final запрещает заменить ссылку в поле, но не запрещает менять сам список.
 * history = new ArrayList<>(); // нельзя: поле final
 *
 * history.add(measurement);   // можно
 * history.clear();            // можно
 *
 *
 * history очистится, а measurements останется заполненной. findLatest() перестанет находить измерения,
 * но повторная отправка уже принятого измерения будет отброшена как дубликат.
 */

/**
 * snapshot — снимок состава списка на момент копирования. Новые измерения появятся в history, но не в уже полученном снимке.
 * service.accept(first);
 *
 * var snapshot = service.getHistory(); // List.copyOf(history)
 *
 * service.accept(second);
 *
 * snapshot.size();             // 1
 * service.getHistory().size(); // 2
 *
 * Полезно отличать три варианта:
 * Что возвращаем	                        Можно менять результат?          Видны последующие добавления в history?
 * new ArrayList<>(history)	                Да, независимо от оригинала	                    Нет
 *
* List.copyOf(history)	                             Нет	                                Нет
 *
 * Collections.unmodifiableList(history)	Нет через эту обёртку	                        Да
 *
 * Последний вариант — неизменяемое представление исходного списка, а не независимый снимок.
 */
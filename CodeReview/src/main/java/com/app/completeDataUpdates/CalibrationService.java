package com.app.completeDataUpdates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Задание №2: пакетная калибровка датчиков
 * Время: примерно 15–25 минут. Java Core, без Spring.
 *
 * В системе измерений оператор может задать температурные поправки сразу для нескольких датчиков.
 * Например, поправка -0.5 °C превращает показание 23.0 °C в 22.5 °C.
 *
 * Требования:
 * - При создании сервиса передаются идентификаторы зарегистрированных датчиков. Начальная поправка каждого — 0.0 °C.
 * - Поправка должна быть конечным числом в диапазоне [-10.0; 10.0] включительно.
 * - Пакет применяется целиком: если хотя бы одно обновление недопустимо, метод выбрасывает IllegalArgumentException,
 * а все прежние поправки сохраняются.
 * - Обновлять поправку и рассчитывать температуру можно только для зарегистрированного датчика.
 * Иначе — IllegalArgumentException.
 * - Если датчик встречается в допустимом пакете несколько раз, используется последняя указанная поправка.
 * - Пустой пакет ничего не меняет.
 * Предположения: Java 17+, один поток; аргументы, элементы коллекций и поля записей не бывают null;
 * идентификаторы непустые и уникальны в исходном наборе. rawTemperature — конечное число в диапазоне [-100; 200].
 * Хранение в памяти допустимо.
 */

public class CalibrationService {

    public record CalibrationUpdate(
            String deviceId,
            double offset
    ) {}

    private final Map<String, Double> offsets = new HashMap<>();

    public CalibrationService(Set<String> registeredDeviceIds) {
        for (String deviceId : registeredDeviceIds) {
            offsets.put(deviceId, 0.0);
        }
    }

    public void updateCalibrations(List<CalibrationUpdate> updates) {
        for (CalibrationUpdate update : updates) {
            if (!offsets.containsKey(update.deviceId())) {
                throw new IllegalArgumentException(
                        "Unknown device: " + update.deviceId()
                );
            }

            // У double кроме обычных чисел есть NaN — «не число».
            // Сравнения NaN < -10.0 и NaN > 10.0 оба дают false.
            if (update.offset() < -10.0 || update.offset() > 10.0) {
                throw new IllegalArgumentException(
                        "Invalid offset: " + update.offset()
                );
            }

            // FIX
//            double offset = update.offset();
//            if (!Double.isFinite(offset) || offset < -10.0 || offset > 10.0) {
//                throw new IllegalArgumentException("Invalid offset: " + offset);
//            }

            offsets.put(update.deviceId(), update.offset());
        }
    }

    public double calibratedTemperature(
            String deviceId,
            double rawTemperature
    ) {
        // сервис рассчитает температуру для незарегистрированного датчика,
        // хотя по требованиям должен выбросить IllegalArgumentException.
        double offset = offsets.getOrDefault(deviceId, 0.0);
        // FIX
//        if (!offsets.containsKey(deviceId)) {
//            throw new IllegalArgumentException("Unknown device: " + deviceId);
//        }
//        return rawTemperature + offsets.get(deviceId);
        return rawTemperature + offset;
    }
}

/**
 * Сравни требование «пакет применяется целиком» с тем, когда меняется offsets.
 *
 * В сервисе зарегистрированы два датчика:
 * offsets = { "sensor-A": 0.0, "sensor-B": 0.0 }
 *
 * Приходит пакет:
 * List.of(
 *     new CalibrationUpdate("sensor-A", 2.0),
 *     new CalibrationUpdate("sensor-B", 15.0)
 * )
 *
 * Выполнение идёт последовательно:
 * 1. Для sensor-A поправка 2.0 проходит проверки. Выполняется:offsets.put("sensor-A", 2.0);
 * 2. Для sensor-B поправка 15.0 выходит за допустимый диапазон. Метод выбрасывает исключение.
 * Исключение само по себе не отменяет изменения, сделанные ранее.
 *
 * Попробуй ответить на два вопроса:
 * - Какая поправка останется у sensor-A после исключения и какая должна остаться по требованиям?
 * - Как организовать проверки и сохранение, чтобы ошибка во втором элементе не оставляла первый уже применённым?
 */

/**
 * double offset = offsets.getOrDefault(deviceId, 0.0);
 *
 * Что вернёт calibratedTemperature("unknown-device", 25.0) и что должно произойти по требованиям?
 *
 * Не найдет в мапе ничего,потому что ключа такого нет
 *
 * Да, такого ключа в мапе нет. Но getOrDefault(deviceId, 0.0) в этом случае вернёт значение по умолчанию — 0.0.
 *
 * Поэтому метод продолжит работу:
 * return rawTemperature + offset;
 * //     25.0           + 0.0 = 25.0
 *
 * Получается, сервис рассчитает температуру для незарегистрированного датчика,
 * хотя по требованиям должен выбросить IllegalArgumentException.
 */

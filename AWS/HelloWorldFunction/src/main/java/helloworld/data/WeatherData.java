package helloworld.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherData {
    private int temp;
    private int humidity;
}

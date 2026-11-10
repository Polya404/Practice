package helloworld;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomInputs {

    public Map<String, Long> handler(List<String> users) {
        return users.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
    }

//    public double handler(List<Integer> nums){
//        return nums.stream().mapToInt(Integer::intValue).average().getAsDouble();
//    }
}

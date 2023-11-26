package leetcode;

import java.util.HashMap;
import java.util.Map;

public class RomanToInt {
    public static void main(String[] args) {

    }

    public static int romanToInt2(String s) {
        int ans = 0, num = 0;
        for (int i = s.length()-1; i >= 0; i--) {
            switch(s.charAt(i)) {
                case 'I': num = 1; break;
                case 'V': num = 5; break;
                case 'X': num = 10; break;
                case 'L': num = 50; break;
                case 'C': num = 100; break;
                case 'D': num = 500; break;
                case 'M': num = 1000; break;
            }
            if (4 * num < ans) ans -= num;
            else ans += num;
        }
        return ans;
    }

    public static int romanToInt(String s) {
        Integer answer = 0;
        Map<String, Integer> map = getMap();

        String [] array = s.split("");
        for (int i = 0, j = 1; j <= array.length; i++, j++){
            if (array[i].equals("I") | array[i].equals("X") | array[i].equals("C")){
                if(j == array.length){
                    answer += map.get(array[j-1]);
                    continue;
                }
                if (check(array[i], array[j]) == 0){
                    answer += map.get(array[i]);

                }
                else {
                    answer += check(array[i], array[j]);
                    i +=1;
                    j +=1;
                }
            }
            else {
                answer += map.get(array[i]);
            }
        }
        return answer;
    }

    private static Map<String, Integer> getMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        return map;
    }

    private static Integer check (String f, String s){
        if (f.equals("I") & s.equals("V") ){
            return 4;
        }
        if (f.equals("I") & s.equals("X") ){
            return 9;
        }
        if (f.equals("X") & s.equals("L") ){
            return 40;
        }
        if (f.equals("X") & s.equals("C") ){
            return 90;
        }
        if (f.equals("C") & s.equals("D") ){
            return 400;
        }
        if (f.equals("C") & s.equals("M") ){
            return 900;
        }
        return 0;
    }
}

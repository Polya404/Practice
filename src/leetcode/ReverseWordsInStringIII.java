package leetcode;

import java.util.Arrays;

public class ReverseWordsInStringIII {
    public static void main(String[] args) {
        String s = "Let's take LeetCode contest";
        System.out.println(reverseWords(s));
    }

    public static String reverseWords(String s) {
        String res = "";
        int x = s.indexOf(" ");
        char[] arr = s.toCharArray();
        for (int i = arr.length-1; i >= 0; i--) {
            res += String.valueOf(arr[i]);
        }
        String[] array = res.split(" ");
        String tmp;
        for (int i = (array.length - 1), j = 0; i > (array.length / 2) - 1; i--, j++) {
            tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
        res = String.join(" ", array);
        return res;
    }
}

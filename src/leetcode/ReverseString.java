package leetcode;

public class ReverseString {
    public static void main(String[] args) {

    }

    public static char[] reverseString(char[] s) {
        //System.out.println(s);
        char tmp;
        for (int i = (s.length - 1), j = 0; i > (s.length / 2) - 1; i--, j++) {
            tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
        return s;
    }
}

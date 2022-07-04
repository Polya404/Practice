package leetcode;

public class LengthOfLastWord {
    public static void main(String[] args) {

    }

    public static int lengthOfLastWord(String s) {
        s = s.trim();
        int x = s.lastIndexOf(" ");
        int count = 0;
        char[] res = s.toCharArray();
        for (int i = x + 1; i < res.length; i++) {
            count += 1;
        }
        return count;
    }
}

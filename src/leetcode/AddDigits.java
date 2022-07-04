package leetcode;

public class AddDigits {
    public static void main(String[] args) {

    }

    public static int addDigits(int num) {
        /**
         * Given an integer num, repeatedly add all its
         * digits until the result has only one digit, and return it.
         */
        int i;
        int x1 = num;
        int[] arr;
        int c = 0;
        if (num == 0) {
            return 0;
        }
        while ((num > 9)) {
            for (; num > 0; num /= 10) {
                c += 1;
            }
            arr = new int[c];
            for (int j = 0; x1 > 0; x1 /= 10, j++) {
                i = x1 % 10;
                arr[j] = i;
            }
            for (int value : arr) {
                num = num + value;
                x1 = num;
            }
        }
        return num;
    }
}

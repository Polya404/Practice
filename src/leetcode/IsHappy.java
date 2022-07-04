package leetcode;

public class IsHappy {
    public static void main(String[] args) {

    }

    public static boolean isHappy(int n) {
        /**
         * Starting with any positive integer, replace the number by the sum of the squares of its digits.
         * Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
         * Those numbers for which this process ends in 1 are happy.
         */
        int i;
        int x1 = n;
        int[] arr;
        int c = 0;

        if (n < 10) {
            n = (int) Math.pow(n, 2);
        }
        while ((n > 9)) {
            for (; n > 0; n /= 10) {
                c += 1;
            }
            arr = new int[c];
            for (int j = 0; x1 > 0; x1 /= 10, j++) {
                i = x1 % 10;
                arr[j] = (int) Math.pow(i, 2);
            }
            for (int value : arr) {
                n = n + value;
                x1 = n;
            }
            if (n == 4) {
                return false;
            }
            c = 0;
            if (n < 10) {
                n = (int) Math.pow(n, 2);
            }
        }
        return n == 1;
    }
}

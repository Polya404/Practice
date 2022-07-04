package leetcode;

public class IsPowerOfThree {
    public static void main(String[] args) {

    }

    public static boolean isPowerOfThree(int n) {
        int count = 0;
        int i = n;
        if (n == 1) {
            return true;
        }
        if (n % 3 == 0) {
            while (n > 1) {
                n = n / 3;
                count += 1;
                if ((Math.pow(3, count)) == i) {
                    return true;
                }
            }
        }
        return false;
    }
}

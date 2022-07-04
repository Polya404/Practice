package leetcode;

public class MyPow {
    public static void main(String[] args) {

    }

    public static double myPow(double x, int n) {
        if (x == 1) {
            return 1;
        }
        double i = 0;
        double j = x;
        if (n == 1) {
            return x;
        }
        if (n == 0) {
            return 1;
        }
        if (n > 0) {
            while ((n - 1) >= 1) {
                i = j * x;
                j = i;
                n -= 1;
            }
        } else {
            n *= -1;
            if (n == 1) {
                return 1 / x;
            }
            while ((n - 1) >= 1) {
                i = j * x;
                j = i;
                n -= 1;
            }
            i = 1 / i;
        }
        return i;
    }
}

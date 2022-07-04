package leetcode;

public class IsPowerTwo {
    public static void main(String[] args) {

    }

    public static boolean isPowerOfTwo(int n) {
        int count = 0;
        int i = n;
        if (n == 1) {
            return true;
        }
        if (n % 2 == 0) {
            while (n > 1) {
                n = n / 2;
                count += 1;
                if ((Math.pow(2, count)) == i) {
                    return true;
                }
            }
        }
        return false;
//        if (n == 1){
//            return true;
//        }
//        for (long i = 0; i < n; i++) {
//            long x = 2L << i;
//            System.out.println(x);
//            if(x == n){
//                return true;
//            }
//        }
//        return false;
    }
}

package leetcode;

public class isPalindrome {
    public static void main(String[] args) {

    }

    public static boolean isPalindrome(int x) {
        // x = 132
        if (x == 0) {
            return true;
        }
        int i;
        int x1 = x;
        int[] arr;
        int c = 0;
        for (; x > 0; x /= 10) {
            c += 1;
        }
        arr = new int[c];
        for (int j = 0; x1 > 0; x1 /= 10, j++) {
            i = x1 % 10;
            arr[j] = i;
        }

        for (int k = 0, j = arr.length - 1; k < arr.length; k++, j--) {
            if (arr[k] == arr[j]) {
                if ((arr.length / 2 == k) && (arr[k] == arr[j])) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}

package leetcode;

public class isPalindrome {
    public static void main(String[] args) {

    }

    /**
     * Given an integer x, return true if x is a
     * palindrome, and false otherwise.
     * Example 1:
     *
     * Input: x = 121
     * Output: true
     * Explanation: 121 reads as 121 from left to right and from right to left.
     * Example 2:
     *
     * Input: x = -121
     * Output: false
     * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
     * Example 3:
     *
     * Input: x = 10
     * Output: false
     * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
     * @param x
     * @return
     */
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


    public static boolean isPalindrome2(int x) {
        String s = String.valueOf(x);
        String[] a = s.split("");
        for(int i = 0, j = a.length-1; i < a.length; i++, j--) {
            if(!(a[i].equals(a[j]))){
                return false;
            }
        }
        return true;
    }
}

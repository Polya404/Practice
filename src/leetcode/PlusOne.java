package leetcode;

public class PlusOne {
    public static void main(String[] args) {

    }

    public int[] plusOne(int[] digits) {
        int n = digits.length;
        int carry = 1;
        for (int i = n - 1; i >= 0; i--) {
            digits[i] = digits[i] + carry;
            if (digits[i] == 10) {
                digits[i] = 0;
                carry = 1;
            } else {
                return digits;
            }
            if (i == 0 && carry == 1) {
                int[] arr = new int[n + 1];
                arr[0] = 1;
                for (int j = 1; j < n + 1; j++) {
                    arr[j] = digits[j - 1];
                }
                return arr;
            }
        }
        return digits;
    }
}

package leetcode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Tasks {
    public static void main(String[] args) {
        //System.out.println(singleNumber(new int[]{2,2,1}));
        //System.out.println(isPalindrome(18361));
        //System.out.println(lengthOfLastWord(new String("   fly me   to   the moon  ")));
        //System.out.println(searchInsert(new int[]{1, 3}, 3));
        //System.out.println(addBinary(new String("11"),new String("1")));
        //System.out.println(isHappy(4));
        //System.out.println(containsDuplicate(new int[]{1, 2, 3, 1}));
        //System.out.println(isPowerOfTwo(8));
        //System.out.println(isPowerOfThree(27));
        //char[] s = new char[]{'h', 'e', 'l', 'l', 'o'};
        //System.out.println(reverseString(s));
        //System.out.println(myPow(2.00000, -2));
        //System.out.println(addDigits(294));
//        int[] l = new int[]{2,7,4};
//        System.out.println(addToArrayForm(l, 181));
    }

    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; ; i++) {
            for (int j = 1; j < nums.length; j++) {
                if ((nums[i] + nums[j]) == target && (i != j)) {
                    return new int[]{i, j};
                }

            }
        }
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

    public static int singleNumber(int[] nums) {
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {

            if (count.containsKey(nums[i])) {
                count.put(nums[i], 2);
            } else {
                count.put(nums[i], 1);
            }

        }

        System.out.println(count);
        for (int i = 0; i < nums.length; i++) {

            if (count.get(nums[i]).equals(1)) {
                return nums[i];
            }
        }
        return 0;
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

    public static int searchInsert(int[] nums, int target) {
        if (target < nums[0]) {
            return 0;
        }
        if (target > nums[nums.length - 1]) {
            return nums.length;
        }
        if (nums.length == 1) {
            return 0;
        }
        for (int i = 0; i < nums.length - 1; i++) {
            if (target == nums[i]) {
                return i;
            }
            if ((target > nums[i]) && (target <= nums[i + 1])) {
                return i + 1;
            }
        }
        return target;
    }

    public static String addBinary(String a, String b) {
        //return Integer.toBinaryString(Integer.parseInt(a,2) + Integer.parseInt(b,2));
        BigInteger num1 = new BigInteger(a, 2);
        BigInteger num2 = new BigInteger(b, 2);
        num1 = num1.add(num2);
        return num1.toString(2);
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

    public static boolean containsDuplicate(int[] nums) {
        /**
         * Given an integer array nums, return true if any
         * value appears at least twice in the array, and
         * return false if every element is distinct.
         */
        HashMap<Integer, Integer> counter = new HashMap<>();
        for (int num : nums) {
            if (counter.containsKey(num)) {
                counter.put(num, 2);
            } else {
                counter.put(num, 1);
            }
        }
        for (int num : nums) {

            if (counter.get(num).equals(2)) {
                return true;
            }
        }
        System.out.println(counter);
        return false;

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

    public static List<Integer> addToArrayForm(int[] num, int k) {
//        int carry = 0;
//        List<Integer> ans = new ArrayList<>();
//        int i = num.length - 1;
//        while (i >= 0 || carry > 0 || k > 0) {
//            int toadd = k % 10 + carry + (i >= 0 ? num[i--] : 0);
//            carry = toadd / 10;
//            toadd = toadd % 10;
//            k = k / 10;
//            ans.add(0, toadd);
//        }
//        return ans;
        int tmp;
        int c = 0;
        long res = 0;
        for (int i = num.length - 1, n = 0; i >= 0; --i, n++) {
            long pos = (long) Math.pow(10, i);
            res += num[n] * pos;
        }
        res += k;
        long j = res;
        for (; j > 0; j /= 10) {
            c += 1;
        }
        int[] arr = new int[c];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] += res % 10;
            res /= 10;
        }

        for (int i = (arr.length - 1), x = 0; i > (arr.length / 2) - 1; i--, x++) {
            tmp = arr[i];
            arr[i] = arr[x];
            arr[x] = tmp;
        }
        List<Integer> list = new ArrayList<>();
        for (int value : arr) {
            list.add(value);
        }
        return list;
    }

}

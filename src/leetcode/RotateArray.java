package leetcode;

import java.util.Arrays;

public class RotateArray {
    public static void main(String[] args) {
        int[] num = new int[]{1, 2, 8, 5, 9, 3, 4, 7, 6}; //4 7 6 1 2 8 5 9 3
        int k = 3;
        rotate(num, k);
    }

    public static void rotate(int[] nums, int k) {
        /**
         * Given an array, rotate the array to the right by k steps, where k is non-negative
         */
        if (nums.length == 1) {

        } else if (k > nums.length) {
            for (int i = nums.length - 1; i > nums.length - 1 - k; i--) {
                int tmp = nums[nums.length - 1];
                for (int j = nums.length - 2; j >= 0; j--) {
                    nums[j + 1] = nums[j];
                }
                nums[0] = tmp;
            }
        } else {
            int[] newArr = new int[nums.length];
            System.arraycopy(nums, 0, newArr, k, nums.length - k);
            System.arraycopy(nums, nums.length - k, nums, 0, k);
            System.arraycopy(newArr, k, nums, k, nums.length - k);
            System.out.println(Arrays.toString(nums));
        }
        System.out.println(Arrays.toString(nums));
    }


}

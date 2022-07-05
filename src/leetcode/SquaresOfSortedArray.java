package leetcode;

import java.util.Arrays;

public class SquaresOfSortedArray {
    public static void main(String[] args) {
        int[] array = new int[]{-4, -1, 0, 3, 10};
        sortedSquares(array);
    }
    public static int[] sortedSquares(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nums[i]*nums[i];
        }
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] < nums[i - 1]) {
                    swap(nums, i, i-1);
                    needIteration = true;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
        return nums;
    }
    private static void swap(int[] array, int ind1, int ind2) {
        int tmp = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = tmp;

    }
}

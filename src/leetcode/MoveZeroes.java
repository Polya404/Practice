package leetcode;

import java.util.Arrays;

public class MoveZeroes {
    public static void main(String[] args) {
        int[] nums = new int[]{0, 0, 0, 1, 3, 0, 0, 0, 5, 0, 7, 6, 0, 8, 9, 0};  // 1 3 5 7 6 8 9 0 0 0 0
        moveZeroes(nums);
    }

    public static void moveZeroes(int[] nums) {

        for (int i = 1; i < nums.length; i++) {
            int tmp;
            if (nums[i - 1] == 0 && nums[i] != 0) {
                tmp = nums[i - 1];
                nums[i - 1] = nums[i];
                nums[i] = tmp;
                i--;
            }
            if (i - 1 >= 0 && nums[i - 1] == 0 && nums[i] != 0) {
                i--;
            }
        }
        System.out.println(Arrays.toString(nums));
    }
}

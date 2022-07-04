package leetcode;

public class TwoSum {
    public static void main(String[] args) {

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
}

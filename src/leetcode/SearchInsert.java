package leetcode;

public class SearchInsert {
    public static void main(String[] args) {

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
}

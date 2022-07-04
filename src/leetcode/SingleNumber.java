package leetcode;

import java.util.HashMap;

public class SingleNumber {
    public static void main(String[] args) {

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
}

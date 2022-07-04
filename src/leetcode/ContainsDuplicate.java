package leetcode;

import java.util.HashMap;

public class ContainsDuplicate {
    public static void main(String[] args) {

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
}

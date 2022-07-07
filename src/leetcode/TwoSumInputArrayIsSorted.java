package leetcode;

import java.util.Arrays;

public class TwoSumInputArrayIsSorted {
    public static void main(String[] args) {
        int[] num = new int[]{1,1,2,3,3,3,6,6};
        System.out.println(Arrays.toString(twoSum(num, 7)));
    }

    public static int[] twoSum(int[] numbers, int target) {
        // for the first occurrence of a number

//        for (int i = 0; ; i++) {
//            for (int j = 1; j < numbers.length; j++) {
//                if(numbers[i] == numbers[j] && (numbers[i] + numbers[j]) != target && (i != j)){
//                    j++;
//                    continue;
//                }
//                if ((numbers[i] + numbers[j]) == target && (i != j)) {
//                    return new int[]{i + 1, j + 1};
//                }
//                if (numbers[i] + numbers[j] > target && i < numbers.length-1) {
//                    i++;
//                    j = 0;
//                }
//            }
//        }
        int[] ans = new int[2];
        int i = 0, j = numbers.length - 1;

        while (i <= j) {
            int s = numbers[i] + numbers[j];
            if (s == target) {
                ans[0] = i + 1;
                ans[1] = j + 1;
                return ans;
            }
            if (s > target) {
                j--;
            } else {
                i++;
            }
        }
        return ans;
    }
}

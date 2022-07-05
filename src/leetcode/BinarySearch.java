package leetcode;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,7,8,12,46,78,81};
        int target = 46;
        int target1 = 1;
        int target2 = 81;
        int target3 = 83;
        System.out.println(search(arr, target));
        System.out.println(search(arr, target1));
        System.out.println(search(arr, target2));
        System.out.println(search(arr, target3));
    }

    public static int search(int[] nums, int target) {
        int first = 0;
        int last = nums.length-1;
        while (first <= last){
            int middle = first +(last-first)/2;
            if (nums[middle]==target){
                return middle;
            }else if(nums[middle] < target){
                first = middle+1;
            }else {
                last = middle-1;
            }
        }
        return -1;
    }
}

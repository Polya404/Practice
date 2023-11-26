package leetcode;

public class MergeTwoSortedList {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2, new ListNode(9));
        ListNode l2 = new ListNode(3, new ListNode(5));
        System.out.println(mergeTwoLists(new ListNode(1, l1), new ListNode(2, l2)).toString());
    }

    public static class ListNode {
        int val;
        ListNode next;

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    /**
     * You are given the heads of two sorted linked lists list1 and list2.
     * Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.
     * Return the head of the merged linked list.
     * Example 1:
     * Input: list1 = [1,2,4], list2 = [1,3,4]
     * Output: [1,1,2,3,4,4]
     * Example 2:
     * Input: list1 = [], list2 = []
     * Output: []
     * Example 3:
     * Input: list1 = [], list2 = [0]
     * Output: [0]
     */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        System.out.print("List1   -->  ");
        System.out.println(list1);
        System.out.print("List2   -->  ");
        System.out.println(list2);
        if (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                System.out.print("List1 before mergeTwoLists if list1.val < list2.val -->  ");
                System.out.println(list1);
                System.out.print("List2 before mergeTwoLists if list1.val < list2.val -->  ");
                System.out.println(list2);
                list1.next = mergeTwoLists(list1.next, list2);
                System.out.print("List1 after mergeTwoLists if list1.val < list2.val -->  ");
                System.out.println(list1);
                System.out.print("List2 after mergeTwoLists if list1.val < list2.val -->  ");
                System.out.println(list2);
                return list1;
            } else {
                System.out.print("List1 before mergeTwoLists if list1.val > list2.val -->  ");
                System.out.println(list1);
                System.out.print("List2 before mergeTwoLists if list1.val > list2.val -->  ");
                System.out.println(list2);
                list2.next = mergeTwoLists(list1, list2.next);
                System.out.print("List1 after mergeTwoLists if list1.val > list2.val -->  ");
                System.out.println(list1);
                System.out.print("List2 after mergeTwoLists if list1.val > list2.val -->  ");
                System.out.println(list2);
                return list2;
            }
        }
        if (list1 == null) {
            System.out.print("List1 == null  -->  ");
            System.out.println(list2);
            return list2;
        }
        return list1;
    }
}

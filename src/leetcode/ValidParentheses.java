package leetcode;

import java.util.*;

public class ValidParentheses {
    public static void main(String[] args) {
        System.out.println(isValid("([]){}"));
    }

    /**
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * An input string is valid if:
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     * Every close bracket has a corresponding open bracket of the same type.
     * <p>
     * Example 1:
     * <p>
     * Input: s = "()"
     * Output: true
     * Example 2:
     * <p>
     * Input: s = "()[]{}"
     * Output: true
     * Example 3:
     * <p>
     * Input: s = "(]"
     * Output: false
     *
     * @param s
     * @return determine if the input string is valid
     */
    public static boolean isValid(String s) {
        String[] strings = s.split("");
        Deque<String> deque = new ArrayDeque<>();
        for (String string : strings) {
            switch (string) {
                case "{":
                    deque.push("}");
                    break;
                case "[":
                    deque.push("]");
                    break;
                case "(":
                    deque.push(")");
                    break;
                default:
                    if (deque.isEmpty() || (!deque.pop().equals(string))) return false;
            }
        }
        return deque.isEmpty();
    }

    public static boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }
}

package leetcode;

import java.math.BigInteger;

public class AddBinary {
    public static void main(String[] args) {

    }

    public static String addBinary(String a, String b) {
        //return Integer.toBinaryString(Integer.parseInt(a,2) + Integer.parseInt(b,2));
        BigInteger num1 = new BigInteger(a, 2);
        BigInteger num2 = new BigInteger(b, 2);
        num1 = num1.add(num2);
        return num1.toString(2);
    }
}

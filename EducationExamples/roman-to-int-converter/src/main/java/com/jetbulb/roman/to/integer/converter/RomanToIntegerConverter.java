package com.jetbulb.roman.to.integer.converter;

import java.util.Set;
import java.util.regex.Pattern;

public final class RomanToIntegerConverter {

    public static final char R_ONE = 'I';
    public static final char R_FIVE = 'V';
    public static final char R_TEN = 'X';
    public static final char R_FIFTY = 'L';
    public static final char R_HUNDRED = 'C';
    public static final char R_FIVE_HUNDREDS = 'D';
    public static final char R_THOUSAND = 'M';
    private static final Pattern ROMAN_NUMERAL_REGEX =
            Pattern.compile("^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

    public static final Set<Character> ROMAN_DIGITS = Set.of(
            R_ONE, R_FIVE, R_TEN, R_FIFTY, R_HUNDRED, R_FIVE_HUNDREDS, R_THOUSAND
    );

    public static int convert(String value) {
        if (value == null)
            throw new ConstraintViolationException("Roman numeral can’t be null");
        if (!ROMAN_NUMERAL_REGEX.matcher(value).matches())
            throw new ConstraintViolationException("Invalid Roman numeral detected: '%s'".formatted(value));

        var upperValue = value.toUpperCase();
        var result = 0;
        var num = 0;

        for (int i = upperValue.length() - 1; i >= 0; i--) {
            switch (upperValue.charAt(i)) {
                case R_ONE -> num = 1;
                case R_FIVE -> num = 5;
                case R_TEN -> num = 10;
                case R_FIFTY -> num = 50;
                case R_HUNDRED -> num = 100;
                case R_FIVE_HUNDREDS -> num = 500;
                case R_THOUSAND -> num = 1000;
            }
            if (4 * num < result) result -= num;
            else result += num;
        }

        return result;
    }
}

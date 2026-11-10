package com.jetbulb.roman.to.integer.converter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class RomanToIntegerConverterTest {

    // many possible values skipped for simplification
    private static final List<String> NON_ROMAN_DIGITS = IntStream.range(0, 128)
            .filter(asciiIntVal -> !RomanToIntegerConverter.ROMAN_DIGITS.contains((char) asciiIntVal))
            .boxed()
            .map(asciiIntVal -> String.valueOf((char) asciiIntVal.intValue()))
            .toList();

    @ParameterizedTest
    @CsvSource(value = {
            "III,3",
            "LVIII,58",
            "MCMXCIV,1994",
    })
    void shouldConvertRomanDigitToInteger_whenGivenRomanOneIsValid(String actualRoman, int expectedInteger) {
        var actual = RomanToIntegerConverter.convert(actualRoman);
        assertThat(actual).isEqualTo(expectedInteger);
    }

    @ParameterizedTest
    @MethodSource("nonRomanDigitsProvider")
    void shouldThrowConstraintViolationException_whenGivenRomanDigitIsInvalid(TestData testData) {
        assertThat(
                catchThrowableOfType(
                        () -> RomanToIntegerConverter.convert(testData.invalidRomanDigit), ConstraintViolationException.class
                )
        )
                .hasNoCause()
                .hasMessage(testData.errorMessage);

    }

    private static Stream<TestData> nonRomanDigitsProvider() {
        var firstPortion = Stream.of(
                new TestData(null, "Roman numeral can’t be null"),
                new TestData("", "Invalid Roman numeral detected: ''"),
                new TestData(" ", "Invalid Roman numeral detected: ' '")
        );

        var secondPortion = NON_ROMAN_DIGITS.stream()
                .map(nonRoman -> new TestData(nonRoman, "Invalid Roman numeral detected: '%s'".formatted(nonRoman)));

        return Stream.concat(firstPortion, secondPortion);
    }

    record TestData(String invalidRomanDigit, String errorMessage) {
    }
}
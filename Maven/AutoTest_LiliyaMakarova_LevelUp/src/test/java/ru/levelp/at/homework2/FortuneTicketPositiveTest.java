package ru.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FortuneTicketPositiveTest {

    @Tag("Positive")
    @ParameterizedTest
    @ValueSource(ints = { 333333, 999999 })
    void sumGood(int input) {
        boolean expected = true;
        boolean actual = FortuneTicket.ticket(input);
        assertThat(actual).isEqualTo(expected);
    }

    @Tag("Positive")
    @ParameterizedTest
    @ValueSource(ints = { 222223, 888889 })
    void sumIsNotGood(int input) {
        boolean expected = false;
        boolean actual = FortuneTicket.ticket(input);
        assertThat(actual).isEqualTo(expected);
    }

}

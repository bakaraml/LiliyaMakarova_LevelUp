package ru.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FortuneTicketNegativeTest {

    @Tag("Negative")
    @ParameterizedTest
    @ValueSource(ints = { 123, 1234 })
    void billetIsBad(int input) {
        assertThatIllegalArgumentException().isThrownBy(() -> FortuneTicket.ticket(input));
    }
}

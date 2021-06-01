package DDD.framework;

import org.junit.Test;

import java.math.BigDecimal;

import static DDD.framework.Objects.CAN_NOT_BE_NULL;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class BigDecimalsTest {

    @Test
    public void requireNotNul() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> BigDecimals.requireNotNul(null))
                .withMessage(CAN_NOT_BE_NULL);
    }

    @Test
    public void requirePositifOrZero() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> BigDecimals.requirePositiveOrZero(valueOf(-1)))
                .withMessage(BigDecimals.CAN_NOT_BE_NEGATIVE);
    }

    @Test
    public void isNegative() {
        assertThat(BigDecimals.isNegative(valueOf(-1))).isTrue();
        assertThat(BigDecimals.isNegative(BigDecimal.ZERO)).isFalse();
    }
}
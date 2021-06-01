package DDD.framework;

import java.math.BigDecimal;

import static DDD.framework.Objects.CAN_NOT_BE_NULL;
import static java.math.BigDecimal.ZERO;

public class BigDecimals {

    public static final String CAN_NOT_BE_NEGATIVE = "can not be negative";

    public static BigDecimal requireNotNul(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException(CAN_NOT_BE_NULL);
        }
        return value;
    }

    public static BigDecimal requirePositiveOrZero(BigDecimal value) {
        requireNotNul(value);
        if (isNegative(value)) {
            throw new IllegalArgumentException(CAN_NOT_BE_NEGATIVE);
        }
        return value;
    }

    public static boolean isNegative(BigDecimal value) {
        return value.compareTo(ZERO) < 0;
    }
}

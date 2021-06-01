package DDD.framework;

import com.lb.formationtest.domain.TransactionException;

import java.util.Collection;

public class Objects {

    public static final String CAN_NOT_BE_NULL = "can not be null";
    public static final String VALUE_NOT_IN_THE_DOMAIN = "value not in the domain";

    public static <T> T requireNotNull(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException(CAN_NOT_BE_NULL);
        }
        return obj;
    }

    public static <T> T requireNotNull(T obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);
        return obj;
    }

    public static <T> Collection<T> requireNotEmpty(Collection<T> obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);
        return obj;
    }


    public static void checkSame(Object object1, Object object2, String msg) {
        if (!object1.equals(object2))
            throw new TransactionException(msg);
    }
}

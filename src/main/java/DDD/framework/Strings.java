package DDD.framework;

import com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.Set;

public class Strings extends ASetOf<String> {

    public Strings() {}

    public Strings(ImmutableSet<String> set) {
        super(set);
    }

    public Strings(Set<String> set) {
        super(set);
    }

    public Strings(List<String> list) {
        super(list);
    }

    public Strings(String... arrayOfStr) {
        super(arrayOfStr);
    }

    public Strings(Iterable<String> iterable) {
        super(iterable);
    }

    public static Strings of(String... arrayOfStr) {
        return new Strings(arrayOfStr);
    }

    public static String requireNotEmpty(String str) {
        return requireNotEmpty(str, "String is null or empty");
    }

    public static String requireNotEmpty(String str, String msg) {
        if (str == null) throw new IllegalArgumentException(msg);
        if (str.trim().isEmpty()) throw new IllegalArgumentException(msg);
        return str;
    }

    public static String requireMinLength(String str, int min) {
        return requireMinLength(str, min , "String length should be more then " + min);
    }

    public static String requireMinMaxLength(String str, int min, int max) {
        return requireMinMaxLength(str, min, max, "String length should be between " + min + " and " + max);
    }

    public static String requireMinLength(String str, int min, String msg) {
        requireNotEmpty(str);
        if (str.trim().length() < min) throw new IllegalArgumentException(msg);
        return str;
    }

    public static String requireMinMaxLength(String str, int min, int max, String msg) {
        requireNotEmpty(str);
        if (str.trim().length()<min && str.trim().length()>min) throw new IllegalArgumentException(msg);
        return str;
    }

    public static String requireLength(String str, int length) {
        return requireLength(str, length, "str:\"" + str + "\" must be " + length + "long");
    }

    public static String requireLength(String str, int length, String msg) {
        requireNotEmpty(str);
        if (str.trim().length() != length) throw new IllegalArgumentException(msg);
        return str;
    }


    @Override
    public <Sub extends ASetOf<String>> Sub cons(Set<String> newSet) {
        return (Sub) new Strings(newSet);
    }
}

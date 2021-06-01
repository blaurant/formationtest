package DDD.framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static DDD.framework.Objects.CAN_NOT_BE_NULL;
import static com.google.common.collect.ImmutableSet.copyOf;
import static java.util.Objects.requireNonNull;

public abstract class ASetOf<T> implements Iterable<T> {

    protected ImmutableSet<T> set;

    public ASetOf() {
        this.set = ImmutableSet.of();
    }

    public ASetOf(ImmutableSet<T> set) {
        this.set = requireNonNull(set);
    }

    public ASetOf(Set<T> set) {
        this.set = copyOf(requireNonNull(set));
    }
    public ASetOf(List<T> list) {
        this.set = copyOf(requireNonNull(list));
    }

    public ASetOf(T[] arrayOfT) {
        this.set = copyOf(requireNonNull(arrayOfT));
    }

    public ASetOf(Iterable<T> iterable) {
        this.set = ImmutableSet.copyOf(iterable);
    }

    public static <T, SUB extends ASetOf<T>> SUB requireNotEmpty(SUB sub) {
        return requireNotEmpty(sub, null);
    }

    public static <T, SUB extends ASetOf<T>> SUB requireNotNullNotEmpty(SUB sub, String msg) {
        if (sub == null)
            throw new IllegalArgumentException(msg);
        return requireNotEmpty(sub, msg);
    }
    public static <T, SUB extends ASetOf<T>> SUB requireNotNullNotEmpty(SUB sub) {
        return requireNotEmpty(sub, CAN_NOT_BE_NULL);
    }

    public static <T, SUB extends ASetOf<T>> SUB requireNotEmpty(SUB sub, String msg) {
        if (sub.isEmpty())
            throw new IllegalArgumentException(msg);
        return sub;
    }

    public abstract <SUB extends ASetOf<T>> SUB cons(Set<T> newSet);

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }

    public <SUB extends ASetOf<T>> SUB add(T t) {
        return cons(new ImmutableSet.Builder<T>().addAll(set).add(t).build());
    }

    public <SUB extends ASetOf<T>> SUB addAll(SUB sub) {
        return cons(new ImmutableSet.Builder<T>().addAll(set).addAll(sub.set).build());
    }

    public <SUB extends ASetOf<T>> SUB remove(T t) {
        return filter(e -> !e.equals(t));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASetOf<?> aSetOf = (ASetOf<?>) o;
        return set.equals(aSetOf.set);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    public boolean contains(T t) {
        return set.contains(t);
    }

    public boolean doNotContains(T t) {
        return !contains(t);
    }

    public <SUB extends ASetOf<T>> boolean containsAll(SUB sub) {
        return sub.minus(this).isEmpty();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isUnique() {
        return size() == 1;
    }

    public int size() {
        return set.size();
    }

    public Optional<T> findAny() {
        return stream().findAny();
    }

    public Optional<T> first() {
        return stream().findFirst();
    }

    public T first(T t) {
        return first().orElse(t);
    }

    public T firstOrNull() {
        return first().orElse(null);
    }

    private void checkUnique() {
        if (set.size() > 1)
            throw new IllegalStateException("this contains more than one element");
    }

    public T require(T t) {
        if (doNotContains(t))
            throw new IllegalArgumentException(Objects.VALUE_NOT_IN_THE_DOMAIN);
        return t;
    }

    public Optional<T> unique() {
        checkUnique();
        return first();
    }

    public T unique(T t) {
        checkUnique();
        return first(t);
    }

    public T uniqueOrNull() {
        checkUnique();
        return firstOrNull();
    }

    public Stream<T> stream() {
        return set.stream();
    }

    public Stream<T> streamFilter(Predicate<T> predicate) {
        return set.stream().filter(predicate);
    }

    public <SUB extends ASetOf<T>> SUB filter(Predicate<T> predicate) {
        return cons(set.stream().filter(predicate).collect(Collectors.toSet()));
    }

    public void each(Consumer<T> consumer) {
        set.stream().forEach(consumer);
    }

    public T find(T t) {
        return streamFilter(e -> e.equals(t)).findFirst().orElse(null);
    }

    public List<T> toList() {
        return Lists.newArrayList(this);
    }

    public Set<T> toSet() {
        return set;
    }

    public <SUB extends ASetOf<T>, R> Set<R> map(Function<T, R> mapper) {
        return set.stream().map(mapper).collect(Collectors.toSet());
    }

    public <SUB extends ASetOf<T>> SUB minus(SUB sub) {
        return filter(e -> !sub.contains(e));
    }

    public <SUB extends ASetOf<T>> SUB union(SUB sub) {
        return addAll(sub);
    }

    public <SUB extends ASetOf<T>> SUB inter(SUB sub) {
        return filter(sub::contains);
    }

}

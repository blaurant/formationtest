package DDD;

public @interface Entity {

    Class<?> aggregate();

    boolean rootAggregate() default false;
}

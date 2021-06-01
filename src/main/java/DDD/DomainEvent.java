package DDD;

public interface DomainEvent {

    String getChannel();

    void apply(Object obj);

}


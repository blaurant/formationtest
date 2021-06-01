package DDD.framework;

import DDD.DomainEvent;

import java.util.Set;

public class DomainEvents extends ASetOf<DomainEvent> {

    public DomainEvents(Set<DomainEvent> set) {
        super(set);
    }

    public DomainEvents(DomainEvent... DomainEvents) {
        super(DomainEvents);
    }

    @Override
    public <Sub extends ASetOf<DomainEvent>> Sub cons(Set<DomainEvent> newSet) {
        return (Sub) new DomainEvents(newSet);
    }

}

   
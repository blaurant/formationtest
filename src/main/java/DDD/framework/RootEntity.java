package DDD.framework;

import DDD.Bus;
import DDD.DomainEvent;

import static DDD.framework.Objects.requireNotNull;

public abstract class RootEntity<ID> extends Entity<ID>{

    protected Bus bus;

    public RootEntity(ID id) {
        super(id);
    }

    public void injectBus(Bus bus) {
        this.bus = requireNotNull(bus);
    }

    public void execCmd(Command command) {
        DomainEvents domainEvents = decide(command);
        evolve(domainEvents);
        publish(domainEvents);
    }

    protected DomainEvents decide(Command command) {
        return command.decide(this);
    }

    protected void evolve(DomainEvents events) { // this is map sumup
        events.each(event -> event.apply(this)); // TODO fatal !!!!! asetof is a set, no order !!!!
    }

    protected void publish(DomainEvent event) {
        bus.publish(event);
    }

    protected void publish(DomainEvents events) {
        events.each(event -> publish(event));
    }
}

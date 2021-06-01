package DDD.framework;

import DDD.Bus;
import DDD.DomainEvent;
import org.springframework.statemachine.StateMachine;

import static DDD.framework.Objects.requireNotNull;

/**
 @param <ID> id's type of the entity
 @param <S> state's type
 */
public abstract class LifecycleEntity<ID, S> extends Entity<ID> {

    protected Bus bus;
    protected StateMachine<S, Command> stateMachine;

    public LifecycleEntity(ID id, S state) {
        super(id);
        initLifecycle(requireNotNull(state));
    }

    protected void initLifecycle(S state) {
        try {
            this.stateMachine = buildMachine(state);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        stateMachine.start();
    }

    protected abstract StateMachine<S, Command> buildMachine(S state) throws Exception;


    public void executeCmd(Command command) {
        stateMachine.sendEvent(command);
    }

    protected void fire(DomainEvent event) {
        apply(event);
        publish(event);
    }

    protected void apply(DomainEvent event) {
        event.apply(this);
    }

    protected void apply(DomainEvents events) { // this is map sumup
        events.each(event -> event.apply(this)); // TODO fatal !!!!! asetof is a set, no order !!!!
    }

    public S currentState() {
        return this.stateMachine.getState().getId();
    }

    public boolean isState(S s) {
        return currentState().equals(s);
    }

    protected void publish(DomainEvent event) {
        bus.publish(event);
    }
}

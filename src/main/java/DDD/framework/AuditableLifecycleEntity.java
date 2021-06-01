package DDD.framework;

import java.util.Optional;

import static DDD.framework.Objects.requireNotNull;

public abstract class AuditableLifecycleEntity<ID, S> extends LifecycleEntity<ID, S> {

    private AuditTrail auditTrail;

    public AuditableLifecycleEntity(ID id, AuditTrail auditTrail, S state) {
        super(id, state);
        this.auditTrail = requireNotNull(auditTrail);
    }

    protected void audit(Trace trace) {
        auditTrail = auditTrail.add(trace);
    }

    protected void audit(String command, String entity) {
        audit(new Trace(command, entity));
    }

    protected void audit(String command) {
        audit(new Trace(command, asJson()));
    }

    protected abstract String asJson();

    public AuditTrail auditTrail() {
        return auditTrail;
    }

    public Optional<Trace> lastAudit() {
        return auditTrail.last();
    }

}

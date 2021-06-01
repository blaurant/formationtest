package DDD.framework;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AuditTrail extends ASetOf<Trace> {

    public AuditTrail() {
        super();
    }

    public AuditTrail(Set<Trace> set) {
        super(set);
    }

    @Override
    public <SUB extends ASetOf<Trace>> SUB cons(Set<Trace> newSet) {
        return (SUB) new AuditTrail(newSet);
    }

    public Optional<Trace> last() {
        List<Trace> traces = toList();
        traces.sort((o1, o2) -> o2.dateTime.compareTo(o1.dateTime));
        return traces.stream().findFirst();
    }

    public boolean containsCommand(String cmd) {
        return filter(trace -> trace.command.equals(cmd)).size() > 0;
    }
}

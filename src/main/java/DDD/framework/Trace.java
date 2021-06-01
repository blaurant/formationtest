package DDD.framework;

import java.time.LocalDateTime;

public class Trace {

    public final LocalDateTime dateTime;
    public final String command;
    public final String entity;
    public final Throwable error;

    public Trace(LocalDateTime dateTime, String command, String entity, Throwable error) {
        this.dateTime = dateTime;
        this.command = command;
        this.entity = entity;
        this.error = error;
    }

    public Trace(String command, String entity) {
        this(LocalDateTime.now(), command, entity, null);
    }

    public String asString() {
        return this.toString();
        // TODO return Codec.toJson(this);
    }

}

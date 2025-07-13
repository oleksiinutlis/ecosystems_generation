package io.ecosystems_generation.EntityHandling;

public class Request {
    public final RequestType type;
    public final Entity entity;
    public final int[] args;

    public Request(RequestType type, Entity entity, int... args) {
        this.type = type;
        this.entity = entity;
        this.args = args;
    }

    public RequestType getType() { return type; }
    public Entity getEntity() { return entity; }
    public int[] getArgs() { return args; }
}
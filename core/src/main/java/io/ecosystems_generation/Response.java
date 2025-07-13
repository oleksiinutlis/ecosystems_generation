package io.ecosystems_generation;

public class Response {
    public final RequestType type;
    public final Entity entity;
    public final ResponseStatus status;
    public final Object result;

    public Response(RequestType type, Entity entity, ResponseStatus status, Object result) {
        this.type = type;
        this.entity = entity;
        this.status = status;
        this.result = result;
    }

    public RequestType getType() { return type; }
    public Entity getEntity() { return entity; }
    public ResponseStatus getStatus() { return status; }
    public Object getResult() { return result; }
}

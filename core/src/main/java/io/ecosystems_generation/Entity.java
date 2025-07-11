package io.ecosystems_generation;

public interface Entity {
    EntityType getType();
    void act(EntityHandler handler, int x, int y);
}

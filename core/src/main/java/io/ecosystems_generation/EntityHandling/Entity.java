package io.ecosystems_generation.EntityHandling;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Entity {
    EntityType getType();

    // Phase 1: Entity sends intent for current tick
    void sendRequests(EntityHandler handler, int x, int y);

    // Phase 2: Entity receives response(s) from the handler
    void receiveResponse(Response r);
    void draw(SpriteBatch batch, TextureRegion[] textures);
    boolean isMoving();

    int[] randomStep(int x, int y);
    void setDesiredCoordinates(int x, int y);

    int getDesiredX();
    int getDesiredY();
}

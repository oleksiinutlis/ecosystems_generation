package io.ecosystems_generation.EntityHandling;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Food implements Entity{
    @Override
    public EntityType getType() {
        return EntityType.FOOD;
    }

    @Override
    public void sendRequests(EntityHandler handler, int x, int y) {
        //
    }

    @Override
    public void receiveResponse(Response r) {
        //
    }

    @Override
    public void draw(SpriteBatch batch, TextureRegion[] textures) {
        //
    }
}

package io.ecosystems_generation.EntityHandling;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Entity {
    EntityType getType();
    void act(EntityHandler handler, int x, int y);
    void draw(SpriteBatch batch, TextureRegion[] textures);
}

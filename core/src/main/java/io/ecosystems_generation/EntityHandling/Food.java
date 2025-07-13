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

    @Override
    public boolean isMoving() {
//        return desiredX != drawnX || desiredY != drawnY;
        return true;
    }

    @Override
    public int[] randomStep(int x, int y) {
        return new int[0];
    }

    public void setDesiredCoordinates(int x, int y){
//        setDesiredX(Main.getTileSize() * x);
//        setDesiredY(Main.getTileSize() * y);
    }

    public int getDesiredX() {
        return 0;
    }

    public int getDesiredY() {
        return 0;
    }

}

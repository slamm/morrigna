package com.reed.birdseye;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.slamm.morrigna.core.Assets;

public class Particles {

    public void fireUpdateAndDraw(SpriteBatch batch, float deltaTime) {
        Assets.fire.start();
        Assets.fire.draw(batch);
        Assets.fire.setPosition(464, 270);
        Assets.fire.update(deltaTime);
    }

    public void smokeUpdateAndDraw(SpriteBatch batch, float deltaTime) {
        Assets.chimneySmoke.start();
        Assets.chimneySmoke.draw(batch);
        Assets.chimneySmoke.setPosition(1695, 3369);
        Assets.chimneySmoke.update(deltaTime);
    }
}
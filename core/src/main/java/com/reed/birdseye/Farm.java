package com.reed.birdseye;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.slamm.morrigna.core.Assets;
import com.github.slamm.morrigna.core.hud.LevelRenderer;
import com.github.slamm.morrigna.core.map.CoalRenderer;

@SuppressWarnings("unused")
public class Farm {

    private static int cornCount = 0;

    private static String cornCountString = Integer.toString(cornCount);

    private boolean closeEnough;

    private final int distanceFromMaterial = 100;

    private int growing = 0;

    private final int growthRate = 5;

    private final boolean hydrated = false;

    private final CoalRenderer mainGame = new CoalRenderer();

    private float sizeAlpha;

    private float sizeBeta;

    private float time = 0;

    private int x;

    private int y;

    public void closeEnoughToFarm() {
        if (Math.sqrt((x - LevelRenderer.middleX) * (x - LevelRenderer.middleX) + (y - LevelRenderer.middleY)
                * (y - LevelRenderer.middleY)) < distanceFromMaterial) {
            closeEnough = true;
            if (growing == 100) {
                if (Gdx.input.isKeyPressed(Keys.B)) {
                    cornCount++;
                    growing = 0;
                }
            }
            // if (amountOfWater >= 8 && !hydrated) {
            // font.draw(batch, "Press R to Hydrate crops.", 50, 100);
            // if (Gdx.input.isKeyPressed(Keys.R)) {
            // hydrated = true;
            // growthRate = 1;
            // }
            // }
        }
    }

    public void draw(SpriteBatch batch, BitmapFont font) {
        // if (hydrated)
        // batch.draw(Assets.hydration, mainGame.levelX + x - 52,
        // mainGame.levelY + y - 8);
        // standard crop field
        batch.draw(Assets.farm, x, y);
        sizeAlpha = (float) growing / 100;
        sizeBeta = sizeAlpha * 14;
        // actual crops
        for (int i = 510; i <= 570; i += 20) {
            batch.draw(Assets.crop, i, 410, sizeBeta, sizeBeta);
        }
        for (int i = 510; i <= 570; i += 20) {
            batch.draw(Assets.crop, i, 428, sizeBeta, sizeBeta);
        }
        for (int i = 510; i <= 570; i += 20) {
            batch.draw(Assets.crop, i, 446, sizeBeta, sizeBeta);
        }
        for (int i = 510; i <= 570; i += 20) {
            batch.draw(Assets.crop, i, 464, sizeBeta, sizeBeta);
        }
        // draw text
        if (closeEnough) {
            font.draw(batch, "Crops are " + growing + "% grown!", 50, 50);
        }
        if (growing == 100) {
            font.draw(batch, "Press B to obtain crops.", 50, 100);
        }
    }

    public void farmTimer() {
        if (growing < 100) {
            time += Gdx.graphics.getDeltaTime();
        }
        if (time >= growthRate) {
            growing += 1;
            time = 0;
        }
    }
}
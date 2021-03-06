package com.reed.birdseye;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.slamm.morrigna.core.Assets;
import com.github.slamm.morrigna.core.hud.CurrentToolRenderer;
import com.github.slamm.morrigna.core.hud.PointsRenderer;
import com.github.slamm.morrigna.core.hud.TopMenuRenderer;
import com.github.slamm.morrigna.core.map.PlayerRenderer;

public class Coal {

    public static int amountOfCoal = 0;

    private static final int DISTANCE_FROM_MARERIAL = 100;

    public final int x;

    public final int y;

    private final int charWidth = Assets.mainChar.getRegionWidth();

    private float coalTimer = 0;

    private boolean drawResource = true;

    private int height = 58;

    private final float miningRate = 1f;

    private boolean readyForRegen = false;

    private float timer;

    private int width = 64;

    public Coal(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void collect() {
        // starts making the image of the "resource" smaller as b is held down
        if (closeEnough()) {
            if (TopMenuRenderer.currentTool == 0 && CurrentToolRenderer.isTooling) {
                coalTimer += Gdx.graphics.getDeltaTime() * miningRate;
                if (coalTimer > 1 && coalTimer < 2) {
                    width = (int) (64 * .8);
                    height = (int) (58 * .8);
                }
                if (coalTimer > 2 && coalTimer < 3) {
                    width = (int) (64 * .6);
                    height = (int) (58 * .6);
                }
                if (coalTimer > 3 && coalTimer < 4) {
                    width = (int) (64 * .4);
                    height = (int) (58 * .4);
                }
                if (coalTimer > 4 && coalTimer < 5) {
                    width = (int) (64 * .2);
                    height = (int) (58 * .2);
                }
                if (coalTimer > 5) {
                    amountOfCoal += 1;
                    coalTimer = 0;
                    drawResource = false;
                    readyForRegen = true;
                    PointsRenderer.gainExperience(1);
                }
            } else {
                coalTimer = 0;
                width = 64;
                height = 58;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        if (drawResource) {
            batch.draw(Assets.coalOre, x, y, width, height);
        }
    }

    public void mobCollision(Mob mob) {
        // use for pig and mob (use on mob when not under attack)
        if (!readyForRegen) {
            if (mob.x + charWidth > x - 4 && mob.x < x + 20 && mob.y < y + 58 && mob.y > y) {
                mob.direction = 2;
            }
            if (mob.x + charWidth > x + 54 && mob.x < x + 68 && mob.y < y + 58 && mob.y > y) {
                mob.direction = 3;
            }
            if (mob.x + charWidth > x && mob.x < x + 64 && mob.y > y - 14 && mob.y < y + 10) {
                mob.direction = 1;
            }
            if (mob.x + charWidth > x && mob.x < x + 64 && mob.y > y + 48 && mob.y < y + 62) {
                mob.direction = 0;
            }
        }
    }

    public void mobUnderAttackCollision(Mob mob) {
        // if it goes into a rock under attack becomes false... not sure if I should keep this or what
        if (!readyForRegen) {
            if (mob.x + mob.realMob.getRegionWidth() > x - 4 && mob.x < x + 20 && mob.y < y + 58 && mob.y > y) {
                mob.underAttack = false;
            }
            if (mob.x + mob.realMob.getRegionWidth() > x + 54 && mob.x < x + 68 && mob.y < y + 58 && mob.y > y) {
                mob.underAttack = false;
            }
            if (mob.x + mob.realMob.getRegionWidth() > x && mob.x < x + 64 && mob.y > y - 14 && mob.y < y + 10) {
                mob.underAttack = false;
            }
            if (mob.x + mob.realMob.getRegionWidth() > x && mob.x < x + 64 && mob.y > y + 48 && mob.y < y + 62) {
                mob.underAttack = false;
            }
        }
    }

    public void playerCollision() {
        if (!readyForRegen) {
            if (PlayerRenderer.x + charWidth > x - 4 && PlayerRenderer.x < x + 20 && PlayerRenderer.y < y + 58
                    && PlayerRenderer.y > y) {
                PlayerRenderer.isAbleToMoveRight = false;
            }
            if (PlayerRenderer.x + charWidth > x + 54 && PlayerRenderer.x < x + 68 && PlayerRenderer.y < y + 58
                    && PlayerRenderer.y > y) {
                PlayerRenderer.isAbleToMoveLeft = false;
            }
            if (PlayerRenderer.x + charWidth > x && PlayerRenderer.x < x + 64 && PlayerRenderer.y > y - 14
                    && PlayerRenderer.y < y + 10) {
                PlayerRenderer.isAbleToMoveUp = false;
            }
            if (PlayerRenderer.x + charWidth > x && PlayerRenderer.x < x + 64 && PlayerRenderer.y > y + 48
                    && PlayerRenderer.y < y + 62) {
                PlayerRenderer.isAbleToMoveDown = false;
            }
        }
    }

    public void regeneration() {
        // just so I can save one less boolean * amount of coal
        if (!drawResource) {
            readyForRegen = true;
        }
        // add time to timer to keep track when regen is possible
        if (readyForRegen && timer < 130) {
            timer += Gdx.graphics.getDeltaTime();
        }
        // if timer is greater than 120 seconds (2m) it can regen
        if (timer > 120 && distanceBetweenCoalAndPlayer() > 560) {
            readyForRegen = false;
            timer = 0;
            coalTimer = 0;
            width = 64;
            height = 58;
            drawResource = true;
        }
    }

    private boolean closeEnough() {
        // save if it is being drawn or not*
        return Math.sqrt((x - PlayerRenderer.x) * (x - PlayerRenderer.x) + (y - PlayerRenderer.y)
                * (y - PlayerRenderer.y)) < DISTANCE_FROM_MARERIAL;
    }

    private float distanceBetweenCoalAndPlayer() {
        return (float) Math.sqrt((x - PlayerRenderer.x) * (x - PlayerRenderer.x) + (y - PlayerRenderer.y)
                * (y - PlayerRenderer.y));
    }
}

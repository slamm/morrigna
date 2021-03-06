package com.github.slamm.morrigna.core.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.github.slamm.morrigna.core.Assets;
import com.github.slamm.morrigna.core.map.PlayerRenderer;
import com.reed.birdseye.Tutorial;

public class CurrentToolRenderer {

    public static boolean isTooling = false; // great name :)

    private final SkeletonRenderer renderer = new SkeletonRenderer();

    private final Bone root;

    private float timer;

    /**
     * pick stuff
     */
    private final Skeleton toolSkel;

    public CurrentToolRenderer() {
        toolSkel = new Skeleton(Assets.toolsMasterData);
        root = toolSkel.getRootBone();
    }

    public void render(SpriteBatch batch) {
        if ((PlayerRenderer.currentDirection == 2 || PlayerRenderer.currentDirection == 3)
                && !(TopMenuRenderer.currentTool == 5) && !(TopMenuRenderer.currentTool == 4)) {
            if (Gdx.input.isKeyPressed(Keys.B)) {
                isTooling = true;
                renderer.draw(batch, toolSkel);
            } else {
                isTooling = false;
            }
        }
    }

    public void update() {
        timer += Gdx.graphics.getDeltaTime() / 2;
        toolSkel.setSkin("sword");
        Assets.toolsMasterAnim.apply(toolSkel, timer, timer, true, null);
        toolSkel.updateWorldTransform();
        changeTool();
        direction();
    }

    private void changeTool() {
        // TODO: FIX INVENTORY BOX clicking coordinates
        if (TopMenuRenderer.currentTool == 0) {
            toolSkel.setSkin("pick");
            toolSkel.setSlotsToSetupPose();
            toolSkel.updateWorldTransform();
        }
        if (TopMenuRenderer.currentTool == 1 && Tutorial.step >= 2) {
            toolSkel.setSkin("fishingRod");
            toolSkel.setSlotsToSetupPose();
            toolSkel.updateWorldTransform();
        }
        if (TopMenuRenderer.currentTool == 2 && Tutorial.step >= 5) {
            toolSkel.setSkin("hatchet");
            toolSkel.setSlotsToSetupPose();
            toolSkel.updateWorldTransform();
        }
        if (TopMenuRenderer.currentTool == 3 && Tutorial.step >= 7) {
            toolSkel.setSkin("sword");
            toolSkel.setSlotsToSetupPose();
            toolSkel.updateWorldTransform();
        }
    }

    private void direction() {
        if (PlayerRenderer.right) {
            root.setX(LevelRenderer.middleX + 11);
            root.setY(LevelRenderer.middleY - 11);
            toolSkel.setFlipX(false);
        }
        if (PlayerRenderer.left) {
            root.setX(LevelRenderer.middleX + 20);
            root.setY(LevelRenderer.middleY - 11);
            toolSkel.setFlipX(true);
        }
    }
}
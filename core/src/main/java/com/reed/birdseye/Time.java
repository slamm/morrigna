package com.reed.birdseye;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.droidinteractive.box2dlight.PointLight;
import com.droidinteractive.box2dlight.RayHandler;

/**
 * Everything related to time!
 */
public class Time {

    // 5 minute days
    // 1 minute transition
    // 3 minute nights
    // 1 minute transition
    public static float colorAlpha = 0f;

    // set to .2 for proper darkness
    private static float ambientLight = 1f;

    private static boolean isOutdoors = true;

    // set to .9 for proper light
    private final static Color lightColor = new Color(255, 237, 138, colorAlpha);

    private static Array<PointLight> pointLights = new Array<>();

    private static float timeOfDay = 0;

    /**
     * Sets up new lights
     */
    public static void createLights(RayHandler rayHandler) {
        pointLights.add(newLight(rayHandler, 1360, 3722));
        pointLights.add(newLight(rayHandler, 1521, 3722));
        pointLights.add(newLight(rayHandler, 1360, 3516));
        pointLights.add(newLight(rayHandler, 1360, 2954));
        pointLights.add(newLight(rayHandler, 1518, 2954));
        pointLights.add(newLight(rayHandler, 1360, 2578));
        pointLights.add(newLight(rayHandler, 1198, 3634));
        pointLights.add(newLight(rayHandler, 1696, 3636));
        pointLights.add(newLight(rayHandler, 2062, 3636));
        pointLights.add(newLight(rayHandler, 2480, 3636));
        pointLights.add(newLight(rayHandler, 2896, 3636));
        pointLights.add(newLight(rayHandler, 2896, 3402));
        pointLights.add(newLight(rayHandler, 3056, 3402));
        pointLights.add(newLight(rayHandler, 2896, 2836));
        pointLights.add(newLight(rayHandler, 2896, 2448));
        pointLights.add(newLight(rayHandler, 3056, 2448));
        pointLights.add(newLight(rayHandler, 2668, 2116));
        pointLights.add(newLight(rayHandler, 2808, 2116));
        pointLights.add(newLight(rayHandler, 2073, 2468));
        pointLights.add(newLight(rayHandler, 2213, 2468));
        pointLights.add(newLight(rayHandler, 2194, 2942));
        pointLights.add(newLight(rayHandler, 1518, 2454));
        pointLights.add(newLight(rayHandler, 734, 2480));
        pointLights.add(newLight(rayHandler, 813, 2126));
    }

    /**
     * Returns ambient light value
     */
    public static float getAmbientLight() {
        return ambientLight;
    }

    /**
     * Returns current time
     */
    public static float getTimeOfDay() {
        return timeOfDay;
    }

    /**
     * check if time is less then 5 minutes, if so it is day
     */
    public static boolean isDay() {
        return timeOfDay < 300;
    }

    /**
     * detect if night
     */
    public static boolean isNight() {
        return timeOfDay > 420 && timeOfDay < 720;
    }

    public static boolean isOutdoors() {
        return isOutdoors;
    }

    /**
     * Sets ambient light value
     */
    public static void setAmbientLight(float ambientLight) {
        Time.ambientLight = ambientLight;
    }

    public static void setOutdoors(boolean isOutdoors) {
        Time.isOutdoors = isOutdoors;
    }

    /**
     * Sets current time
     */
    public static void setTimeOfDay(float timeOfDay) {
        Time.timeOfDay = timeOfDay;
    }

    public static void update(RayHandler rayHandler) {
        if (Time.isOutdoors()) {
            // Constantly update time
            timeOfDay += Gdx.graphics.getDeltaTime();
            // set time back to 0 if reaches 10 minutes
            if (timeOfDay > 840) {
                timeOfDay = 0;
            }
            sunset();
            sunrise();
            // necessary to do to load lights from a save
            lightColor.set(255, 237, 138, colorAlpha);
            for (int i = 0; i < pointLights.size; i++) {
                pointLights.get(i).setColor(lightColor);
            }
        }
        rayHandler.setAmbientLight(getAmbientLight());
    }

    /**
     * detect if sunrise
     */
    private static boolean isSunrise() {
        return timeOfDay > 720;
    }

    /**
     * detect if sunset
     */
    private static boolean isSunset() {
        return timeOfDay > 300 && timeOfDay < 420;
    }

    private static PointLight newLight(RayHandler rayHandler, int lightX, float lightY) {
        // create new light
        return new PointLight(rayHandler, 2500, lightColor, 175, lightX, lightY);
    }

    private static void sunrise() {
        // appropriate sunrise stuff
        if (isSunrise()) {
            if (colorAlpha >= 0f) {
                colorAlpha -= .0002f;
                lightColor.set(255, 237, 138, colorAlpha);
            }
            if (ambientLight <= 1f) {
                ambientLight += .00015f;
            }
            for (int i = 0; i < pointLights.size; i++) {
                pointLights.get(i).setColor(lightColor);
            }
        }
    }

    private static void sunset() {
        // appropriate sunset effects
        if (isSunset()) {
            if (colorAlpha <= .9f) {
                colorAlpha += .0002f;
                lightColor.set(255, 237, 138, colorAlpha);
            }
            if (ambientLight >= .2f) {
                ambientLight -= .00015f;
            }
            for (int i = 0; i < pointLights.size; i++) {
                pointLights.get(i).setColor(lightColor);
            }
        }
    }
}
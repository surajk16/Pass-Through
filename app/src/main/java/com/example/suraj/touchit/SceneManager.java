package com.example.suraj.touchit;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by suraj on 29-06-2017.
 */

public class SceneManager  {
    private ArrayList<Scene> scenes = new ArrayList<> ();
    public static int ACTIVE_SCENE;

    public SceneManager () {
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene());
    }

    public void update () {
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw (Canvas canvas) {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }

    public void receiveTouch(MotionEvent event) {
        scenes.get(ACTIVE_SCENE).receiveTouch(event);
    }

}

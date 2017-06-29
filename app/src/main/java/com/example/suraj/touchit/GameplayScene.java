package com.example.suraj.touchit;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by suraj on 29-06-2017.
 */

public class GameplayScene implements Scene {


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE=0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {

    }
}

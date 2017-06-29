package com.example.suraj.touchit;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by suraj on 29-06-2017.
 */

public interface Scene {
    public void update ();
    public void draw (Canvas canvas);
    public void terminate ();
    public void receiveTouch(MotionEvent event);
}

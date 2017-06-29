package com.example.suraj.touchit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.example.suraj.touchit.MainThread.canvas;

/**
 * Created by suraj on 29-06-2017.
 */

public class Obstacle implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;


    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {
        this.color = color;
        rectangle = new Rect(0,startY,startX,startY+rectHeight);
        rectangle2 = new Rect (startX+playerGap,startY,Constants.SCREEN_WIDTH,startY+rectHeight);
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void incrementY (float y) {
        rectangle.top+=y;
        rectangle.bottom+=y;
        rectangle2.top+=y;
        rectangle2.bottom+=y;
    }

    public boolean playerCollide (RectPlayer player) {
        return Rect.intersects(player.getRectangle(), rectangle) || Rect.intersects(player.getRectangle(), rectangle2);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);
    }

    @Override
    public void update() {

    }
}

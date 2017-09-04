package com.example.suraj.touchit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


/**
 * Created by suraj on 25-08-2017.
 */

public class PowerUp implements GameObject {
    public int state = 0;
    private Rect rectangle;
    private Paint paint;
    private boolean VISIBLE = false;
    private long startTime;
    private long initTime;
    private Animation coin;
    private AnimationManager animManager;

    public PowerUp(int state, int startX, int startY) {
        this.state = state;
        this.rectangle = new Rect(startX, startY, startX + 75, startY + 75);
        paint = new Paint();

        startTime = initTime = System.currentTimeMillis();
        VISIBLE = false;

        if (state == 0)
            paint.setColor(Color.BLACK);
        else if (state == 1)
            paint.setColor(Color.GREEN);
        else if (state == 2)
            paint.setColor(Color.BLUE);

        BitmapFactory bf = new BitmapFactory();

        Bitmap[] coins = new Bitmap[5];
        coins[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.coin1);
        coins[1] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.coin2);
        coins[2] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.coin3);
        coins[3] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.coin4);
        coins[4] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.coin5);

        coin = new Animation(coins, 0.5f);
        animManager = new AnimationManager(new Animation[]{coin});

    }

    @Override
    public void draw(Canvas canvas) {
        if (state != 2) canvas.drawRect(rectangle, paint);
        else animManager.draw(canvas, rectangle);

    }

    @Override
    public void update() {
        if (state == 0)
            paint.setColor(Color.BLACK);
        else if (state == 1)
            paint.setColor(Color.GREEN);
        else if (state == 2) {
            animManager.update();
            animManager.playAnim(0);
        }
        if (startTime < Constants.INIT_TIME) startTime = Constants.INIT_TIME;
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float) (1 + Math.pow((startTime - initTime), 0.66) / 1000.0) * Constants.SCREEN_HEIGHT / 10000.0f;

        if (VISIBLE) {
            incrementY(speed * elapsedTime);
        } else {
            rectangle.setEmpty();
        }
    }

    public boolean playerCollide(RectPlayer player) {
        return Rect.intersects(player.getRectangle(), rectangle);
    }

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
    }

    public void setVisible() {
        VISIBLE = true;
    }

    public void setInvisible() {
        VISIBLE = false;
    }

    public void setXY(int x, int y) {
        rectangle.set(x, y, x + 75, y + 75);
    }

    public boolean isVisible() {
        return VISIBLE;
    }

    public boolean outofBounds() {
        return rectangle.top > Constants.SCREEN_HEIGHT;
    }

    Rect getRectangle() {
        return rectangle;
    }
}

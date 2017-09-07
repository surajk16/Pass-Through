package com.example.suraj.touchit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by suraj on 29-06-2017.
 */

public class RectPlayer implements GameObject {
    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private Animation climbUp;
    private AnimationManager animManager;


    public RectPlayer(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();

        Bitmap[] idleImages = new Bitmap[1];
        idleImages[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.c);
        //idleImages[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);

        Bitmap[] runRight = new Bitmap[1];
        runRight[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cr);
        //runRight[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walkright);
        //runRight[1] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2right);


        Bitmap[] runLeft = new Bitmap[1];
        runLeft[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cl);
        //runLeft[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walkleft);
        //runLeft[1] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2left);

        Bitmap climb[] = new Bitmap[1];
        climb[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.c);
        //climb[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_climb1);
        //climb[1] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_climb2);

        idle = new Animation(idleImages, 0.5f);
        walkRight = new Animation(runRight, 0.5f);
        walkLeft = new Animation(runLeft, 0.5f);
        climbUp = new Animation(climb, 0.5f);

        animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft, climbUp});
    }

    public Rect getRectangle() {
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle,paint);
        animManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animManager.update();
    }

    public void update (Point point) {
        float oldLeft = rectangle.left;
        float oldTop = rectangle.top;
        rectangle.set(point.x-rectangle.width()/2,point.y-rectangle.height()/2,point.x+rectangle.width()/2,point.y+rectangle.height()/2);

        int state = 0;
        if (rectangle.left - oldLeft > 5)
            state = 1;
        else if (rectangle.left - oldLeft < -5)
            state = 2;
        else if (rectangle.top - oldTop < -5)
            state = 3;

        animManager.playAnim(state);
        animManager.update();
    }
}

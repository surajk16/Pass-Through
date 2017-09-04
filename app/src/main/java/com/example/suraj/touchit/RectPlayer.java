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
        idleImages[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);
        /*idleImages[0] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__000);
        idleImages[1] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__001);
        idleImages[2] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__002);
        idleImages[3] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__003);
        idleImages[4] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__004);
        idleImages[5] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__005);
        idleImages[6] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__006);
        idleImages[7] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__007);
        idleImages[8] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__008);
        idleImages[9] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle__009);
        */

        Bitmap[] runRight = new Bitmap[2];
        runRight[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walkright);
        runRight[1] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2right);
        /*runRight[0] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__000);
        runRight[1] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__001);
        runRight[2] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__002);
        runRight[3] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__003);
        runRight[4] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__004);
        runRight[5] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__005);
        runRight[6] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__006);
        runRight[7] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__007);
        runRight[8] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__008);
        runRight[9] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.run__009);
        */


        Bitmap[] runLeft = new Bitmap[2];
        runLeft[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walkleft);
        runLeft[1] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2left);
        /*runLeft[0] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__000);
        runLeft[1] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__001);
        runLeft[2] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__002);
        runLeft[3] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__003);
        runLeft[4] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__004);
        runLeft[5] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__005);
        runLeft[6] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__006);
        runLeft[7] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__007);
        runLeft[8] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__008);
        runLeft[9] = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.runl__009);
        */

        Bitmap climb[] = new Bitmap[2];
        climb[0] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_climb1);
        climb[1] = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_climb2);

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

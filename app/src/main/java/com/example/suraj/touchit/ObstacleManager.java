package com.example.suraj.touchit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by suraj on 29-06-2017.
 */

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        obstacles = new ArrayList<>();
        Constants.SCORE = 0;

        startTime =initTime = System.currentTimeMillis();

        populateObstacles();
    }

    private void populateObstacles () {
        int currY = -5*Constants.SCREEN_HEIGHT/4;

        while (currY<0)
        {
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH-playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color,xStart,currY,playerGap));
            currY+= obstacleHeight + obstacleGap;
        }
    }

    public void update(int state) {
        if (startTime < Constants.INIT_TIME) startTime = Constants.INIT_TIME;
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime =  System.currentTimeMillis();
        Constants.SCORE += elapsedTime / 750f;
        float speed;
        if (state == 0)
            speed = (float) (1 + Math.pow((startTime - initTime), 0.66) / 1000.0) * Constants.SCREEN_HEIGHT / 10000.0f;
        else
            speed = (float) (1 + Math.pow((startTime - initTime), 0.66) / 1000.0 / 3.0f) * Constants.SCREEN_HEIGHT / 10000.0f;

        for (Obstacle ob : obstacles)
            ob.incrementY(speed * elapsedTime);
        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart,
                    obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
        }

    }


    public void draw (Canvas canvas) {
        for (Obstacle ob: obstacles)
            ob.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(100);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        canvas.drawText("" + df.format(Constants.SCORE), 50, 30 - paint.ascent() + paint.descent(), paint);
    }

    public boolean playerCollide (RectPlayer player) {
        for (Obstacle ob: obstacles)
            if (ob.playerCollide(player))
                return true;

        return false;
    }

    public boolean powerUpCollide(PowerUp powerUp) {
        for (Obstacle ob : obstacles)
            if (ob.powerUpCollide(powerUp))
                return true;

        return false;
    }

}

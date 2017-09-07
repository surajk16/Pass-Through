package com.example.suraj.touchit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by suraj on 29-06-2017.
 */

public class GameplayScene extends Activity implements Scene {

    protected RectPlayer player;
    protected Point playerPoint;
    protected ObstacleManager obstacleManager;
    protected PowerUp powerUp;
    protected boolean gameOver = false;
    protected boolean touchActivated = false;
    protected long gameOverTime;
    protected long gameElapsedTime;
    protected OrientationData orientationData;
    protected long frameTime;
    private Bitmap mFinalbitmap;
    private boolean movingPlayer = false;
    private boolean powerUpActivated = false;
    private int state = 2;
    private Rect r = new Rect();
    private int speedState = 0;
    private long powerupActivatedTime;
    private long powerUpInvisibleTime = 0;

    public GameplayScene() {
        mFinalbitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.bg);
        mFinalbitmap = Bitmap.createScaledBitmap(mFinalbitmap, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);
        player = new RectPlayer(new Rect(100, 100, 210, 210), Color.GREEN);
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(250, 450, 75, Color.BLACK);
        powerUp = new PowerUp(state, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_WIDTH / 4);
        powerUp.setVisible();
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
        gameElapsedTime = 0;
    }

    public void reset() {
        player = new RectPlayer(new Rect(100, 100, 210, 210), Color.GREEN);
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(250, 450, 75, Color.BLACK);
        state = 0;
        powerUp = new PowerUp(state, 50, 50);
        movingPlayer = false;
        powerUpActivated = false;
        touchActivated = false;
        powerUp.setInvisible();
        gameElapsedTime = 0;
        speedState = 0;
        powerUpInvisibleTime = 0;
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text, float height) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        //float y = cHeight / 2f + r.height() / 2f - r.bottom;
        float y = height;
        canvas.drawText(text, x, y, paint);
    }


    @Override
    public void update() {
        if (!gameOver) {
            if (frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            gameElapsedTime += System.currentTimeMillis() - frameTime;
            //Log.i("Time",""+(int)gameElapsedTime/1000);
            frameTime = System.currentTimeMillis();

            if (!powerUpActivated) {
                if (!powerUp.isVisible()) {
                    if (((int) (System.currentTimeMillis() - powerUpInvisibleTime) >= 10000) && gameElapsedTime > 10000) {
                        powerUp.setVisible();
                        Random rand = new Random();
                        state = rand.nextInt(3);
                        do {
                            if (player.getRectangle().top > 150)
                                powerUp.setXY((int) (Math.random() * (Constants.SCREEN_WIDTH - 75)), (int) (Math.random() * (75)));
                            else
                                powerUp.setXY((int) (Math.random() * (Constants.SCREEN_WIDTH - 75)), (int) (Math.random() * (75)) + 150);
                        } while (obstacleManager.powerUpCollide(powerUp));
                        powerUp.state = state;
                    }
                }
            } else {
                activatePowerup(state);
            }


            if (powerUp.isVisible()) {
                if (powerUp.outofBounds()) {
                    powerUp.setInvisible();
                    powerUpInvisibleTime = System.currentTimeMillis();
                    Log.d("set", "invisible");
                }
            }

            if (powerUp.playerCollide(player)) {
                powerupActivatedTime = System.currentTimeMillis();
                powerUpActivated = true;
                activatePowerup(state);
                powerUp.setInvisible();
            }


            if (!touchActivated) {
                if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                    float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                    float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                    float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / (Constants.SENSITIVITY * 150);
                    float ySpeed = 2 * pitch * Constants.SCREEN_HEIGHT / (Constants.SENSITIVITY * 200);

                    playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
                    playerPoint.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0;
                }

                if (playerPoint.x - player.getRectangle().width() / 2 < 0)
                    playerPoint.x = player.getRectangle().width() / 2;
                else if (playerPoint.x + player.getRectangle().width() / 2 > Constants.SCREEN_WIDTH)
                    playerPoint.x = Constants.SCREEN_WIDTH - player.getRectangle().width() / 2;

                if (playerPoint.y - player.getRectangle().height() / 2 < 0)
                    playerPoint.y = player.getRectangle().height() / 2;
                else if (playerPoint.y + player.getRectangle().height() / 2 > Constants.SCREEN_HEIGHT)
                    playerPoint.y = Constants.SCREEN_HEIGHT - player.getRectangle().width() / 2;
            } else
                activatePowerup(state);

            player.update(playerPoint);
            obstacleManager.update(speedState);
            powerUp.update();

            if (obstacleManager.playerCollide(player)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }

        }
    }


    @Override
    public void draw(Canvas canvas) {
        //canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(mFinalbitmap, 0, 0, null);
        if (!gameOver) {
            obstacleManager.draw(canvas);
            powerUp.draw(canvas);
            if (powerUpActivated) {
                drawRect(canvas);
                Log.d("dg", "drawing");
            }
            player.draw(canvas);
        }


        if (gameOver) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(200);
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            drawCenterText(canvas, paint, "" + df.format(Constants.SCORE), 500);

        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY()))
                    movingPlayer = true;
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameOver = false;
                    orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer && touchActivated)
                    playerPoint.set((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if (touchActivated)
                    movingPlayer = false;
                break;
        }
    }

    void activatePowerup(int state) {
        switch (state) {
            case 0:
                touchActivated = true;
                powerUpActivated = true;
                if (System.currentTimeMillis() - powerupActivatedTime >= 5000) {
                    touchActivated = false;
                    powerUpActivated = false;
                    powerUpInvisibleTime = System.currentTimeMillis();
                }
                break;
            case 1:
                speedState = 1;
                if (System.currentTimeMillis() - powerupActivatedTime >= 5000) {
                    powerUpActivated = false;
                    speedState = 0;
                    powerUpInvisibleTime = System.currentTimeMillis();
                }
                break;
            case 2:
                Constants.SCORE += 5;
                powerUpActivated = false;
                powerUpInvisibleTime = System.currentTimeMillis();
        }
    }

    public void drawRect(Canvas canvas) {
        float x = ((5000 - (System.currentTimeMillis() - powerupActivatedTime)) / 5000f * Constants.SCREEN_WIDTH);
        Log.d("dshdsh", "" + (5000 - (System.currentTimeMillis() - powerupActivatedTime)));
        Rect rectangle = new Rect(0, Constants.SCREEN_HEIGHT, (int) x, Constants.SCREEN_HEIGHT - 75);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(rectangle, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        drawCenterText(canvas, paint, "" + df.format((5000 - (System.currentTimeMillis() - powerupActivatedTime)) / 1000f), Constants.SCREEN_HEIGHT);

    }

}

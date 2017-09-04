package com.example.suraj.touchit;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by suraj on 29-06-2017.
 */

public class MainThread extends Thread {
    public static final int MAX_FPS = 60;
    public static Canvas canvas;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime>0)
                    sleep(waitTime);
            } catch (Exception e){e.printStackTrace();}

            totalTime+=System.nanoTime() - startTime;
            frameCount++;
            if (frameCount==MAX_FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount=0;
                totalTime=0;
                //System.out.println(averageFPS);
            }
        }
    }

}

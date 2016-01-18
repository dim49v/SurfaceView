package com.example.admin.surfaceview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Admin on 17.01.2016.
 */

public class Draw extends SurfaceView implements SurfaceHolder.Callback{
    public boolean onTouchEvent(MotionEvent event) {
        drawThread.x0 = event.getX();
        drawThread.y0 = event.getY();
        return true;
    }
    private DrawThread drawThread;
    public Draw(Context context) {
        super(context);
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder(), getResources());
        drawThread.setRunning(true);
        drawThread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            }
            catch (Exception e) {

            }
            }
        }
    }


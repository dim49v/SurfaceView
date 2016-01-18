package com.example.admin.surfaceview;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(new Draw(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class DrawThread extends Thread{
    private boolean runFlag = false;
    Canvas canvas = new Canvas();
    Paint paint = new Paint();
    Paint paint1 = new Paint();
    int ct = 0;
    int i=0;
    long c = System.currentTimeMillis()/1000;
    float x, y, x0, y0, dy, dx;

    int r, g ,b, a;
    private SurfaceHolder surfaceHolder;

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources){
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        paint1.setColor(Color.WHITE);
        paint1.setTextSize(30);
        a=0;
        r=255;
        g=0;
        b=0;
        x=100;
        x0=100;
        y=100;
        y0=100;
        while (runFlag) {
            canvas = null;

            if (System.currentTimeMillis()/1000!=c){
                c=System.currentTimeMillis()/1000;
                ct=i;
                i=0;
            }


            if (y!=y0||x!=x0){
                dy=y0-y;
                dx=x0-x;
                y=y+(float)(Math.signum(dy)*Math.abs(dy)/50);
                x=x+(float)(Math.signum(dx)*Math.abs(dx)/50);
            }
            else
            {
                x=x0;
                y=y0;
            }

            switch (a) {
                case 0:
                    b=b+3;
                    if (b == 255) {
                        a = 1;
                    }
                    break;
                case 1:
                    r=r-3;
                    if (r == 0) {
                        a = 2;
                    }
                    break;
                case 2:
                    g=g+3;
                    if (g == 255) {
                        a = 3;
                    }
                    break;
                case 3:
                    b=b-3;
                    if (b == 0) {
                        a = 4;
                    }
                    break;
                case 4:
                    r=r+3;
                    if (r == 255) {
                        a = 5;
                    }
                    break;
                case 5:
                    g=g-3;
                    if (g == 0) {
                        a = 0;
                    }
                    break;
            }
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    canvas.drawColor(Color.BLACK);
                    paint.setColor(Color.rgb(r, g, b));
                    canvas.drawCircle(x, y, 100, paint);
                    canvas.drawText(String.valueOf(ct),20,40,paint1);
                    i++;
                }


            }
            catch(Exception e){}
            finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}

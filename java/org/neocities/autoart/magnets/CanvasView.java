package org.neocities.autoart.magnets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CanvasView extends View
{
    //public static Game game;
    public double[] playerLocation = new double[2];
    public ArrayList<double[]> magnetLocations = new ArrayList<>();
    public int w;
    public int h;
    private boolean isDragging = false;
    public double greenRingSize;
    public static Game game;
    public static int score = -1;
    public static boolean playing = false;

    final public Handler handler = new Handler();
    final private CreateMagnet createMagnet = new CreateMagnet();

    final private MoveMagnets moveMagnets = new MoveMagnets();

    public double dist(double x1, double y1, double x2, double y2)
    {
        if (((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)) < 0)
            return 1;
        return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));

    }


    private void onInit()
    {

        createMagnet.cv = this;
        moveMagnets.cv = this;

        w = getWidth();
        h = getHeight();

        greenRingSize = w/2;

        playerLocation = new double[2];
        playerLocation[0] = w/2;
        playerLocation[1] = h/2;




        handler.postDelayed(createMagnet, 1000);
        handler.postDelayed(moveMagnets, 1000);

        invalidate();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        greenRingSize /= 1.001;
        int x = (int)event.getX();
        int y = (int)event.getY();

        if (event.getAction() != MotionEvent.ACTION_UP && !isDragging)
        {
            isDragging = dist(x, y, playerLocation[0], playerLocation[1]) < 50;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE && isDragging)
        {
            playerLocation[0] = x;
            playerLocation[1] = y;
            invalidate();
        }
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            isDragging = false;
        }
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        w = getWidth();
        h = getHeight();
        onInit();

    }

    public CanvasView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public CanvasView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

    }

    public CanvasView(Context context)
    {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255, 0, 0, 255);
        canvas.drawCircle((float) playerLocation[0], (float) playerLocation[1], 10, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setARGB(255, 0, 255, 0);
        canvas.drawCircle((float) playerLocation[0], (float) playerLocation[1], (float) greenRingSize, paint);

        paint.setStyle(Paint.Style.FILL);

        for (double[] location: magnetLocations)
        {
            paint.setARGB(255, 255, 0, 0);
            canvas.drawCircle((int) location[0], (int) location[1], 10, paint);
            paint.setARGB(25, 255, 0, 0);
            canvas.drawCircle((int)location[0], (int)location[1], 200, paint);
        }


    }


}

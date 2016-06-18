package org.neocities.autoart.magnets;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;


public class CreateMagnet implements Runnable
{
    public CanvasView cv;

    @Override
    public void run() {

        try {
            if (!CanvasView.playing)
            {
                cv.magnetLocations = new ArrayList<>();
            }
            else
            {

                double x;
                double y;


                do {
                    x = Math.floor(Math.random() * cv.w);
                    y = Math.floor(Math.random() * cv.h);
                }
                while (cv.dist(x, y, cv.playerLocation[0], cv.playerLocation[1]) < cv.greenRingSize);
                double[] pos = new double[2];
                pos[0] = x;
                pos[1] = y;
                cv.magnetLocations.add(pos);
                TextView tv = (TextView) cv.game.findViewById(R.id.score);


                cv.invalidate();
                cv.handler.postDelayed(this, 1000);
            }


        }
        catch(Throwable t)
        {
            TextView tv = (TextView)cv.game.findViewById(R.id.score);
            tv.setText("Oh no!");
        }
    }
}

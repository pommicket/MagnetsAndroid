package org.neocities.autoart.magnets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class MoveMagnets implements Runnable
{

    public CanvasView cv;

    @Override
    public void run() {
        try {
            if (!CanvasView.playing)
                return;
            for (int i = 0; i < cv.magnetLocations.size(); i++) {
                double[] pos = cv.magnetLocations.get(i);
                double d = cv.dist(pos[0], pos[1], cv.playerLocation[0], cv.playerLocation[1]);
                if (d == 0)
                    return;
                double r = Math.sqrt((pos[0] - cv.playerLocation[0]) * (pos[0] - cv.playerLocation[0]) + (pos[1] - cv.playerLocation[1]) * (pos[1] - cv.playerLocation[1]));
                pos[0] -= 1000.0*(pos[0]-cv.playerLocation[0])/(r*d);
                pos[1] -= 1000.0*(pos[1]-cv.playerLocation[1])/(r*d);
                cv.magnetLocations.set(i, pos);
                if (cv.dist(pos[0], pos[1], cv.playerLocation[0], cv.playerLocation[1]) < 10)
                {
                    CanvasView.score = cv.magnetLocations.size();
                    SharedPreferences prefs = cv.game.getSharedPreferences("magnetsHighscore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor;

                    if (!prefs.contains("highscore"))
                    {
                        editor = prefs.edit();
                        editor.putInt("highscore", CanvasView.score);
                        editor.apply();
                    }
                    else
                    {
                        int hs = prefs.getInt("highscore", 0);
                        if (CanvasView.score > hs)
                        {
                            editor = prefs.edit();
                            editor.putInt("highscore", CanvasView.score);
                            editor.apply();
                        }
                    }
                    CanvasView.playing = false;
                    Intent intent = new Intent(CanvasView.game, Menu.class);
                    CanvasView.game.startActivity(intent);
                    cv.magnetLocations = new ArrayList<>();
                    cv.playerLocation[0] = -1000;


                }
            }

            cv.invalidate();
            cv.handler.postDelayed(this, 10);
        }
        catch(Throwable t)
        {
            TextView tv = (TextView)cv.findViewById(R.id.score);
            tv.setText("Oh no!");
        }
    }

}

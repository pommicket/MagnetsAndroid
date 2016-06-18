package org.neocities.autoart.magnets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        CanvasView.game = this;

        CanvasView cv = (CanvasView) findViewById(R.id.canvas);
        cv.magnetLocations = new ArrayList<>();


    }


    @Override
    protected void onStart()
    {
        super.onStart();
        CanvasView cv = (CanvasView) findViewById(R.id.canvas);
        cv.onInit();
    }
}

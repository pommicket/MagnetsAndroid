package org.neocities.autoart.magnets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(android.view.View v)
        {
            CanvasView.playing = true;
            Intent intent = new Intent(Menu.this, Game.class);
            startActivity(intent);
        }
        });
        if (CanvasView.score != -1)
        {
            TextView tv = (TextView) findViewById(R.id.score_text);
            tv.setText("You lost! Score: " + CanvasView.score);
        }
        TextView highscore_text = (TextView) findViewById(R.id.highscore_text);
        SharedPreferences prefs = getSharedPreferences("magnetsHighscore", Context.MODE_PRIVATE);
        if (prefs.contains("highscore"))
        {
            highscore_text.setText("Highscore: " + (prefs.getInt("highscore", 0)));
        }
    }
}

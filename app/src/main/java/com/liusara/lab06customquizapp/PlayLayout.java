package com.liusara.lab06customquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class PlayLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_layout);

        Intent playIntent = getIntent();
        String[] playersArray = playIntent.getStringArrayExtra(MainActivity.PLAYER_INFO);

        TextView playersView = findViewById(R.id.scoreboard);
        assert playersArray != null;
        playersView.setText(Arrays.toString(playersArray));
    }
}

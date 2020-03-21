package com.liusara.lab06customquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class PlayLayout extends AppCompatActivity {
    Button homeButton;
    TextView questionTextView;
    EditText answerEditText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_layout);

        Intent playIntent = getIntent();
        String[] playersArray = playIntent.getStringArrayExtra(MainActivity.PLAYER_INFO);
        Gson gson = new Gson();
        StringBuilder playersInfo = new StringBuilder();
        assert playersArray != null;
        for(String playerInfo:playersArray)
        {
            playersInfo.append(gson.fromJson(playerInfo, Player.class)).append("\n\n");
        }

        TextView playersView = findViewById(R.id.scoreboard);
        playersView.setText(playersInfo);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });

        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        submitButton = findViewById(R.id.clickButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerEditText.setText("");
            }
        });
    }
}

package com.liusara.lab06customquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String PLAYER_INFO = "com.liusara.lab06customquizapp.PLAYER_INFO";
    TextView displayText;
    Button submitButton;
    EditText nameText;
    EditText colorText;
    EditText hobbyText;
    Button playButton;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        displayText = findViewById(R.id.textBox);
        submitButton = findViewById(R.id.clickButton);
        nameText = findViewById(R.id.nameEditText);
        colorText = findViewById(R.id.colorEditText);
        hobbyText = findViewById(R.id.hobbyEditText);

        playButton = findViewById(R.id.playButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("testButton", "Hi "+nameText.getText());
                String displayString = "Welcome " + nameText.getText().toString() + "! Your information has been recorded.";
                displayText.setText(displayString);

                Player player = new Player(nameText.getText().toString(), colorText.getText().toString(), hobbyText.getText().toString());
                Gson gson = new Gson();
                Set<String> currentPlayers = sharedPreferences.getStringSet("storedPlayers", new HashSet<String>());
                System.out.println("Before: " + sharedPreferences.getStringSet("storedPlayers", new HashSet<String>()));
                currentPlayers.add(gson.toJson(player));
                editor.clear();
                editor.putStringSet("storedPlayers", currentPlayers);
                editor.apply();
                System.out.println("Added " + gson.toJson(player));
                System.out.println("After: " + sharedPreferences.getStringSet("storedPlayers", new HashSet<String>()));
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(getApplicationContext(), PlayLayout.class);
                Set<String> currentPlayers = sharedPreferences.getStringSet("storedPlayers", new HashSet<String>());
                String[] playersArray = new String[currentPlayers.size()];
                int index = 0;
                for(String playerInfo:currentPlayers)
                {
                    playersArray[index++] = playerInfo;
                }
                playIntent.putExtra(PLAYER_INFO, playersArray);
                startActivity(playIntent);
            }
        });
    }
}
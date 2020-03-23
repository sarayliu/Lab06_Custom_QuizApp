package com.liusara.lab06customquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
    public static final String PLAYER_OBJECT = "com.liusara.lab06customquizapp.PLAYER_OBJECT";
    TextView displayText;
    Button submitButton;
    EditText nameText;
    EditText colorText;
    EditText hobbyText;
    Button playButton;

    Gson gson;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String displayString;
    Player player;
    Player tempPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        displayText = findViewById(R.id.textBox);
        submitButton = findViewById(R.id.clickButton);
        nameText = findViewById(R.id.nameEditText);
        colorText = findViewById(R.id.colorEditText);
        hobbyText = findViewById(R.id.hobbyEditText);
        playButton = findViewById(R.id.playButton);
        playButton.setEnabled(false);
        playButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("testButton", "Hi "+nameText.getText());
                if(nameText.getText().toString().equals(""))
                {
                    player = new Player();
                    System.out.println("Default constructor initiated");
                    displayString = "Welcome Anonymous Player!";
                    displayText.setText(displayString);
                }
                else
                {
                    String playerName = nameText.getText().toString();
                    String playerColor = colorText.getText().toString();
                    String playerHobby = hobbyText.getText().toString();
                    displayString = "Welcome " + nameText.getText().toString() + "! Your information has been recorded.";
                    displayText.setText(displayString);
                    player = new Player(playerName, playerColor, playerHobby);
                    gson = new Gson();
                    Set<String> currentPlayers = sharedPreferences.getStringSet("storedPlayers", new HashSet<String>());
                    System.out.println("Before: " + sharedPreferences.getStringSet("storedPlayers", new HashSet<String>()));
                    for(String temp:currentPlayers)
                    {
                        tempPlayer = gson.fromJson(temp, Player.class);
                        if(playerName.equals(tempPlayer.getName()) && playerColor.equals(tempPlayer.getInfo().get(0)) && playerHobby.equals(tempPlayer.getInfo().get(1)))
                        {
                            player.setScore(tempPlayer.getScore());
                            break;
                        }
                    }
                    currentPlayers.add(gson.toJson(player));
                    editor = sharedPreferences.edit();
                    editor.clear();
                    editor.putStringSet("storedPlayers", currentPlayers);
                    editor.apply();
                    System.out.println("Added " + gson.toJson(player));
                    System.out.println("After: " + sharedPreferences.getStringSet("storedPlayers", new HashSet<String>()));
                }
                playButton.setEnabled(true);
                playButton.getBackground().setColorFilter(null);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(getApplicationContext(), PlayLayout.class);
                System.out.println(player.toString());
                gson = new Gson();
                playIntent.putExtra(PLAYER_OBJECT, gson.toJson(player));
                startActivity(playIntent);
                System.out.println("playButton clicked");
            }
        });
    }
}
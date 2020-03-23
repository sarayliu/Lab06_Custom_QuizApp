package com.liusara.lab06customquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PlayLayout extends AppCompatActivity {
    Button homeButton;
    TextView playerView;
    String playerString;
    TextView playersView;
    TextView questionTextView;
    EditText answerEditText;
    Button submitAnswerButton;
    Random rand = new Random();
    int num1;
    int num2;
    int answer;
    String displayString;
    Toast toast;
    Player player;
    Gson gson;
    StringBuilder playersInfo;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_layout);

        Intent playIntent = getIntent();
        String[] playersArray = playIntent.getStringArrayExtra(MainActivity.PLAYERS_INFO);
        gson = new Gson();
        playersInfo = new StringBuilder();
        assert playersArray != null;
        for(String playerInfo:playersArray)
        {
            playersInfo.append(gson.fromJson(playerInfo, Player.class)).append("\n\n");
        }

        playersView = findViewById(R.id.scoreboard);
        playersView.setText(playersInfo);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                System.out.println("Returned to Home");
            }
        });

        player = gson.fromJson(playIntent.getStringExtra(MainActivity.PLAYER_OBJECT), Player.class);
        playerView = findViewById(R.id.playerTextView);
        playerString = player.getName() + "\n" + player.getScore() + " points";
        playerView.setText(playerString);

        questionTextView = findViewById(R.id.questionTextView);
        num1 = rand.nextInt(100);
        num2 = rand.nextInt(100);
        answer = num1 + num2;
        displayString = num1 + " + " + num2;
        questionTextView.setText(displayString);
        answerEditText = findViewById(R.id.answerEditText);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);
        submitAnswerButton.setEnabled(false);
        submitAnswerButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        System.out.println("submitAnswerButton disabled");
        answerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(""))
                {
                    submitAnswerButton.setEnabled(false);
                    submitAnswerButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    System.out.println("submitAnswerButton disabled");
                }
                else
                {
                    submitAnswerButton.setEnabled(true);
                    submitAnswerButton.getBackground().setColorFilter(null);
                    System.out.println("submitAnswerButton enabled");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        final String playerName = player.getName();
        final String playerColor = player.getInfo().get(0);
        final String playerHobby = player.getInfo().get(1);
        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer == Integer.parseInt(answerEditText.getText().toString()))
                {
                    toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
                    toast.show();
                    player.setScore(player.getScore() + 1);
                    playerString = player.getName() + "\n" + player.getScore() + " points";
                    playerView.setText(playerString);

                    if(!playerName.equals("Anonymous Player")) {
                        Set<String> currentPlayers = sharedPreferences.getStringSet("storedPlayers", new HashSet<String>());
                        for (String temp : currentPlayers) {
                            Player tempPlayer = gson.fromJson(temp, Player.class);
                            if (playerName.equals(tempPlayer.getName()) && playerColor.equals(tempPlayer.getInfo().get(0)) && playerHobby.equals(tempPlayer.getInfo().get(1))) {
                                currentPlayers.remove(gson.toJson(tempPlayer));
                                break;
                            }
                        }
                        currentPlayers.add(gson.toJson(player));
                        editor = sharedPreferences.edit();
                        editor.clear();
                        editor.putStringSet("storedPlayers", currentPlayers);
                        editor.apply();
                        playersInfo = new StringBuilder();
                        for(String playerInfo:currentPlayers)
                        {
                            playersInfo.append(gson.fromJson(playerInfo, Player.class)).append("\n\n");
                        }
                        playersView.setText(playersInfo);
                    }
                }
                else
                {
                    toast = Toast.makeText(getApplicationContext(), "Sorry, try again!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                answerEditText.setText("");
                num1 = rand.nextInt(100);
                num2 = rand.nextInt(100);
                answer = num1 + num2;
                displayString = num1 + " + " + num2;
                questionTextView.setText(displayString);
                System.out.println("submitAnswerButton clicked");
            }
        });
    }
}

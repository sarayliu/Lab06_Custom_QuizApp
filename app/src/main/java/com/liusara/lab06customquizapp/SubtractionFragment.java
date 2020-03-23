package com.liusara.lab06customquizapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class SubtractionFragment extends Fragment {
    private TextView playerView;
    private String playerString;
    private TextView playersView;
    private TextView questionTextView;
    private EditText answerEditText;
    private Button submitAnswerButton;
    private Random rand = new Random();
    private int num1;
    private int num2;
    private int answer;
    private String displayString;
    private Toast toast;
    private Player player;
    private Gson gson;
    private StringBuilder playersInfo;
    private Set<String> currentPlayers;
    private int playerScore;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subtraction, container, false);

        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
        player = gson.fromJson(getActivity().getIntent().getExtras().getString(MainActivity.PLAYER_OBJECT), Player.class);
        final String playerName = player.getName();
        final String playerColor = player.getInfo().get(0);
        final String playerHobby = player.getInfo().get(1);

        currentPlayers = sharedPreferences.getStringSet("storedPlayers", new HashSet<String>());
        playersInfo = new StringBuilder();
        for(String playerInfo:currentPlayers)
        {
            Player currPlayer = gson.fromJson(playerInfo, Player.class);
            if(playerName.equals(currPlayer.getName()) && playerColor.equals(currPlayer.getInfo().get(0)) && playerHobby.equals(currPlayer.getInfo().get(1)))
            {
                playerScore = currPlayer.getScore();
                player.setScore(playerScore);
            }
            playersInfo.append(currPlayer).append("\n\n");
        }
        System.out.println(playersInfo);
        playersView = view.findViewById(R.id.scoreboard);
        playersView.setText(playersInfo);

        playerView = view.findViewById(R.id.playerTextView);
        playerString = player.getName() + "\n" + playerScore + " points";
        playerView.setText(playerString);

        questionTextView = view.findViewById(R.id.questionTextView);
        num1 = rand.nextInt(100);
        num2 = rand.nextInt(100);
        answer = num1 - num2;
        displayString = num1 + " - " + num2;
        questionTextView.setText(displayString);
        answerEditText = view.findViewById(R.id.answerEditText);
        submitAnswerButton = view.findViewById(R.id.submitAnswerButton);
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

        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer == Integer.parseInt(answerEditText.getText().toString()))
                {
                    toast = Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT);
                    toast.show();
                    player.setScore(player.getScore() + 1);
                    playerString = player.getName() + "\n" + player.getScore() + " points";
                    playerView.setText(playerString);

                    currentPlayers = sharedPreferences.getStringSet("storedPlayers", new HashSet<String>());
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
                else
                {
                    toast = Toast.makeText(getActivity(), "Sorry, try again!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                answerEditText.setText("");
                num1 = rand.nextInt(100);
                num2 = rand.nextInt(100);
                answer = num1 - num2;
                displayString = num1 + " - " + num2;
                questionTextView.setText(displayString);
                System.out.println("submitAnswerButton clicked");
            }
        });

        return view;
    }
}

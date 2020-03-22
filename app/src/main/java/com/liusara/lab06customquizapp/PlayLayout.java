package com.liusara.lab06customquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Random;

public class PlayLayout extends AppCompatActivity {
    Button homeButton;
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

        questionTextView = findViewById(R.id.questionTextView);
        num1 = rand.nextInt(100);
        num2 = rand.nextInt(100);
        answer = num1 + num2;
        displayString = num1 + " + " + num2;
        questionTextView.setText(displayString);
        answerEditText = findViewById(R.id.answerEditText);
        submitAnswerButton = findViewById(R.id.submitAnswerButton);
        submitAnswerButton.setEnabled(false);
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
                    System.out.println("submitAnswerButton disabled");
                }
                else
                {
                    submitAnswerButton.setEnabled(true);
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
                    toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
                    toast.show();
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

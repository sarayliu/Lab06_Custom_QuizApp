package com.liusara.lab06customquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    TextView displayText;
    Button submitButton;
    EditText nameText;
    EditText colorText;
    EditText hobbyText;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        displayText=findViewById(R.id.textBox);
        submitButton=findViewById(R.id.clickButton);
        nameText=findViewById(R.id.nameEditText);
        colorText=findViewById(R.id.colorEditText);
        hobbyText=findViewById(R.id.hobbyEditText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("testButton", "Hi "+nameText.getText());
                String displayString = "Welcome " + nameText.getText().toString() + "!";
                displayText.setText(displayString);

                Player player = new Player(nameText.getText().toString(), colorText.getText().toString(), hobbyText.getText().toString());
                Gson gson = new Gson();
                String currentPlayers = sharedPreferences.getString("storedPlayers", "");
                editor.putString("storedPlayers", currentPlayers + gson.toJson(player));
                editor.apply();
                System.out.println(gson.toJson(player));
            }
        });
    }
}
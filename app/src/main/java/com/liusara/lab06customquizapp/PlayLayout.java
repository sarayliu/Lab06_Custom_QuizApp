package com.liusara.lab06customquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayLayout extends AppCompatActivity {
    Button homeButton;
    Button addButton;
    Button subtractButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_layout);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, new AdditionFragment());
        transaction.addToBackStack(null);
        transaction.commit();

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                System.out.println("Returned to Home");
            }
        });

        addButton = findViewById(R.id.addButton);
        addButton.setEnabled(false);
        addButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.setEnabled(false);
                addButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                subtractButton.setEnabled(true);
                subtractButton.getBackground().setColorFilter(null);
                loadFragment(new AdditionFragment());
            }
        });

        subtractButton = findViewById(R.id.subtractButton);
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractButton.setEnabled(false);
                subtractButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                addButton.setEnabled(true);
                addButton.getBackground().setColorFilter(null);
                loadFragment(new SubtractionFragment());
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transactionReplace = getSupportFragmentManager().beginTransaction();
        transactionReplace.replace(R.id.fragment_container, fragment);
        transactionReplace.addToBackStack(null);
        transactionReplace.commit();
    }
}

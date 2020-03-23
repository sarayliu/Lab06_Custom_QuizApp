package com.liusara.lab06customquizapp;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class Player {
    private String name;
    private Integer score;
    private List<String> info;

    Player(){
        this.name = "Anonymous Player";
        this.score = 0;
        this.info = Arrays.asList("Unknown color", "Unknown hobby");
    }

    Player(String name, String color, String hobby){
        this.name = name;
        this.score = 0;
        this.info = Arrays.asList(color, hobby);
    }

    @Override
    @NonNull
    public String toString()
    {
        return name + ": " + score + " points\nFavorite color: " + info.get(0) + "; Favorite hobby: " + info.get(1);
    }

    String getName()
    {
        return name;
    }

    int getScore()
    {
        return score;
    }

    List<String> getInfo()
    {
        return info;
    }

    void setScore(int newScore)
    {
        score = newScore;
    };
}

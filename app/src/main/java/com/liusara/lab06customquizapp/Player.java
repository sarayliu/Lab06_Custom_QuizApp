package com.liusara.lab06customquizapp;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class Player {
    private String name;
    private Integer score;
    private List<String> info;

    public Player(){
        this.name = "Unknown Player";
        this.score = 0;
        this.info = Arrays.asList("Unknown color", "Unknown hobby");
    }

    public Player(String name, String color, String hobby){
        this.name = name;
        this.score = 0;
        this.info = Arrays.asList(color, hobby);
    }

    @Override
    @NonNull
    public String toString()
    {
        return "Player [name=" + name + ", score=" + score + ", info=" + info + "]";
    }

    public String getName()
    {
        return name;
    }

    public void setScore(int newScore)
    {
        score = newScore;
    };
}

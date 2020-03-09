package com.liusara.lab06customquizapp;

import android.os.Bundle;

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
        this.info = Arrays.asList("Unknown birthday", "Unknown hobby");
    }

    public Player(String name, String birthday, String hobby){
        this.name = name;
        this.score = 0;
        this.info = Arrays.asList(birthday, hobby);
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

    public int getScore()
    {
        return score;
    }
}

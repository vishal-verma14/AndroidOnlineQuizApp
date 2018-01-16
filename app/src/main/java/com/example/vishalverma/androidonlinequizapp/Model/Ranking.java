package com.example.vishalverma.androidonlinequizapp.Model;

/**
 * Created by vishalverma on 13/01/18.
 */

public class Ranking {

    public Ranking(String userName, long score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    private String userName;
    private long score;

    public Ranking() {
    }
}

package com.example.android.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int teamAScore = 0;

    int teamBScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /* Clears the score of each team */

    public void resetScore(View v) {

        teamAScore = 0;
        teamBScore = 0;
        displayTeamScoreA(teamAScore);
        displayTeamScoreB(teamBScore);

    }

    /* Add 3 points to team A's score */

    public void addThreeForTeamA(View v) {

        teamAScore = teamAScore + 3;
        displayTeamScoreA(teamAScore);

    }

        /* Add 2 points to team A's score */

    public void addTwoForTeamA(View v) {

        teamAScore = teamAScore + 2;
        displayTeamScoreA(teamAScore);

    }

    /* Add 1 point to team A's score */

    public void addOneForTeamA(View v) {

        teamAScore = teamAScore + 1;
        displayTeamScoreA(teamAScore);

    }

    /* Add 6 points to team A's score */

    public void addSixForTeamA(View v) {

        teamAScore = teamAScore + 6;
        displayTeamScoreA(teamAScore);

    }

    /* Add 3 points to team B's score */

    public void addThreeForTeamB(View v) {

        teamBScore = teamBScore + 3;
        displayTeamScoreB(teamAScore);

    }

    /* Add 2 points to team B's score */

    public void addTwoForTeamB(View v) {

        teamBScore = teamBScore + 2;
        displayTeamScoreB(teamBScore);

    }

    /* Add 1 point to team B's score */

    public void addOneForTeamB(View v) {

        teamBScore = teamBScore + 1;
        displayTeamScoreB(teamBScore);

    }

    /* Add 6 points to team B's score */

    public void addSixForTeamB(View v) {

        teamBScore = teamBScore + 6;
        displayTeamScoreB(teamBScore);

    }

    /* Displays team A score */

    public void displayTeamScoreA(int score) {
        TextView scoreATxtView = (TextView) findViewById(R.id.team_a_score);
        scoreATxtView.setText(String.valueOf(score));
    }

    /* Displays team B score */

    public void displayTeamScoreB(int score) {
        TextView scoreBTxtView = (TextView) findViewById(R.id.team_b_score);
        scoreBTxtView.setText(String.valueOf(score));
    }
}

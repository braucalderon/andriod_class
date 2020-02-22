package com.example.scorecounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int score1 = 0;
    private static int score2 = 0;
    private static int win = 5;
    private static final String score1_key = "scoreOne";
    private static final String score2_key = "scoreTwo";
    private Button button1, button2;
    private TextView text_score_1, text_score_2, text_winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Buttons
        button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(this);
//        TextViews
        text_score_1 = findViewById(R.id.score_1);
        text_score_2 = findViewById(R.id.score_2);
        text_winner = findViewById(R.id.winner);

//       rotation, save the results
        if(savedInstanceState != null){
            score1 = savedInstanceState.getInt(score1_key);
            score2 = savedInstanceState.getInt(score2_key);
            text_score_1.setText(score1+"");
            text_score_2.setText(score2+"");
        }

    }

    //    rotation, save the results
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(score2_key, score2);
        outState.putInt(score1_key, score1);
    }

    //    rotation, save the results
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        score1 = savedInstanceState.getInt(score1_key);
        score2 = savedInstanceState.getInt(score2_key);
    }

    public boolean winner1(){
        boolean won = false;
        if(score1 == win){
            won = true;

        }
        return won;
    }

    public boolean winner2(){
        boolean won = false;
        if(score2 == win){
            won = true;

        }
        return won;
    }

    public String winner_points(){
        String message = " ";

        if(winner1()){
            int w = score1 - score2;
            String ww = Integer.toString(w);
            message = String.format("Player One won by %s point(s)", ww);
            return message;
        }
        if(winner2()){
            int w = score2 - score1;
            String ww = Integer.toString(w);
            message = String.format("Player Two won by %s points(s)", ww);
            return message;
        }

        return message;

    }


    public void OpenWinner_Acticity(){
        Intent intent = new Intent(this, Winner_Activity.class);
        intent.putExtra("result_key", winner_points());
        startActivity(intent);
    }



    public void reset(){
        score2 = 0;
        score1 = 0;
        text_score_2.setText("0");
        text_score_1.setText("0");
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
//            nested cases
            case R.id.button_1:
            case 1:
                if(winner1()){
                    OpenWinner_Acticity();
                    reset();
                    break;
                }
            case 2:
                if(winner1()==false){
                    score1 ++;
                    text_score_1.setText(Integer.toString(score1));
                    break;
                }

//            nested cases
            case R.id.button_2:
            case 3:
                if(winner2()){
                    OpenWinner_Acticity();
                    reset();
                    break;
                }
            case 4:
                if(winner2()==false){
                    score2 ++;
                    text_score_2.setText(Integer.toString(score2));
                    break;
                }


        }

    }
}

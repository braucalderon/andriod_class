package com.example.scorecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Winner_Activity extends AppCompatActivity implements View.OnClickListener{

    private TextView text_result;
    private Button button_newGame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_);
        text_result = findViewById(R.id.result);
        button_newGame = findViewById(R.id.button_new_game);
        button_newGame.setOnClickListener(this);

        result();

    }

    public void result(){
        Intent intent = getIntent();
        String r = intent.getStringExtra("result_key");
        text_result.setText(r);
    }

    public void Main_Acticity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.button_new_game:
                Main_Acticity();
                break;

        }
    }
}

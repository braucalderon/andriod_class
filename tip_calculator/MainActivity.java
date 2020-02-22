package com.example.tip_calculator2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static SeekBar seek_bar;
    private static TextView text_tip, total_bill, bill_tip;
    private static EditText edit_text;
    private static String value, value1;
    //    private static int textTip;
//    private static double total, total1;
    private static int progress_value = 0;
    private Button  button0, button1, button2, button3, button4, button5, button6, button7, button8,
            button9, buttonCalculate, buttonPeriod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seek_bar = findViewById(R.id.seekbar_percent);
        text_tip = findViewById(R.id.tip);
        edit_text = findViewById(R.id.enter_amount);
        total_bill = findViewById(R.id.total_bill);
        bill_tip = findViewById(R.id.bill_tip);
        seekbar();

////        button find by id
//        button0 = findViewById(R.id.button_zero);
//        button1 = findViewById(R.id.button_one);
//        button2 = findViewById(R.id.button_two);
//        button3 = findViewById(R.id.button_three);
//        button4 = findViewById(R.id.button_four);
//        button5 = findViewById(R.id.button_five);
//        button6 = findViewById(R.id.button_six);
//        button7 = findViewById(R.id.button_seven);
//        button8 = findViewById(R.id.button_eight);
//        button9 = findViewById(R.id.button_nine);
        buttonCalculate = findViewById(R.id.button_calculate);
//        buttonPeriod = findViewById(R.id.button_zero);
//
////        click on Listener
//        button0.setOnClickListener(this);
//        button1.setOnClickListener(this);
//        button2.setOnClickListener(this);
//        button3.setOnClickListener(this);
//        button4.setOnClickListener(this);
//        button5.setOnClickListener(this);
//        button6.setOnClickListener(this);
//        button7.setOnClickListener(this);
//        button8.setOnClickListener(this);
//        button9.setOnClickListener(this);
        buttonCalculate.setOnClickListener(this);
//        buttonPeriod.setOnClickListener(this);

    }

    public void seekbar(){
        text_tip.setText(seek_bar.getProgress()+ "%" + " from " + seek_bar.getMax() + "%");



//        adding a listener

        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        text_tip.setText(progress + "%"+ " from " + seek_bar.getMax() +"%");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        text_tip.setText(progress_value +"%" + " from " + seek_bar.getMax() + "%");



                    }
                }
        );


    }

    public double calculate_tip(){
        double seekbar_value, total_bill;
        double percentage = 100;

        value = edit_text.getText().toString();
        seekbar_value = progress_value / percentage;
        total_bill = seekbar_value * Integer.parseInt(value);

        return total_bill;
    }

    public double total_bill(){

        double totalBill;
        value = edit_text.getText().toString();

        totalBill = Integer.parseInt(value) + calculate_tip();

        return totalBill;




    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_calculate:
                String tip = Double.toString(calculate_tip());
                String dollars_tip = String.format("$ %s", tip);

                String bill = Double.toString(total_bill());
                String dollars_bill = String.format("$ %s", bill);

                bill_tip.setText(dollars_tip);
                total_bill.setText(dollars_bill);
                break;


        }


    }
}



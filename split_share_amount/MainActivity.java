package com.example.tipcalculator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static SeekBar seek_bar;
    private static TextView text_tip, total_bill, bill_tip, text_per_person;

    private static EditText edit_text;
    private static String value;
    private String spinner_string = "";
    private static int radio_value = 0;

    private static final String key_total_bill = "totalbill";
    private static final String key_tip = "tip";
    private static final String key_per_person = "perperson";

    private static int progress_value = 0;
    private Button buttonCalculate, share_button;
    private static double total_bill_value = 0.0;
    private static double per_person_value = 0.0;
    private static double tip_value = 0.0;
    private RadioButton btn_no, btn_tip, btn_total;
    Spinner spinner_numbers;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TextViews to gather info
        seek_bar = findViewById(R.id.seekbar_percent);
        text_tip = findViewById(R.id.tip);
        edit_text = findViewById(R.id.enter_amount);

        // TexViews for final results
        total_bill = findViewById(R.id.total_bill);
        bill_tip = findViewById(R.id.bill_tip);
        text_per_person = findViewById(R.id.per_person);

        // Radio Buttons
        btn_no = findViewById(R.id.radio_btn_no);
        btn_tip = findViewById(R.id.radio_btn_tip);
        btn_total = findViewById(R.id.radio_btn_total);


        seekbar();

//      Buttons
        buttonCalculate = findViewById(R.id.button_calculate);
        buttonCalculate.setOnClickListener(this);
        share_button = findViewById(R.id.button_share);
        share_button.setOnClickListener(this);



//      Spinner
        spinner_numbers = findViewById(R.id.spinner);
        if (spinner_numbers != null) {
            spinner_numbers.setOnItemSelectedListener(this);
        }
        adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_number, android.R.layout.simple_spinner_item);

        // specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        if (spinner_numbers != null) {
            spinner_numbers.setAdapter(adapter);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_info:
                displayToast(getString(R.string.split_bill));
                break;

            case R.id.action_share:
                displayToast(getString(R.string.share_results));
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    // rotation, save the result
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(key_total_bill, total_bill_value);
        outState.putDouble(key_per_person, per_person_value);
        outState.putDouble(key_tip, tip_value);
    }

    // rotation, save the result
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            total_bill_value = savedInstanceState.getDouble(key_total_bill);
            per_person_value = savedInstanceState.getDouble(key_per_person);
            tip_value = savedInstanceState.getDouble(key_tip);

            total_bill.setText(total_bill_value+"");
            bill_tip.setText(tip_value+"");
            text_per_person.setText(per_person_value+"");
        }
        total_bill_value = savedInstanceState.getDouble(key_total_bill);
        per_person_value = savedInstanceState.getDouble(key_per_person);
        tip_value = savedInstanceState.getDouble(key_tip);

    }

    // Spinner (array of items in the drop down)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        spinner_string = adapterView.getItemAtPosition(position).toString();
        displayToast("The bill will be split by " + spinner_string + " person(s)");

    }

    // Radio Buttons, ClickListener based on boolean to check if button have been clicked or not
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // prompts the user to send a text message  with the information about the bill
    public void send_message(){
        String bill = edit_text.getText().toString();
        String total = total_bill.getText().toString();
        String tip = bill_tip.getText().toString();
        String per_person = text_per_person.getText().toString();

        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Message send to: ")
                .setText("Calculator tip breakdown: " + "\n" +"Bill: $" +bill + "\n"
                        +"Total Bill: " + total + "\n" +"Tip: "+ tip + "\n" + "Per Person: "+per_person)
                .startChooser();
    }

    public void radioButton(View v) {

        // if radio button is checked
        boolean checked = ((RadioButton) v).isChecked();
        switch ((v.getId())) {

            case R.id.radio_btn_no:
                if (checked) {
                    // if button is clicked enables buttonCalculate, and assign a value to the global variable for later use
                    buttonCalculate.setEnabled(true);
                    radio_value = 1;
                    break;
                }
            case R.id.radio_btn_tip:
                if(checked){
                    buttonCalculate.setEnabled(true);
                    radio_value = 2;
                    break;
                }
            case R.id.radio_btn_total:
                if(checked){
                    buttonCalculate.setEnabled(true);
                    radio_value = 3;
                    break;
                }


        }
    }


    // display message
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }

    // Seek Bar percentage
    public void seekbar() {
        text_tip.setText(seek_bar.getProgress() + "%" + " from " + seek_bar.getMax() + "%");


//       adding a listener to the seek bar
        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // global variable holds the value of the seekBar for later use
                        progress_value = progress;
                        text_tip.setText(progress + "%" + " from " + seek_bar.getMax() + "%");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // shows the percentage desired by the user
                        text_tip.setText(progress_value + "%" + " from " + seek_bar.getMax() + "%");
                    }
                }
        );

    }

    // calculate tip
    public double calculate_tip(double decimal) {
        double num = 100;
        value = edit_text.getText().toString();
        double percentage = progress_value / num;
        tip_value = Double.parseDouble(value) * percentage + decimal;
        return tip_value;

    }
    // calculates tip and bill, with a round-up the nearest 10 in the tip only
    public void tip_RoundedUp() {
        String tip = Double.toString(Math.round(calculate_tip(0.5)));
        String dollars_tip = String.format("$ %s", tip);

        String bill = Double.toString(total_bill((Math.round(calculate_tip(0.5)))));
        String dollars_bill = String.format("$ %s", bill);

        bill_tip.setText(dollars_tip);
        total_bill.setText(dollars_bill);

    }


    // calculate total bill plus the tip
    public double total_bill(double tip) {

        value = edit_text.getText().toString();
        total_bill_value = tip + Integer.parseInt(value);
        return  total_bill_value;

    }

    // calculates the bill based on the number of people splitting the bill
    public void total_per_person(int num_person, double total) {

        per_person_value = total / num_person;
        String dollars_per_person = String.format("$ %s", per_person_value);
        text_per_person.setText(dollars_per_person);

    }

    // calculates the exact tip and bill
    public void calculation() {

        String tip = Double.toString(calculate_tip(0));
        String dollars_tip = String.format("$ %s", tip);

        String bill = Double.toString(total_bill(calculate_tip(0)));
        String dollars_bill = String.format("$ %s", bill);

        bill_tip.setText(dollars_tip);
        total_bill.setText(dollars_bill);

    }

    // calculates tip and bill (bill rounded up)
    public void total_bill_roundedup() {

        String tip = Double.toString(calculate_tip(0));
        String dollars_tip = String.format("$ %s", tip);

        String bill = Double.toString(Math.round(total_bill(calculate_tip(0))+0.5));
        String dollars_bill = String.format("$ %s", bill);

        bill_tip.setText(dollars_tip);
        total_bill.setText(dollars_bill);

    }

    // reset
    public void reset(){
        btn_no.setChecked(false);
        btn_tip.setChecked(false);
        btn_total.setChecked(false);
        buttonCalculate.setEnabled(false);
    }


    @Override
    public void onClick(View v) {

        value = edit_text.getText().toString();

        // spinner (the number of people splitting the bill)
        int spiner_items = Integer.parseInt(spinner_string);
        double value_int = Integer.parseInt(value);


        switch (v.getId()) {
            case R.id.button_calculate:
            case 1:
                if(value_int > 0 && radio_value == 1 ) {
                    calculation();
                    total_per_person(spiner_items , total_bill(calculate_tip(0)));
                    share_button.setEnabled(true);
                    reset();
                    break;
                }
            case 2:
                if(value_int > 0 && radio_value == 2 ) {
                    tip_RoundedUp();
                    total_per_person(spiner_items , total_bill(Math.round((calculate_tip(0.5)))));
                    share_button.setEnabled(true);
                    reset();
                    break;
                }
            case 3:
                if(value_int > 0 && radio_value == 3){
                    total_bill_roundedup();
                    total_per_person(spiner_items, total_bill(Math.round(calculate_tip(0.5))));
                    share_button.setEnabled(true);
                    reset();
                    break;

                }
            case R.id.button_share:
                send_message();
                share_button.setEnabled(false);





        }

    }
}

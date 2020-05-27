package com.example.covidconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    private TextView mCountry_TextView;
    private TextView mInfected_TextView;
    private TextView mCure_TextView;
    private TextView mDeath_TextView;
    private TextView mCountryCode_TextView;
    private TextView mNewCases_TextView;
    private TextView mNewDeaths_TextView;
    private TextView mDate_TextView;
    private TextView mNewRecovered_TextView;
    private String mMap_country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPage();

            }
        });

        mCountry_TextView = findViewById(R.id.textViewDetail_country);
        mInfected_TextView = findViewById(R.id.textViewDetail_infected);
        mCure_TextView = findViewById(R.id.textViewDetail_cure);
        mDeath_TextView = findViewById(R.id.textViewDetail_death);
        mCountryCode_TextView = findViewById(R.id.textViewDetail_countryCode);
        mNewCases_TextView = findViewById(R.id.textViewDetail_newCases);
        mNewDeaths_TextView = findViewById(R.id.textViewDetail_newDeaths);
        mDate_TextView = findViewById(R.id.textViewDetail_date);
        mNewRecovered_TextView = findViewById(R.id.textViewDetail_recovered);

//        getting data from incoming intent
        mCountry_TextView.setText(getIntent().getStringExtra("country"));
        mInfected_TextView.setText("Infected: " + getIntent().getStringExtra("infected"));
        mCure_TextView.setText("Recovered: "+getIntent().getStringExtra("cure"));
        mDeath_TextView.setText("Total Deaths: "+getIntent().getStringExtra("death"));
        mCountryCode_TextView.setText("Country Code: "+getIntent().getStringExtra("country_code"));
        mNewCases_TextView.setText("New Cases: "+getIntent().getStringExtra("newCases"));
        mNewDeaths_TextView.setText("New Deaths: "+getIntent().getStringExtra("newDeaths"));
        mDate_TextView.setText("Updated on"+"\n"+getIntent().getStringExtra("date"));
        mNewRecovered_TextView.setText("New Recovered: " + getIntent().getStringExtra("recovered"));


    }

    private void mainPage(){
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    public void map_country(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        mMap_country = mCountry_TextView.getText().toString();
        String clear = "";
        intent.putExtra("country", mMap_country);
        intent.putExtra("clear", clear);
        startActivity(intent);


    }
}// end class

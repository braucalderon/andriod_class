package com.example.foodieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AddMeal extends AppCompatActivity {

    private EditText addpic_edittext;
    private EditText namemeal_edittext;
    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        namemeal_edittext = findViewById(R.id.meal_name);



    }

    public void addMeal(View view) {
        // getting strings from the array
        String[] food = getResources().getStringArray(R.array.food_name);
        items = new ArrayList<String>(food.length);

        String new_foodItem = namemeal_edittext.getText().toString();
        for(int i = 0; i < food.length+1; i++){
            items.add(food[i]);
        }
        items.add(new_foodItem);



    }
}

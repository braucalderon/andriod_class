package com.example.foodieapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Food> mFoodData;
    private FoodAdapter mFoodAdapter;
    private int gridColumnCount;
    private ItemTouchHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToast("Add item is not working");
            }
        });

        // initialize the RecyclerView
        mRecyclerView = findViewById(R.id.recyclerView);

        // set the layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // initialize the ArrayList that will contain the data
        mFoodData = new ArrayList<>();

        // initialize the adapter and set it to the RecyclerView
        mFoodAdapter = new FoodAdapter(this, mFoodData);
        mRecyclerView.setAdapter(mFoodAdapter);

        // integers.xml, runtime will take care of deciding integer to use
        // integer will dictate the number of columns for landscape layout
        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        // change the LinearLayout Manager for the RecyclerView to a GridLayoutManager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));


        // get the data
        initializeData();


    }

    public void addNewMeal(){
        Intent intent = new Intent(this, AddMeal.class);
        startActivity(intent);
    }

    public void initializeData() {
        String[] foodName = getResources().getStringArray(R.array.food_name);
        String[] foodInfo = getResources().getStringArray(R.array.food_info);
        String[] foodRecipe = getResources().getStringArray(R.array.food_recipes);

        // TypedArray helps the gather images from string.xml file
        TypedArray foodImages = getResources().obtainTypedArray(R.array.food_image);

        mFoodData.clear();

        for(int i = 0; i < foodName.length; i ++){
            mFoodData.add(new Food(foodName[i], foodInfo[i], foodRecipe[i], foodImages.getResourceId(i,0)));
        }

        foodImages.recycle();

        // notify the adapter of the change
        mFoodAdapter.notifyDataSetChanged();

        itemDeleteItem();


    }

    public void itemDeleteItem(){
        helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT|
                ItemTouchHelper.LEFT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                int from = viewHolder.getAdapterPosition();
//                int to = target.getAdapterPosition();
//
//                Collections.swap(mFoodData, from, to);
//                mFoodAdapter.notifyItemMoved(from, to);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                mFoodData.remove(viewHolder.getAdapterPosition());
                mFoodAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                displayToast("Item has been deleted");
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
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
            case R.id.action_refresh:
                initializeData();
                return true;
            case R.id.action_instructions:
                displayToast("Swipe LEFT or RIGHT to delete the item");
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }



}

package com.example.covidconnect;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    private static String TAG = "MainPage";
    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;
    private ArrayList<Item> mItemsData;
    private RequestQueue mRequestQueue;
    private TextView mDate_TextView;
    private String url = "https://api.covid19api.com/summary";
    private String date;
    private int gridColumnCount;
    private ItemTouchHelper helper;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    public MainPage(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity();

            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mDate_TextView = findViewById(R.id.textView_date);

//      set recyclerView layoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


//      new array to enter the new data
        mItemsData = new ArrayList<>();

        mItemAdapter = new ItemAdapter(this, mItemsData);
//      set the adapter to the recycler view
        mRecyclerView.setAdapter(mItemAdapter);

        // integers.xml, runtime will take care of deciding integer to use
        // integer will dictate the number of columns for landscape layout
        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        // change the LinearLayout Manager for the RecyclerView to a GridLayoutManager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));


        mRequestQueue = Volley.newRequestQueue(this);

        parseJson();

    }


    private void parseJson() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Countries");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject countries = jsonArray.getJSONObject(i);

                                String country = countries.getString("Country");
                                String infected = String.valueOf(countries.getInt("TotalConfirmed"));
                                String cure = String.valueOf(countries.getInt("TotalRecovered"));
                                String death = countries.getString("TotalDeaths");
                                String countryCode = countries.getString("CountryCode");
                                String recovered = String.valueOf(countries.getInt("NewRecovered"));
                                String newDeaths = String.valueOf(countries.getInt("NewDeaths"));
                                String newCases = String.valueOf(countries.getInt("NewConfirmed"));

                                date = countries.getString("Date");
                                mItemsData.add(new Item(country, infected, cure, death, countryCode,
                                        recovered, date, newDeaths, newCases));

                            }
                            mDate_TextView.append("Stay up-to-date with the coronavirus");
                            mItemAdapter = new ItemAdapter(MainPage.this, mItemsData);
                            mRecyclerView.setAdapter(mItemAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
        mItemAdapter.notifyDataSetChanged();
        itemDeleteItem();
    }

    private void mainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

//    remove individual cards from the layout.
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

                mItemsData.remove(viewHolder.getAdapterPosition());
                mItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.refresh:
                Intent intent = new Intent(this, MainPage.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
} // end class

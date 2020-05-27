package com.example.covidconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn_enter;
    private RecyclerView mRecyclerView;
    private ArrayList<Item> mItemsData;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void mainPageActivity(){
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    private void loggingActivity(){
        Intent intent = new Intent(this, Logging.class);
        startActivity(intent);
    }



    public void launchActivity(View view) {
        switch (view.getId()){
            case R.id.cardView_corona:
                mainPageActivity();
                break;
            case R.id.cardView_search:
                loggingActivity();
                break;

        }
    }
}// end class

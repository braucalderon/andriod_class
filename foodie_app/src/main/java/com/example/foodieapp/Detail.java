package com.example.foodieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail extends AppCompatActivity {

    private TextView mDetailTitle;
    private ImageView mDetailImage;
    private TextView mDetailRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailImage = findViewById(R.id.detail_imageView);
        mDetailTitle = findViewById(R.id.detail_textView_title);
        mDetailRecipe = findViewById(R.id.detail_textViewRecipe);

        // get the data from incoming Intent
        Glide.with(this)
                .load(getIntent().getIntExtra("image",0))
                .into(mDetailImage);
        mDetailTitle.setText(getIntent().getStringExtra("title"));
        mDetailRecipe.setText(getIntent().getStringExtra("recipe"));
    }

    // implicit intent
    public void website(View view) {
        String url = "www.countryliving.com/food-drinks/g648/quick-easy-dinner-recipes/";
        Uri website = Uri.parse("http://"+url);
        Intent intent = new Intent(Intent.ACTION_VIEW, website);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Log.d("Implicit_Intent", "Cant handle this");
        }

    }
}

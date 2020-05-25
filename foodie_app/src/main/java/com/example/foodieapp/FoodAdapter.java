package com.example.foodieapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private ArrayList<Food> mFoodData;
    private Context mContext;


    public FoodAdapter(Context context, ArrayList<Food> foodData){
        this.mFoodData = foodData;
        this.mContext = context;
    }


    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        // get current sport
        Food currentFood = mFoodData.get(position);
        holder.bindTo(currentFood);
    }

    @Override
    public int getItemCount() {
        return mFoodData.size();
    }

    // create a ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleFood;
        private TextView mInfoFood;
        private TextView mRecipeFood;
        private ImageView mFoodImage;

        public ViewHolder(View itemView){
            super(itemView);

            // Initialize the views
            mTitleFood = itemView.findViewById(R.id.textView_title);
            mInfoFood = itemView.findViewById(R.id.textView_description);
            mFoodImage = itemView.findViewById(R.id.imageView);


            // set the onclickListener to the entire view
            itemView.setOnClickListener(this);


        }
        public void bindTo(Food currentFood){
            mTitleFood.setText(currentFood.getTitle());
            mInfoFood.setText(currentFood.getInfo());
            Glide.with(mContext)
                    .load(currentFood.getImageSource())
                    .into(mFoodImage);
        }


        @Override
        public void onClick(View v) {
            Food detailFood = mFoodData.get(getAdapterPosition());
            Intent detail_Intent = new Intent(mContext, Detail.class);

            detail_Intent.putExtra("title", detailFood.getTitle());
            detail_Intent.putExtra("image", detailFood.getImageSource());
            detail_Intent.putExtra("recipe", detailFood.getRecipe());


            mContext.startActivity(detail_Intent);

        }
    }
}

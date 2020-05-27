package com.example.covidconnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<Item> mItemData;
    private Context mContext;


    public ItemAdapter(Context context, ArrayList<Item> itemData) {
        mContext = context;
        mItemData = itemData;

    }



    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_items, parent, false));

    }



    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
//        items will be saved in currentItem
        Item currentItem = mItemData.get(position);
        holder.bindTo(currentItem);

    }

    @Override
    public int getItemCount() {
        return mItemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mCountry_TextView;
        private TextView mInfected_TextView;
        private TextView mCure_TextView;
        private TextView mDeath_TextView;
        private TextView mCountryCode_TextView;
        private TextView mNewCases_TextView;
        private TextView mNewDeaths_TextView;
        private TextView mDate_TextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCountry_TextView = itemView.findViewById(R.id.textView_country);
            mInfected_TextView = itemView.findViewById(R.id.textView_infected);
            mCure_TextView = itemView.findViewById(R.id.textView_cure);
            mDeath_TextView = itemView.findViewById(R.id.textView_death);

            itemView.setOnClickListener(this);
        }

        public void bindTo(Item list1) {
            mCountry_TextView.setText(list1.getCountry());
            mInfected_TextView.setText("Infected: " + list1.getInfected());
            mCure_TextView.setText("Recovered: " + list1.getCure());
            mDeath_TextView.setText("Total Deaths: " + list1.getDeath());
        }

        @Override
        public void onClick(View v) {
            Item detail = mItemData.get(getAdapterPosition());
            Intent detail_intent = new Intent(mContext, DetailActivity.class);

            detail_intent.putExtra("country", detail.getCountry());
            detail_intent.putExtra("infected", detail.getInfected());
            detail_intent.putExtra("cure", detail.getCure());
            detail_intent.putExtra("death", detail.getDeath());
            detail_intent.putExtra("country_code", detail.getCountry_code());
            detail_intent.putExtra("recovered", detail.getRecovered());
            detail_intent.putExtra("date", detail.getDate());
            detail_intent.putExtra("newDeaths", detail.getNewDeaths());
            detail_intent.putExtra("newCases", detail.getNewCases());

            mContext.startActivity(detail_intent);

        }
    }
}

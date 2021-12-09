package com.example.foodpriceinquiry.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpriceinquiry.R;
import com.example.foodpriceinquiry.repository.FoodPriceVO;

import java.util.List;

public class FoodPriceAdapter extends RecyclerView.Adapter<FoodPriceViewHolder> {

    private List<FoodPriceVO> foodPriceList;

    public FoodPriceAdapter(List<FoodPriceVO> foodPriceList) {
        this.foodPriceList = foodPriceList;
    }

    @NonNull
    @Override
    public FoodPriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list, parent, false);
        FoodPriceViewHolder holder = new FoodPriceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPriceViewHolder holder, int position) {
        holder.bind(foodPriceList.get(position));
    }

    @Override
    public int getItemCount() {
        if(foodPriceList != null) {
            return foodPriceList.size();
        }
        return 0;
    }
}


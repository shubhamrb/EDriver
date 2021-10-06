package com.example.driver.adapter.order;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver.R;
import com.example.driver.model.order.Orders;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private ArrayList<Orders> listData;
    private final OnMeneuClickListnser onMenuListClicklistener;

    Context mContext;
    int select;

    public OrderAdapter(ArrayList<Orders> list, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.listData = list;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.consignor_nearby_delivery, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orders data = listData.get(position);


        holder.tvOrderName.setText(data.getOrderNumber());
        holder.tvCustomerName.setText(data.getCustomerName());
        holder.tvOrderDate.setText(data.getOrderDate());
        holder.orderClick.setOnClickListener(v->{
            onMenuListClicklistener.onOrderClick(data);
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvOrderName, tvCustomerName, tvOrderDate;
        MaterialCardView orderClick;

        MyViewHolder(View view) {
            super(view);
            tvCustomerName = view.findViewById(R.id.tvCustomerName);
            tvOrderName = view.findViewById(R.id.tvOrderName);
            tvOrderDate = view.findViewById(R.id.tvOrderDate);
            orderClick = view.findViewById(R.id.orderClick);


        }
    }

    public interface OnMeneuClickListnser {
        void onOrderClick(Orders data);
    }


}



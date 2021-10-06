package com.example.driver.adapter.order;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver.R;
import com.example.driver.model.order.OrderCustomerDetails;
import com.example.driver.model.order.OrderProduct;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.MyViewHolder> {
    private ArrayList<OrderProduct> orderProductArrayList;

    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;
    public OrderProductAdapter(ArrayList<OrderProduct> getList, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.orderProductArrayList = getList;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.homemenu, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderProduct data = orderProductArrayList.get(position);

    }

    @Override
    public int getItemCount() {
        return orderProductArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView number,nameText;
        MaterialCardView openpanel;
        View viewBorder;
        MyViewHolder(View view) {
            super(view);

            openpanel=view.findViewById(R.id.openpanel);
            viewBorder=view.findViewById(R.id.border);
            nameText=view.findViewById(R.id.name);



        }
    }
    public interface OnMeneuClickListnser{
        void onOptionClick(String liveTest,int pos);
    }


 }



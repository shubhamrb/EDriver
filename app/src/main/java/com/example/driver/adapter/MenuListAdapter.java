package com.example.driver.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.driver.R;
import com.google.android.material.card.MaterialCardView;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private String arrItems[];
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;
    public MenuListAdapter(String menuList[], OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.arrItems = menuList;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.homemenu, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String menuName=arrItems[position];

        holder.nameText.setText(menuName);
        //holder.number.setText(ticket.getTicketNumber());


        holder.openpanel.setOnClickListener(v -> {
            select=position;
            onMenuListClicklistener.onOptionClick(menuName,position);
             notifyDataSetChanged();
        });
if(select==position){
    holder.viewBorder.setVisibility(View.VISIBLE);
}else{
    holder.viewBorder.setVisibility(View.GONE);
}

    }

    @Override
    public int getItemCount() {
        return arrItems.length;
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



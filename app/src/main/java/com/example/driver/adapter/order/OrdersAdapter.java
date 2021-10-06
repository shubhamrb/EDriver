package com.example.driver.adapter.order;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.driver.R;
import com.example.driver.model.order.Orders;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.driver.network.ApiService.IMG_PRODUCT_URL;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {


    ArrayList<Orders> listData;
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;
String status;
    public OrdersAdapter(String status, ArrayList<Orders> list, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.listData = list;
        this.status=status;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orders data=listData.get(position);


        holder.tvStatus.setText(data.getStatusName());
        holder.tvPrice.setText("₹ "+data.getProductPrice()+".00");
        int tp=Integer.parseInt(data.getQuantity())*Integer.parseInt(data.getProductPrice());
        holder.tvTotalPrice.setText(data.getQuantity()+"X"+data.getProductPrice()+" = ₹ "+tp+".00");


        holder.tvStatus.setTextColor(Color.parseColor(data.getStatusColor()));

        holder.tvStatus.setText(data.getStatusName());

        holder.tvProductAttribute.setText(data.getOrderAttribute());
        holder.tvProductName.setText(data.getProductName());

        holder.catName.setText(data.getCategoryName());
        holder.subCat.setText(data.getSubCategoryName());
        holder.subtosubCat.setText(data.getSubsubCategoryName());
        holder.tvDeliverBoy.setText(data.getDeliveryBoyName());

    holder.tvCheckBox.setVisibility(View.GONE);

       holder.tvCheckUncheck.setOnClickListener(v->{
           if(data.getCheckbox().equalsIgnoreCase("0")){
               data.setCheckbox("1");
               notifyDataSetChanged();
           }else{
               data.setCheckbox("0");
               notifyDataSetChanged();
           }


           onMenuListClicklistener.onOptionClick(listData,position);
       });
        if(data.getCheckbox().equalsIgnoreCase("1")){
            holder.tvCheckUncheck.setBackgroundResource(R.drawable.check);
        }else{
            holder.tvCheckUncheck.setBackgroundResource(R.drawable.uncheck);
        }

        holder.orderAssign.setVisibility(View.GONE);
        Glide.with(mContext)
                .load(IMG_PRODUCT_URL+data.getProductPhoto())
                .into(holder.productImg);

        holder.orderAssign.setOnClickListener(v->{
            onMenuListClicklistener.orderAssign(data,position);
        });

    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView tvTotalPrice,tvDeliverBoy,tvProductName,tvProductAttribute,catName,subCat,subtosubCat, tvDate,tvOrderNumber,tvCustName,orderAssign,tvPrice,tvStatus;
        MaterialCardView cardView;
        AppCompatCheckBox tvCheckBox;
        LinearLayoutCompat detailsShow;
        AppCompatImageView tvCheckUncheck;
        CircleImageView productImg;
         MyViewHolder(View view) {
            super(view);
             tvCheckUncheck=view.findViewById(R.id.tvCheckUncheck);
             tvTotalPrice=view.findViewById(R.id.tvTotalPrice);
             tvProductName=view.findViewById(R.id.tvProductName);
             detailsShow=view.findViewById(R.id.detailsShow);
             tvProductAttribute=view.findViewById(R.id.tvProductAttribute);
             catName=view.findViewById(R.id.catName);
             tvCheckBox=view.findViewById(R.id.tvCheckBox);
             productImg=view.findViewById(R.id.productImg);
             tvDeliverBoy=view.findViewById(R.id.tvDeliverBoy);
             subCat=view.findViewById(R.id.subCat);
             subtosubCat=view.findViewById(R.id.subtosubCat);
             tvStatus=view.findViewById(R.id.tvStatus);
             orderAssign=view.findViewById(R.id.orderAssign);


             tvPrice=view.findViewById(R.id.tvPrice);
             cardView=view.findViewById(R.id.cardView);

         }
    }
    public interface OnMeneuClickListnser{
        void onOptionClick(ArrayList<Orders>  list, int pos);
        void orderAssign(Orders data, int pos);
    }


 }



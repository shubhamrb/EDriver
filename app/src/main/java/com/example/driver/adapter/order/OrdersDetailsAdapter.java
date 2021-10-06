package com.example.driver.adapter.order;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver.R;
import com.example.driver.model.order.CustomerOrderDetails;
import com.example.driver.model.order.OrderParam;
import com.example.driver.model.order.Orders;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class OrdersDetailsAdapter extends RecyclerView.Adapter<OrdersDetailsAdapter.MyViewHolder> implements  OrdersAdapter.OnMeneuClickListnser {

    private final  OnMeneuClickListnser onMenuListClicklistener;
    ArrayList<CustomerOrderDetails> listData;
    OrdersAdapter ordersAdapter;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;
String status;
    public OrdersDetailsAdapter(ArrayList<CustomerOrderDetails> list,  OnMeneuClickListnser onLiveTestClickListener, Context context,String status) {
        this.listData = list;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext=context;
this.status=status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_order_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CustomerOrderDetails data=listData.get(position);
        OrderParam orderParam=data.getOrderDetails();
        holder.tvDate.setText(orderParam.getOrderDate());
        holder.tvOrderNumber.setText(orderParam.getOrderNumber());
        holder.tvCusName.setText(orderParam.getCustomerName());
        holder.tvTotalPrice.setText(orderParam.getTotal());

        holder.tvShopName.setText(orderParam.getShop_name());
        holder.tvShopAddress.setText(orderParam.getShopaddress());

        String qnty="0";
        if(data.getProductDetails().size()>0 ){
            qnty=String.valueOf(data.getProductDetails().size());
            holder.viewDet.setVisibility(View.VISIBLE);
        }else{

            qnty="0";
            holder.viewDet.setVisibility(View.GONE);
        }

        holder.tvQnty.setText("Quantity : "+qnty);

        holder.tvShow.setOnClickListener(v->{
            if (data.getViewList()){
                data.setViewList(false);
                notifyDataSetChanged();

            }else{
                data.setViewList(true);
                notifyDataSetChanged();


            }

        });
        ArrayList<Orders> list=data.getProductDetails();
        ordersAdapter = new OrdersAdapter(status,list, this, mContext);
        holder.listItem.setAdapter(ordersAdapter);
        holder.tvAddress.setText(orderParam.getAddress());
        if(data.getViewList()){
            holder.listItem.setVisibility(View.VISIBLE);

        }else {
            holder.listItem.setVisibility(View.GONE);

        }

        holder.orderAccept.setOnClickListener(v->{
onMenuListClicklistener.orderAccept(data,position);
        });
        holder.tvCustCall.setOnClickListener(v->{
            onMenuListClicklistener.Call(data,"customer");
        });
        holder.tvVenCall.setOnClickListener(v->{
            onMenuListClicklistener.Call(data,"vendor");
        });
        holder.tvVenmap.setOnClickListener(v->{
            onMenuListClicklistener.NavigateOnMap(data,"vendor");
        });
        holder.tvCustmap.setOnClickListener(v->{
            onMenuListClicklistener.NavigateOnMap(data,"customer");
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }



    @Override
    public void onOptionClick(ArrayList<Orders> list, int pos) {
         onMenuListClicklistener.selectProduct(listData);
    }

    @Override
    public void orderAssign(Orders data, int pos) {

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView tvShopName,tvShopAddress,tvAddress,orderAccept,tvDate,tvQnty,tvOrderNumber,tvCusName,tvTotalPrice;
        RecyclerView listItem;
        AppCompatImageView tvShow,tvCustCall,tvVenCall,tvCustmap,tvVenmap;
        LinearLayoutCompat viewDet;
         MyViewHolder(View view) {
            super(view);
             listItem = itemView.findViewById(R.id.listItem);
             tvDate=view.findViewById(R.id.tvDate);
             tvShopName=view.findViewById(R.id.tvShopName);
             tvShopAddress=view.findViewById(R.id.tvShopAddress);
             tvAddress=view.findViewById(R.id.tvAddress);

             tvCustmap=view.findViewById(R.id.tvCustmap);
             tvVenmap=view.findViewById(R.id.tvVenmap);

             tvShow=view.findViewById(R.id.tvShow);
             tvQnty=view.findViewById(R.id.tvQnty);
             tvCustCall=view.findViewById(R.id.tvCustCall);
             tvVenCall=view.findViewById(R.id.tvVenCall);
             orderAccept=view.findViewById(R.id.orderAccept);
             tvOrderNumber=view.findViewById(R.id.tvOrderNumber);
             viewDet=view.findViewById(R.id.viewDet);
             tvCusName=view.findViewById(R.id.tvCusName);

             tvTotalPrice=view.findViewById(R.id.tvTotalPrice);
         }
    }
    public interface OnMeneuClickListnser{
        void orderAccept(CustomerOrderDetails data, int pos);
        void selectProduct(ArrayList<CustomerOrderDetails> listData);
        void Call(CustomerOrderDetails data,String Type);
        void NavigateOnMap(CustomerOrderDetails data,String type);
    }


 }



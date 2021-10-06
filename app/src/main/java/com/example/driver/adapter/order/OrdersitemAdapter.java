package com.example.driver.adapter.order;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.driver.R;
import com.example.driver.model.order.OrderProduct;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.driver.network.ApiService.IMG_PRODUCT_URL;


public class OrdersitemAdapter extends RecyclerView.Adapter<OrdersitemAdapter.MyViewHolder> {


    ArrayList<OrderProduct> listData;
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int getPosition;
    private int row_index;
    String orderCheckBoxStatus="";
    Context mContext;
    int select;

    public OrdersitemAdapter(String status,ArrayList<OrderProduct> list, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.listData = list;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext = context;
        this.orderCheckBoxStatus=status;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_product_view, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderProduct data = listData.get(position);

        holder.productName.setText(data.getProductName());
        holder.catName.setText(data.getCategoryName());

        holder.subCat.setText(data.getSubCategoryName());
        holder.subtosubCat.setText(data.getSubsubCategoryName());
        int tp = Integer.parseInt(data.getQuantity()) * Integer.parseInt(data.getPrice());
        holder.actualPrice.setText(data.getPrice() + "X" + data.getQuantity() + " = " + tp);
        holder.tvQuantity.setText(data.getQuantity());

//        if(orderCheckBoxStatus.equalsIgnoreCase("All")){
//            holder.checkBox.setVisibility(View.GONE);
//        }else if(orderCheckBoxStatus.equalsIgnoreCase("0")){
//            holder.checkBox.setVisibility(View.VISIBLE);
//        }else{
//            holder.checkBox.setVisibility(View.GONE);
//        }
//        holder.checkBox.setOnClickListener(v -> {
//            if (data.getVendor_selected_product().equalsIgnoreCase("0")) {
//                data.setVendor_selected_product("1");
//            } else {
//                data.setVendor_selected_product("0");
//            }
//            notifyDataSetChanged();
//            onMenuListClicklistener.onOptionClick(data);
//        });


//        if (data.getVendor_selected_product().equalsIgnoreCase("0")) {
//            holder.checkBox.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.uncheck));
//            ;
//        } else {
//            holder.checkBox.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.check));
//            ;
//
//        }
        holder.tvTotPrice.setText("₹ " + tp);
        Glide.with(mContext)
                .load(IMG_PRODUCT_URL + data.getProductPhoto())
                .into(holder.productImg);


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView productImg;
        AppCompatImageView checkBox;
        MaterialTextView productName, catName, subCat, subtosubCat, actualPrice, tvQuantity, tvTotPrice, tvTotalPrice;
        MaterialCardView cardView;

        MyViewHolder(View view) {
            super(view);
            productImg = view.findViewById(R.id.productImg);
            productName = view.findViewById(R.id.productName);
            catName = view.findViewById(R.id.catName);
            subCat = view.findViewById(R.id.subCat);

            subtosubCat = view.findViewById(R.id.subtosubCat);
            actualPrice = view.findViewById(R.id.actualPrice);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            tvTotPrice = view.findViewById(R.id.tvTotPrice);
            tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
            cardView = view.findViewById(R.id.cardView);
        }
    }

    public interface OnMeneuClickListnser {
        void onOptionClick(OrderProduct data);
    }


}



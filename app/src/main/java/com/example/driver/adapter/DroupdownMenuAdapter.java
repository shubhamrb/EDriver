package com.example.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver.R;
import com.example.driver.model.DroupDownModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class DroupdownMenuAdapter extends RecyclerView.Adapter<DroupdownMenuAdapter.MyViewHolder> implements Filterable {

    private List<DroupDownModel> menuClassList;
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    private List<DroupDownModel> searchArray;
    public DroupdownMenuAdapter(List<DroupDownModel> menuClassList, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.menuClassList = menuClassList;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.searchArray=new ArrayList<>();
        this.searchArray.addAll(menuClassList);
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menuviewlayout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DroupDownModel ticket=menuClassList.get(position);

        holder.nameText.setText(ticket.getDescription());
        //holder.number.setText(ticket.getTicketNumber());


holder.openpanel.setOnClickListener(v -> {
    onMenuListClicklistener.onOptionClick(menuClassList.get(position));
   // notifyDataSetChanged();
});

      
      }
    void add(DroupDownModel ticket) {
        this.menuClassList.add(ticket);
    }
   public void addAll(List<DroupDownModel> tickets) {
        this.menuClassList.addAll(tickets);
    }

    @Override
    public int getItemCount() {
        return menuClassList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
          TextView number,nameText;
          MaterialCardView openpanel;
        MyViewHolder(View view) {
            super(view);

            openpanel=view.findViewById(R.id.openpanel);
            nameText=view.findViewById(R.id.name);



        }
    }
    public interface OnMeneuClickListnser{
        void onOptionClick(DroupDownModel liveTest);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterString = charSequence.toString().toLowerCase();
                //arraylist1.clear();
                FilterResults results = new FilterResults();
                int count = searchArray.size();
                final List<DroupDownModel> list = new ArrayList<>();

                DroupDownModel filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = searchArray.get(i);
                    if (searchArray.get(i).getName().toLowerCase().contains(filterString)
                            || searchArray.get(i).getDescription().toLowerCase().contains(filterString)) {
                        list.add(filterableString);
                    }


                }

                results.values = list;
                results.count = list.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                menuClassList = (List<DroupDownModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };


    }
}


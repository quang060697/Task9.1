package com.example.lostandfoundapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    private Context context;
    private List<Item> itemList;
    private OnRowClickListener listener;

    public ItemAdapter(Context context, List<Item> itemList, OnRowClickListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_row,parent,false);
        return new ItemViewHolder(itemView, listener );    }



    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        holder.itemTextView.setText(itemList.get(position).getType()+" "+itemList.get(position).getName()+" ("+ itemList.get(position).getLocation()+")");

    }

    @Override
    public int getItemCount() {
        return itemList.size() ;
    }
    public interface OnRowClickListener {
        void onItemClick (int position);

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemTextView;
        public OnRowClickListener OnRowClickListener;

        public ItemViewHolder(@NonNull View itemView, OnRowClickListener OnRowClickListener) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.itemTextView);
            this.OnRowClickListener = OnRowClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            OnRowClickListener.onItemClick(getAdapterPosition());
        }
    }
}

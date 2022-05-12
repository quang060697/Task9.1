package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ShowAllItemsActivity extends AppCompatActivity implements ItemAdapter.OnRowClickListener {
    DatabaseHelper db;
    List<Item> itemList = new ArrayList<>();
    RecyclerView recyclerView;
    List<Item> tempt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_items);

        recyclerView = findViewById(R.id.recyclerView);
        db = new  DatabaseHelper(this);
        tempt = db.fetchAllItems();
        for (int i = 0; i < tempt.size(); i++) {

            Item item = new Item(tempt.get(i).getItemid(),tempt.get(i).getName(), tempt.get(i).getType(), tempt.get(i).getPhone(),tempt.get(i).getDescription(),tempt.get(i).getDate(),tempt.get(i).getLocation());
            itemList.add(item);

        }
        recyclerView = findViewById(R.id.recyclerView);
        ItemAdapter itemAdapter = new ItemAdapter(ShowAllItemsActivity.this, itemList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(),RemoveItemActivity.class);
        intent.putExtra("name",itemList.get(position).getName());
        intent.putExtra("type",itemList.get(position).getType());
        intent.putExtra("location",itemList.get(position).getLocation());
        intent.putExtra("date",itemList.get(position).getDate());
        intent.putExtra("phone",itemList.get(position).getPhone());
        intent.putExtra("description",itemList.get(position).getDescription());
        intent.putExtra("itemid",itemList.get(position).getItemid());

        startActivity(intent);

    }
}
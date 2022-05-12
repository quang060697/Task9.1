package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RemoveItemActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_item);

        db =new DatabaseHelper(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String type = intent.getStringExtra("type");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");
        String phone = intent.getStringExtra("phone");
        String location = intent.getStringExtra("location");
        Integer itemid = intent.getIntExtra("itemid",0);

        TextView nameInput = findViewById(R.id.nameTextView);
        TextView phoneInput = findViewById(R.id.phoneTextView);
        TextView descriptionInput = findViewById(R.id.descriptionTextView);
        TextView dateInput = findViewById(R.id.dateTextView);
        TextView locationInput = findViewById(R.id.locationTextView);
        Button removeButton = findViewById(R.id.removeButton);

        nameInput.setText(type+" "+name);
        dateInput.setText(date);
        locationInput.setText(location);
        phoneInput.setText(phone);
        descriptionInput.setText(description);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long result = db.deleteItem(itemid);
                if (result>0)
                {
                    Toast.makeText(RemoveItemActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getApplicationContext(),ShowAllItemsActivity.class);

                    startActivity(intent1);
                    finish();
                }
                else {
                    Toast.makeText(RemoveItemActivity.this, itemid.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
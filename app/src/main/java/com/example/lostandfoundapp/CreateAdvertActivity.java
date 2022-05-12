package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateAdvertActivity extends AppCompatActivity {
    RadioButton radioButton;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        EditText nameInput = findViewById(R.id.nameInput);
        EditText phoneInput = findViewById(R.id.phoneInput);
        EditText descriptionInput = findViewById(R.id.descriptionInput);
        EditText dateInput = findViewById(R.id.dateInput);
        EditText locationInput = findViewById(R.id.locationInput);
        Button saveButton = findViewById(R.id.saveButton);
        db = new DatabaseHelper(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                String type = radioButton.getText().toString();
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String date = dateInput.getText().toString();
                String location = locationInput.getText().toString();

                Item item = new Item(name,type,phone,description,date,location);
                long result = db.insertItem(item);
                if (result > 0)
                {
                    Toast.makeText(CreateAdvertActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateAdvertActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(CreateAdvertActivity.this, "Unsuccessfully", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
package com.example.lostandfoundapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {
    RadioButton radioButton;
    DatabaseHelper db;
    EditText locationInput;
    LocationManager locationManager;
    LocationListener locationListener;
    Geocoder geocoder;
    double latitude,longitude;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.checkSelfPermission(CreateAdvertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreateAdvertActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)  ==PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListener);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        EditText nameInput = findViewById(R.id.nameInput);
        EditText phoneInput = findViewById(R.id.phoneInput);
        EditText descriptionInput = findViewById(R.id.descriptionInput);
        EditText dateInput = findViewById(R.id.dateInput);
        locationInput = findViewById(R.id.locationInput);
        Button saveButton = findViewById(R.id.saveButton);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        Button getLocationButton = findViewById(R.id.getLocationButton);
        db = new DatabaseHelper(this);




        String API_KEY = "AIzaSyCgRgdKN7H3VivbU2IwJ8UlHJKlYDsoGd4";
        Places.initialize(getApplicationContext(), API_KEY);

        locationInput.setFocusable(false);
        locationInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(CreateAdvertActivity.this);
                startActivityForResult(intent, 100);
            }
        });

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                List<Address> addresses;
                geocoder = new Geocoder(CreateAdvertActivity.this, Locale.getDefault());
                try {
                    latitude = location.getLatitude();
                    longitude= location.getLongitude();
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    locationInput.setText(addresses.get(0).getAddressLine(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(CreateAdvertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreateAdvertActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateAdvertActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListener);
                }

            }
        });
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


                Item item = new Item(name, type, phone, description, date, location,latitude,longitude);
                long result = db.insertItem(item);
                if (result > 0) {
                    Toast.makeText(CreateAdvertActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateAdvertActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CreateAdvertActivity.this, "Unsuccessfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Place place = Autocomplete.getPlaceFromIntent(data);
            locationInput.setText(place.getName());
            latitude = place.getLatLng().latitude;
            longitude = place.getLatLng().longitude;
        }
    }
}
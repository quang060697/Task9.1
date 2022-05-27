package com.example.lostandfoundapp;

public class Item {
    private int itemid;
    private double latitude,longitude;
    private String name,type,phone,description, date,location;

    public Item(int itemid,String name, String type, String phone, String description, String date, String location) {
        this.itemid = itemid;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }


    public Item( String name, String type, String phone, String description, String date, String location, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public int getItemid() {
        return itemid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

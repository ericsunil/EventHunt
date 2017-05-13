package com.peyxen.eventilizer.Model;

/**
 * Created by Pci on 1/9/2016.
 */
public class Events {
    private String title, description, date, type, location;
    private int amount, id, rating,joined;

    public Events(int id, String title, String description, String date, String type, String location, int amount, int rating,int joined) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.type = type;
        this.location = location;
        this.joined = joined;
        this.amount = amount;
        this.id = id;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public int getJoined() {
        return joined;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }
}

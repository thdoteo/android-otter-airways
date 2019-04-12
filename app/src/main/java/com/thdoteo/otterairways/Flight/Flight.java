package com.thdoteo.otterairways.Flight;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Flight")
public class Flight {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int id;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "departure")
    private String departure;

    @ColumnInfo(name = "arrival")
    private String arrival;

    @ColumnInfo(name = "departure_at")
    private String departure_at;

    @ColumnInfo(name = "capacity")
    private int capacity;

    @ColumnInfo(name = "price")
    private Double price;

    public Flight() {}

    @Ignore
    public Flight(String number, String departure, String arrival, String departure_at, int capacity, Double price)
    {
        this.number = number;
        this.departure = departure;
        this.arrival = arrival;
        this.departure_at = departure_at;
        this.capacity = capacity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture_at() {
        return departure_at;
    }

    public void setDeparture_at(String departure_at) {
        this.departure_at = departure_at;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}

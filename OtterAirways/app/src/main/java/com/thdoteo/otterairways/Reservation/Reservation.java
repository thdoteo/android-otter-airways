package com.thdoteo.otterairways.Reservation;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Reservation")
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int id;

    @ColumnInfo(name = "account_id")
    private int account_id;

    @ColumnInfo(name = "flight_id")
    private int flight_id;

    @ColumnInfo(name = "seats")
    private int seats;

    @ColumnInfo(name = "price")
    private Double price;

    public Reservation() {}

    @Ignore
    public Reservation(int account_id, int flight_id, int seats, Double price)
    {
        this.account_id = account_id;
        this.flight_id = flight_id;
        this.seats = seats;
        this.price = price;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getAccount_id()
    {
        return account_id;
    }

    public void setAccount_id(int account_id)
    {
        this.account_id = account_id;
    }

    public int getFlight_id()
    {
        return flight_id;
    }

    public void setFlight_id(int flight_id)
    {
        this.flight_id = flight_id;
    }

    public int getSeats()
    {
        return seats;
    }

    public void setSeats(int seats)
    {
        this.seats = seats;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

}

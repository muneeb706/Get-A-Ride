package com.example.dell.google_maps_drive;

import java.util.ArrayList;

public class Singleton {
    private ArrayList<Bookings> arrayList;

    private static Singleton instance;

    private Singleton() {
        arrayList = new ArrayList<Bookings>();
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public ArrayList<Bookings> getArrayList() {
        return arrayList;
    }

    public void add_singleton_booking(Bookings book) {
        arrayList.add(book);
    }

    public int getsize() {
        return arrayList.size();
    }

    public void delete_all() {
        arrayList.clear();
    }

    public Bookings get(int position) {
        return arrayList.get(position);
    }

    public void setlist(ArrayList<Bookings> book)
    {
        arrayList=book;
    }

}


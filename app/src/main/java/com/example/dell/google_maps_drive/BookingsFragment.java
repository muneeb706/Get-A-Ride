package com.example.dell.google_maps_drive;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by DELL on 4/26/2016.
 */
public class BookingsFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    View view;
    ListView lv;
    ViewHolder temp;
    boolean check=false;
    int count=0;
    static ArrayList<Bookings> list;

    public static void setBookings(ArrayList<Bookings> l){
        list = l;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.bookings_fragment,container,false);
        view=rootView;
        Singleton s1= Singleton.getInstance();
        s1.delete_all();
        /*Bookings arr= new Bookings("hammad","26-4-16","faisaltown","cantt");
        Bookings arr2= new Bookings("dani","26-4-16","faisaltown","shadman");
        Bookings arr3= new Bookings("jutt","26-4-16","faisaltown","gulberg");

        s1.add_singleton_booking(arr);
        s1.add_singleton_booking(arr2);
        s1.add_singleton_booking(arr3);*/

        s1.setlist(list);

        lv = (ListView)rootView.findViewById(R.id.list);
        lv.setAdapter(new CustomListAdapter(getActivity()));
        lv.setOnItemClickListener(this);
        return rootView;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(check==true)
        {
            temp.from.setVisibility(View.GONE);
            temp.to.setVisibility(View.GONE);
            temp.date.setVisibility(View.GONE);
            temp.driver.setVisibility(View.GONE);
        }

        ViewHolder holder= (ViewHolder) view.getTag();




        holder.from.setVisibility(View.VISIBLE);
        holder.to.setVisibility(View.VISIBLE);
        holder.date.setVisibility(View.VISIBLE);
        holder.driver.setVisibility(View.VISIBLE);
        temp = (ViewHolder) view.getTag();
        check = true;



        if(count%2==1) {
            holder.from.setVisibility(View.GONE);
            holder.to.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);
            holder.driver.setVisibility(View.GONE);
            temp = (ViewHolder) view.getTag();
            check = true;

        }

        count++;
        /*TextView tv1 = (TextView)MainActivity.this.findViewById(R.id.startid);
        TextView tv2 = (TextView)MainActivity.this.findViewById(R.id.toid);
        tv1.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);*/
    }
}

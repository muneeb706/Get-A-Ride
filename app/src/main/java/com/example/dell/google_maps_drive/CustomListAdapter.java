package com.example.dell.google_maps_drive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by hammad on 4/25/2016.
 */
public class CustomListAdapter extends BaseAdapter {

    ArrayList<Bookings> clist;
    Context ctx;


    public CustomListAdapter(Context c) {

        /*SharedPreferences sp=c.getSharedPreferences("myfile",Context.MODE_PRIVATE);
        int file_size=sp.getInt("size",0);
        for(int i=0; i<file_size; i++)
        {
            String tempn=sp.getString("name"+i,"0");
            Contacts tempcon= new Contacts(0,tempn);
            clist.add(tempcon);
        }*/

        Singleton s1=Singleton.getInstance();
        int singleton_size=s1.getsize();
        clist=new ArrayList<Bookings>();

        ctx = c;
        if(singleton_size<1) {
            /*Resources temp_res = ctx.getResources();
            String[] temp_contact = temp_res.getStringArray(R.array.contacts);
            int[] temp_imgs = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4};

            for (int i = 0; i < temp_contact.length; i++) {
                Contacts temp_contacts = new Contacts(temp_imgs[i], temp_contact[i]);
                clist.add(temp_contacts);
            }
        }*/




        }
    }

    @Override
    public int getCount() {
        if(Singleton.getInstance().getsize()<1) {
            return clist.size();
        }
        else
        {
            return Singleton.getInstance().getsize();
        }
    }

    @Override
    public Object getItem(int position) {
        return clist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=convertView;
        ViewHolder holder=null;

        if(v==null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.layout_booking_item, parent, false);
            holder=new ViewHolder(v);
            v.setTag(holder);
        }

        else
        {
            holder= (ViewHolder) v.getTag();
        }

        Bookings arr;

        if(Singleton.getInstance().getsize()<1) {
            arr=clist.get(position);
        }
        else
        {
            arr=Singleton.getInstance().get(position);
        }



        int counter=arr.index;
        String tempcounter=String.valueOf(counter);
        String book="Booking: "+tempcounter;
        String from="From: "+arr.from;
        String to="To: "+arr.to;
        String date="Date: "+arr.date;
        String driver="Driver: "+arr.driver;

        holder.bookingnumber.setText(book);
        holder.date.setText(date);
        holder.driver.setText(driver);
        holder.to.setText(to);
        holder.from.setText(from);

        //holder.fromlabel.setVisibility(View.GONE);

        return v;


    }

    void updategridview()
    {
        this.notifyDataSetChanged();
    }
}

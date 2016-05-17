package com.example.dell.google_maps_drive;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DELL on 3/10/2016.
 */
public class VivzAdapter extends RecyclerView.Adapter<VivzAdapter.MyViewHolder> {
private Context cont;
    private DrawerLayout mdrawerLayout;
    private View drawerView;
    List<Information> data= Collections.emptyList();
    private MapsActivity map_act;
    private LayoutInflater inflater;
    public int selectedPos;
    public VivzAdapter(Context context,List<Information>data){
        inflater=LayoutInflater.from(context);
        cont=context;
selectedPos=3;
        this.data=data;


    }
    public void setDrawerLayout(DrawerLayout drawer){
        mdrawerLayout=drawer;
    }
    public void setActivity(MapsActivity m_act){
        map_act=m_act;
    }
    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
        //notifyDataSetChanged is an expensive operation as it re draws whole list again
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        Log.d("Vivz","onCreateHolder called");
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Information current=data.get(position);
Log.d("Vivz", "onBindViewHilder Called " + position);
        holder.title.setText(current.resourceTitle);
        holder.image.setImageResource(current.resourceId);

        if(selectedPos == position){
            // Here I am just highlighting the background
            holder.itemView.setBackgroundColor(Color.GRAY);
        }else{
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating old as well as new positions
                notifyItemChanged(selectedPos);
                selectedPos = position;
                notifyItemChanged(selectedPos);

                // Do your another stuff for your onClick
            }
        });

    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.listText);
            image=(ImageView)itemView.findViewById(R.id.list_icon);
        }
    }


}

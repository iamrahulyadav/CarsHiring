package com.carsgates.cr.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carsgates.cr.R;
import com.carsgates.cr.models.SpinnerItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SpinnerAdapter extends ArrayAdapter<SpinnerItemModel> {
    Context context;
    int groupId;
    LayoutInflater inflater;
    ArrayList<SpinnerItemModel> countlist;
    public SpinnerAdapter(Context context,int groupId,int id,ArrayList<SpinnerItemModel> countlist) {
        super(context,id,countlist);
        this.context=context;
         this.groupId=groupId;
        this.countlist=countlist;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView=inflater.inflate(groupId,parent,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
        imageView.setImageResource(countlist.get(position).getImageId());
        TextView textView=(TextView)itemView.findViewById(R.id.txt);
        textView.setText(countlist.get(position).getTxt());
        return itemView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}

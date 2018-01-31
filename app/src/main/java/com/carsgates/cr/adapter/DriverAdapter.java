package com.carsgates.cr.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.AnimatioonUtils;
import com.carsgates.cr.models.DriverModel;

import java.util.List;

/**
 * Created by Muhib
 * phone number
 */

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverHolder>
{
    List<DriverModel> driverlist;
    Context mcontext;
    int lastpositon=-1;
    String idd;
    private ClickListener clickListener;
    public interface ClickListener
    {
        void itemClicked(View view,int postion);
    }
    public DriverAdapter(Context mcontext,List<DriverModel> driverlist)
    {
        this.driverlist=driverlist;
        this.mcontext=mcontext;
    }
    @Override
    public DriverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.driverselectionrow,parent,false);
        return new DriverHolder(row);
    }

    @Override
    public void onBindViewHolder(DriverHolder holder, int position) {
        DriverModel model = driverlist.get(position);
        holder.dr_name.setText(model.getDriver_fname()+" "+model.getDriver_sname());
        holder.dr_email.setText(model.getDriver_email());
        holder.dr_phnumber.setText(model.getDriver_phone());
        holder.dr_Age.setText(model.getDriver_age());
        holder.dr_img.setImageResource(model.getDriverImg());
        holder.dr_id.setText(model.getDriver_id());
      /*  if (position > 0) {
            holder.ar_img.setImageResource(R.drawable.ic_blue_arrow);
            holder.ar_img.setBackgroundColor(mcontext.getResources().getColor(R.color.Light_grey));
        }
           *//* Animation animation= AnimationUtils.loadAnimation(mcontext,(position>lastpositon)? R.anim.up_from_bottom:R.anim.bottom_from_up);
            holder.itemView.setAnimation(animation);
            lastpositon=position;*//*
           if (position>lastpositon) {
               AnimatioonUtils.animate(holder,true);
           }
           else
           {
               AnimatioonUtils.animate(holder,false);
           }
        lastpositon=position;*/
    }

    public void setClickListene(ClickListener clickListener)
    {
        this.clickListener=clickListener;
    }


    @Override
    public int getItemCount() {
        return driverlist.size();
    }

    class DriverHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dr_name,dr_email,dr_phnumber,dr_Age,dr_id;
        ImageView dr_img,ar_img;
        public DriverHolder(View itemView) {
            super(itemView);
            dr_name= (TextView) itemView.findViewById(R.id.driver_name);
            dr_email=(TextView) itemView.findViewById(R.id.driver_email);
            dr_phnumber=(TextView) itemView.findViewById(R.id.driver_phnumber);
            dr_Age=(TextView) itemView.findViewById(R.id.driver_Age);
            dr_id=(TextView) itemView.findViewById(R.id.drivers_id);
            dr_img= (ImageView) itemView.findViewById(R.id.driver_image);
            ar_img= (ImageView) itemView.findViewById(R.id.arowimg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener!=null)
            {
                clickListener.itemClicked(itemView,getPosition());
            }
        }

      /*  @Override
        public void onClick(View v) {

            String name=dr_name.getText().toString();
            Toast.makeText(mcontext,""+name,Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    public void onViewAttachedToWindow(DriverHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.itemView.clearAnimation();
    }

}

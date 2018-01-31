package com.carsgates.cr.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.carsgates.cr.R;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.activities.CarDetail;
import com.carsgates.cr.activities.ExcessProtectionActivity;
import com.carsgates.cr.activities.Extras;
import com.carsgates.cr.activities.TermsandCondition;
import com.carsgates.cr.webservices.CarSpecification;
import com.carsgates.cr.webservices.SingleCarDetails;

import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class CarDetailTab1Fragment extends Fragment implements View.OnClickListener {
    View view;
    LinearLayout ll_extra,ll_protection;
    TextView terms,quotes,carname,carprice;
    ImageView carImg,imglogo;
    List<CarSpecification> carSpecificationList;
    ProgressBar bar,bar1;
    LinearLayout ll;
    GridLayout gl;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.cardetail_tab1,container,false);
        ll_extra= (LinearLayout) view.findViewById(R.id.ll_extra);
        ll_protection=(LinearLayout) view.findViewById(R.id.ll_protection);
        terms= (TextView) view.findViewById(R.id.txt_terms);
        quotes=(TextView) view.findViewById(R.id.txt_savequote);
        carname= (TextView) view.findViewById(R.id.txt_modelname);
        carprice= (TextView) view.findViewById(R.id.txt_carPrice);
        carImg= (ImageView) view.findViewById(R.id.img_car);
        imglogo= (ImageView) view.findViewById(R.id.img_carlogo);
        bar= (ProgressBar) view.findViewById(R.id.progressbar);
        bar1= (ProgressBar) view.findViewById(R.id.progressbar1);
        SingleCarDetails singleCarDetails = new SingleCarDetails();
        singleCarDetails = CarDetail.singleCarDetails;
        carSpecificationList=CarDetail.spec;
        ll_extra.setOnClickListener(this);
        ll_protection.setOnClickListener(this);
        terms.setOnClickListener(this);
        quotes.setOnClickListener(this);
        ll= (LinearLayout) view.findViewById(R.id.ll_otherspec);
        gl= (GridLayout) view.findViewById(R.id.gr_Spec);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bar.setVisibility(View.VISIBLE);
        bar1.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(CarDetail.carImage).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                bar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                bar.setVisibility(View.GONE);
                return false;
            }
        }).into(carImg);
        Glide.with(getContext()).load(CarDetail.logo).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                bar1.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                bar1.setVisibility(View.GONE);
                return false;
            }
        }).into(imglogo);
        carname.setText(CarDetail.modelname);
        carprice.setText(CarDetail.carPrice);
        if(carSpecificationList!=null)
        {
            int total=carSpecificationList.size();
            int col=2;
            int row=total/col;
            gl.setColumnCount(col);
            gl.setRowCount(row+1);
            for (int i=0,c=0,r=0;i<carSpecificationList.size();i++,c++)
            {
                CarSpecification carSpecification=carSpecificationList.get(i);
                if(carSpecification.getSpecification_display().equals("4")) {
                    TextView tt = new TextView(getContext());
                    tt.setText(carSpecification.getSpecification_name());
                    tt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick,0,0,0);
                    tt.setCompoundDrawablePadding(5);
                    tt.setGravity(Gravity.CENTER_VERTICAL);
                    tt.setTextSize(14);
                    ll.addView(tt);
                }
                if(carSpecification.getSpecification_display().equals("1") || carSpecification.getSpecification_display().equals("2"))
                {
                    if(c==col)
                    {
                        c=0;
                        r++;
                    }
                    View viw= getActivity().getLayoutInflater().inflate(R.layout.gridcustomstyle,null);
                    TextView tt= (TextView) viw.findViewById(R.id.txt_spec);
                    ImageView iview= (ImageView) viw.findViewById(R.id.img_specif);
                    //TextView tt=new TextView(getContext());
                    iview.setImageResource(R.drawable.ic_air_condition);
                    tt.setText(carSpecification.getSpecification_name());
                     /*    tt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick,0,0,0);
                        tt.setCompoundDrawablePadding(5);tt.setGravity(Gravity.CENTER_VERTICAL);
                        tt.setTextSize(14);
                         GridLayout.LayoutParams params=new GridLayout.LayoutParams();
                        params.height=GridLayout.LayoutParams.WRAP_CONTENT;
                        params.width=GridLayout.LayoutParams.WRAP_CONTENT;
                        params.leftMargin=5;
                        params.topMargin=3;
                        params.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        params.columnSpec=GridLayout.spec(c,13f);
                    }
                    params.rowSpec=GridLayout.spec(r);
                        tt.setLayoutParams(params);*/
                    gl.addView(viw);
                }

            }
        }
    }


    @Override
    public void onClick(View v) {
        Intent it=new Intent(getActivity(), ExcessProtectionActivity.class);
        switch (v.getId())
        {
            case R.id.ll_extra:
                startActivity(new Intent(getActivity(),Extras.class));
                break;
            case R.id.ll_protection:
                it.putExtra("get","ForProtec");
                startActivity(it);
                break;
            case R.id.txt_terms:
                startActivity(new Intent(getActivity(), TermsandCondition.class));
                break;
            case R.id.txt_savequote:
                it.putExtra("get","Forquotes");
                startActivity(it);
                break;
        }
    }
}

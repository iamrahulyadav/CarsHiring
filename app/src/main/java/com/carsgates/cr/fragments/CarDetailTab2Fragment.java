package com.carsgates.cr.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carsgates.cr.R;
import com.carsgates.cr.activities.CarDetail;
import com.carsgates.cr.activities.ExcessProtectionActivity;
import com.carsgates.cr.activities.TermsandCondition;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class CarDetailTab2Fragment extends Fragment implements View.OnClickListener {
    View view;
    TextView terms,quotes;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.cardetail_tab2,container,false);
        terms= (TextView) view.findViewById(R.id.txt_terms);
       quotes=(TextView) view.findViewById(R.id.txt_savequote);
        terms.setOnClickListener(this);
        quotes.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_terms:
                startActivity(new Intent(getContext(), TermsandCondition.class));;
                break;
            case R.id.txt_savequote:
                Intent it=new Intent(getActivity(), ExcessProtectionActivity.class);
                it.putExtra("get","Forquotes");
                startActivity(it);
                break;
        }
    }
}
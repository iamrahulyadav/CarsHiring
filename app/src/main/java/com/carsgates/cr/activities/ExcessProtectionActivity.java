package com.carsgates.cr.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carsgates.cr.R;
import com.carsgates.cr.adapter.MyExListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.constraint.solver.widgets.ConstraintWidget.GONE;


public class ExcessProtectionActivity extends AppCompatActivity implements View.OnClickListener {
    ExpandableListView expListView;
    List<String> head;
    HashMap<String,List<String>> body;
    MyExListAdapter listAdapter;
    TextView text1,text2,text3,toolbartext;
    LinearLayout layout1,layout2,layout3,layoutbuttons;
    TextView extrapro;
    Toolbar toolbar;
    Button bt_standPro,bt_fullPro;
    String FromQuotes="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excess_protection);
        View v=findViewById(android.R.id.content);
        toolbar= (Toolbar) findViewById(R.id.bottomToolBar);
        text1= (TextView) findViewById(R.id.txt_1);
        text2=(TextView) findViewById(R.id.txt_2);
        text3=(TextView) findViewById(R.id.txt_3);
        toolbartext= (TextView) toolbar.findViewById(R.id.txt_bot);
        text1.setText("Damage Excees & Thief Excess");
        text2.setText("Windows,Mirrors,Wheels & Tyres");
        text3.setText("Key Loss & Roadside Assistance");
        toolbartext.setText("Add Full Protection");

        layout1= (LinearLayout) findViewById(R.id.linear_1);
        layout2= (LinearLayout) findViewById(R.id.linear_2);
        layout3= (LinearLayout) findViewById(R.id.linear_3);
        layoutbuttons= (LinearLayout) findViewById(R.id.ll_buttons);
        bt_standPro= (Button)findViewById(R.id.bt_standardPro);
        bt_fullPro=(Button) findViewById(R.id.bt_fullpro);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        bt_fullPro.setOnClickListener(this);
        bt_standPro.setOnClickListener(this);

        Intent it=getIntent();
        if(it!=null)
        {
            String val=it.getStringExtra("get");
            if(val.equalsIgnoreCase("ForProtec"))
            {
                layoutbuttons.setVisibility(View.GONE);

               // Snackbar.make(v,val,Snackbar.LENGTH_LONG).show();
            }
            else if(val.equalsIgnoreCase("FromActi"))
            {
                toolbar.setVisibility(View.GONE);

            }
            else if(val.equalsIgnoreCase("Forquotes"))
            {
                toolbar.setVisibility(View.GONE);
                bt_standPro.setText("Get a quote with Standard Coverage");
                bt_fullPro.setText("Get a quote with Full Protection");
                FromQuotes="Yes";
            }

        }
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Excess Protection");
        }
        extrapro= (TextView) findViewById(R.id.txt_extra_pro);
        extrapro.setOnClickListener(this);
        preapredata();
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExcessProtectionActivity.this,DriverSelectionActivity.class));
            }
        });
    }

    private void preapredata() {
        head=new ArrayList<>();
        body=new HashMap<>();
        head.add("Damage Excees & Thief Excess");
        head.add("Windows,Mirrors,Wheels & Tyres");
        head.add("Key Loss & Roadside Assistance");

        List<String> detail=new ArrayList<>();
        detail.add(getResources().getString(R.string.dummy));

        body.put(head.get(0),detail);
        body.put(head.get(1),detail);
        body.put(head.get(2),detail);
    }
    LinearLayout  layout ;
    @Override
    public void onClick(View v) {
        int id=v.getId();
       Intent intent= new Intent(ExcessProtectionActivity.this,DriverSelectionActivity.class);
        if(layout!=null) layout.setVisibility(View.GONE);
        switch (id)
        {
            case R.id.txt_1:
                layout1.setVisibility(View.VISIBLE);
                layout = layout1 ;
                break;
            case R.id.txt_2:
                layout2.setVisibility(View.VISIBLE);
                layout = layout2 ;
                break;
            case R.id.txt_3:
                layout3.setVisibility(View.VISIBLE);
                layout = layout3;
                break;
            case R.id.txt_extra_pro:
                startActivity(new Intent(ExcessProtectionActivity.this,FullProtectionActivity.class));
                break;
            case R.id.bt_fullpro:
                if(FromQuotes.isEmpty()) {
                    startActivity(new Intent(ExcessProtectionActivity.this, DriverSelectionActivity.class));
                }
                else {
                    intent.putExtra("FromQuotes", FromQuotes);
                    startActivity(intent);
                }
                break;
            case R.id.bt_standardPro:
                if(FromQuotes.isEmpty())
                    startActivity(new Intent(ExcessProtectionActivity.this,DriverSelectionActivity.class));
                else {
                    intent.putExtra("FromQuotes", FromQuotes);
                    startActivity(intent);
                }
                break;
            default:
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

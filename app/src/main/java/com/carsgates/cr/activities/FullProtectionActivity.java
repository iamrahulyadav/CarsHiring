package com.carsgates.cr.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;


import com.carsgates.cr.R;
import com.carsgates.cr.adapter.MyExListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FullProtectionActivity extends AppCompatActivity {
    ExpandableListView expListView;
    List<String> listparent;
    HashMap<String,List<String>> listchild;
    MyExListAdapter myExListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_protection);
        preaprelist();
        expListView= (ExpandableListView) findViewById(R.id.list_protect);
        myExListAdapter=new MyExListAdapter(this,listparent,listchild);
        expListView.setAdapter(myExListAdapter);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setTitle("Full Protection");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
    }

    private void preaprelist() {
        listparent=new ArrayList<>();
        listchild=new HashMap<>();

        listparent.add("Full Protection-Excellent Value,Best Cover,Peace of Mind");
        listparent.add("What does our Full Protection Product cover?");
        listparent.add("What does our Full Protection Product not cover?");
        listparent.add(getResources().getString(R.string.dummy));
        listparent.add(getResources().getString(R.string.dummy));
        listparent.add(getResources().getString(R.string.dummy));
        listparent.add(getResources().getString(R.string.dummy));


        List<String> pro=new ArrayList<String>();
        pro.add(getResources().getString(R.string.dummy));

        List<String> secon=new ArrayList<>();
        secon.add(getResources().getString(R.string.dummy));


        listchild.put(listparent.get(0),pro);
        listchild.put(listparent.get(1),secon);
        listchild.put(listparent.get(2),secon);
        listchild.put(listparent.get(3),secon);
        listchild.put(listparent.get(4),secon);
        listchild.put(listparent.get(5),secon);
        listchild.put(listparent.get(6),secon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId())
       {
           case android.R.id.home:
               onBackPressed();
            return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }
}

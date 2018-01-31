package com.carsgates.cr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.carsgates.cr.R;

public class SortingSelectionActivity extends AppBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting_seletion);
        actionBar = getSupportActionBar() ;
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        String filterString = "" ;
        Intent intent =  getIntent() ;
        if(intent!=null) {
            filterString =   intent.getStringExtra("filter") ;
        }
        RadioGroup filterGroup  = (RadioGroup) findViewById(R.id.filterGroup);

        setSelectedFilter(filterString);

        filterGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                final RadioButton radioButton = (RadioButton) group.findViewById(group.getCheckedRadioButtonId()) ;
                if(radioButton!=null){
                    Intent intent = new Intent();
                    intent.putExtra("filter",radioButton.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private void setSelectedFilter(final String filterString)
    {
        final RadioGroup filterGroup  = (RadioGroup) findViewById(R.id.filterGroup);
        int countChild = filterGroup.getChildCount() ;
        for (int indexChild = 0; indexChild < countChild; indexChild++) {
            View filterButton = filterGroup.getChildAt(indexChild);
            if(filterButton instanceof RadioButton){
                RadioButton rb =  (RadioButton)filterButton ;
                if(rb.getText().toString().equals(filterString)){
                    rb.setChecked(true);
                    break ;
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setTitle(getResources().getString(R.string.sorted_by));
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

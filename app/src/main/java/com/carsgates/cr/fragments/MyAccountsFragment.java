package com.carsgates.cr.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.carsgates.cr.R;
import com.carsgates.cr.activities.MainActivity;
import com.carsgates.cr.adapter.Page_Adapter;

/**
 * Created by lenovo on 3/30/2017.
 */

public class MyAccountsFragment extends Fragment {
View view;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my_account,container,false);

        tabLayout= (TabLayout) view.findViewById(R.id.account_tab);
        tabLayout.addTab(tabLayout.newTab().setText("Account"));
        tabLayout.addTab(tabLayout.newTab().setText("Drivers"));
        tabLayout.addTab(tabLayout.newTab().setText("Payment"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager= (ViewPager) view.findViewById(R.id.account_pager);
        AccountFragment accountFragment=new AccountFragment();
        DriversFragment driversFragment=new DriversFragment();
        PaymentFragment paymentFragment=new PaymentFragment();
        Page_Adapter adapter=new Page_Adapter(getChildFragmentManager(),tabLayout.getTabCount(),accountFragment,driversFragment,paymentFragment);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar =   ((MainActivity)getActivity()).toolbar ;
        toolbar.setTitle(getResources().getString(R.string.action_accounts));

    }
}

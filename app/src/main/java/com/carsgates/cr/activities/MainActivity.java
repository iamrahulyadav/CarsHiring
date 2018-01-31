package com.carsgates.cr.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.carsgates.cr.R;
import com.carsgates.cr.Utils.AppGlobal;
import com.carsgates.cr.Utils.Utility;
import com.carsgates.cr.fragments.MyAccountsFragment;
import com.carsgates.cr.fragments.MyBookingsFragment;
import com.carsgates.cr.fragments.SearchCarFragment;
import com.carsgates.cr.interfaces.ISubViewSetupHandler;
import com.mukesh.tinydb.TinyDB;

import java.util.Locale;

/**
 * Created by Atul Kumar Gupta on 5/3/2017.
 * Contact Number : +91 8470967433
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ISubViewSetupHandler {
    NavigationView navigationView;
    public Toolbar toolbar;
    public SearchQuery searchQuery = new SearchQuery();
    View v;
    String qu;
    TinyDB sherprf;
    DrawerLayout drawer;
    String userId,language_code;
    TextView txtemail, txtusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sherprf = new TinyDB(getApplicationContext());
        language_code = sherprf.getString("language_id");
//        updateResources(this,language_code);
        setContentView(R.layout.activity_main);
        appGlobal.context = getApplicationContext();

        userId = sherprf.getString("userid");
        v = MainActivity.this.findViewById(android.R.id.content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_home);

        language_code = sherprf.getString("language_code") ;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateResources(this,language_code);
        Intent it = getIntent();
       /*if (it != null) {
            qu = it.getStringExtra("From Quotes");
            if (qu != null) {
                if (qu.equalsIgnoreCase("Quotes")) {
                    setupSubView(R.id.action_quotes);
                }
            } else {
                setupSubView(R.id.action_search_car);
            }
        }*/
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onPrepareOptionsMenu(menu);
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.exit));
            builder.setMessage(getResources().getString(R.string.wantExit));
            builder.setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create();
            builder.show();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        updateResources(this,language_code);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private boolean updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        updateResources(this,language_code);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        qu = "";
        int id = item.getItemId();
        setupSubView(id);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        qu = "";
        int id = item.getItemId();
        setupSubView(id);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        updateResources(this,language_code);

        return true;
    }

    AppGlobal appGlobal = AppGlobal.getInstancess();


    @Override
    public void setupSubView(int id) {
        updateResources(this,language_code);
        switch (id) {
            case R.id.action_accounts:
                if (checkLogin()) {
                    MyAccountsFragment myaccountFragment = new MyAccountsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, myaccountFragment)
                            .addToBackStack("null").commit();
                    toolbar.setTitle(getResources().getString(R.string.action_accounts));
                }
                break;

            case R.id.action_quotes:
                if (checkLogin()) {
                    MyBookingsFragment fragment = new MyBookingsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Check", qu);
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,
                            fragment).addToBackStack("mybooking").commit();
                    toolbar.setTitle(getResources().getString(R.string.action_quotes));
                }
                break;

            case R.id.action_search_car:
                Bundle bundle = new Bundle();
                bundle.putSerializable("query", searchQuery);
                SearchCarFragment searchCarFragment = new SearchCarFragment();
                searchCarFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, searchCarFragment)
                        .addToBackStack("search").commit();
                toolbar.setTitle(getResources().getString(R.string.action_search_car));
                break;

            case R.id.action_about_us:
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                Toast.makeText(MainActivity.this, "About US Action", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_contact_us:
                startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                Toast.makeText(MainActivity.this, "Contact US Action", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_language:
//
                startActivity(new Intent(MainActivity.this, Language.class));
                Toast.makeText(MainActivity.this, "Language", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_currency:
                startActivity(new Intent(MainActivity.this, CurrencyActivity.class));
                Toast.makeText(MainActivity.this, "Currency Change", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                Toast.makeText(MainActivity.this, "Settings Action", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean checkLogin() {
        if (userId != null && !userId.trim().isEmpty()) {
            return true;
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return false;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_home);
        language_code = sherprf.getString("language_code") ;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent it = getIntent();
        final Fragment fragment;
        if (getSupportFragmentManager().getBackStackEntryCount()>0){
        }


//        if(getSupportFragmentManager().getBackStackEntryCount()>1)
//        {
//            getSupportFragmentManager().popBackStackImmediate();
//
//
//            } else {
//                setupSubView(R.id.action_search_car);
//        }

            qu = it.getStringExtra("From Quotes");
            if (qu != null) {
                if (qu.equalsIgnoreCase("Quotes")) {
                    setupSubView(R.id.action_quotes);
                }
            }
//            else if (getSupportFragmentManager().getBackStackEntryCount()>1){
//
//                int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
//                FragmentManager.BackStackEntry backStackEntry = getSupportFragmentManager().getBackStackEntryAt(index);
//                String tag = backStackEntry.getName();
//
//                fragment = getSupportFragmentManager().findFragmentByTag(tag);
//                if (fragment!=null){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, fragment).commit();
//                }
//            }
            else {
                setupSubView(R.id.action_search_car);
            }

        View view = navigationView.getHeaderView(0);
        txtemail = (TextView) view.findViewById(R.id.tvEmail);
        txtusername = (TextView) view.findViewById(R.id.tvUserName);
        String username = sherprf.getString("user_name");
        String email = sherprf.getString("user_email");
        if (username != null && email != null) {
            if (!username.isEmpty() || !email.isEmpty()) {
                txtemail.setText(email);
                txtusername.setText(username);
            }
        }
        checknetwork();
    }



    public void checknetwork() {
        if (!Utility.isNetworkConnected(MainActivity.this)) {
            Snackbar.make(v, "Check Your Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checknetwork();
                }
            }).setActionTextColor(getResources().getColor(R.color.redStrong)).show();
        }
    }
}


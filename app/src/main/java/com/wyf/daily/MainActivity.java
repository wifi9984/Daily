package com.wyf.daily;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private EventsFragment eventsFragment;
    private DebugFragment debugFragment;
    private android.app.Fragment isFragment;//showing fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFragment(savedInstanceState);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("所有事项");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    public void initFragment(Bundle savedInstanceState){
        if(savedInstanceState == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if(eventsFragment == null){
                eventsFragment = new EventsFragment();
            }
            isFragment = eventsFragment;
            ft.replace(R.id.fragment_main,eventsFragment).commit();
        }
    }

    public void switchContent(Fragment from,Fragment to){
        if(isFragment != to) {
            isFragment = to;
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if(!to.isAdded()){
                ft.hide(from).add(R.id.fragment_main,to).commit();
            }else {
                ft.hide(from).show(to).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        getSupportActionBar().setTitle(item.getTitle());
        int id = item.getItemId();

        if (id == R.id.nav_all) {
            if(eventsFragment == null){
                eventsFragment = new EventsFragment();
            }
            switchContent(isFragment,eventsFragment);
        } else if (id == R.id.nav_debug) {
            if(debugFragment == null){
                debugFragment = new DebugFragment();
            }
            switchContent(isFragment,debugFragment);
        } else if (id == R.id.nav_notes) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

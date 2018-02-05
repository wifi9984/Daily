package com.wyf.daily;

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

import com.avos.avoscloud.AVOSCloud;

/**
 * Main Activity
 *
 * @author wifi9984
 * @date 2017/8/31
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private EventsFragment eventsFragment;
    private DebugFragment debugFragment;
    private SettingsFragment settingsFragment;
    private SignUpFragment signUpFragment;
    // record showing fragment

    private android.app.Fragment isFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVOSCloud.initialize(this, "JwKhE7dXF0KNRlam6zUn1KMR-9Nh9j0Va", "3v4GrE3nHuGmRcytTRefRY97");
        AVOSCloud.setDebugLogEnabled(true);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFragment(savedInstanceState);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     *  初始化Toolbar
     */
    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("所有事项");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 初始化Fragment，默认加载EventsFragment
     *
     * @param savedInstanceState
     */
    public void initFragment(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if(eventsFragment == null){
                eventsFragment = new EventsFragment();
            }
            isFragment = eventsFragment;
            ft.replace(R.id.frame_main,eventsFragment).commit();
        }
    }

    /**
     * Fragment的切换
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from,Fragment to) {
        if(isFragment != to) {
            isFragment = to;
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if(!to.isAdded()){
                ft.hide(from).add(R.id.frame_main,to).commit();
            }else {
                ft.hide(from).show(to).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            if(signUpFragment == null){
                signUpFragment = new SignUpFragment();
            }
            switchContent(isFragment, signUpFragment);
        } else if (id == R.id.nav_settings) {
            if(settingsFragment == null){
                settingsFragment = new SettingsFragment();
            }
            switchContent(isFragment,settingsFragment);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

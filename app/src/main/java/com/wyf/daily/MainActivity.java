package com.wyf.daily;

import android.content.Intent;
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
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVOSCloud;

/**
 * Main Activity
 *
 * @author wifi9984
 * @date 2018/3/16
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private EventsFragment eventsFragment;
    private DebugFragment debugFragment;
    private SettingsFragment settingsFragment;
    // record showing fragment

    private android.app.Fragment isFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVOSCloud.initialize(this, "JwKhE7dXF0KNRlam6zUn1KMR-9Nh9j0Va", "3v4GrE3nHuGmRcytTRefRY97");
        AVOSCloud.setDebugLogEnabled(true);
        setContentView(R.layout.main_activity);
        initToolbar();
        initFragment(savedInstanceState);
        initNav();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     *  初始化Toolbar
     */
    void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
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
    void initFragment(Bundle savedInstanceState) {
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
     * 初始化侧滑页面
     */
    void initNav() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        ImageView loginEntrance = navHeader.findViewById(R.id.nav_user_avatar);
        loginEntrance.setOnClickListener(this);
    }

    /**
     * Fragment的切换
     *
     * @param from 当前的Fragment
     * @param to 指定的Fragment
     */
    void switchContent(Fragment from,Fragment to) {
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
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.nav_user_avatar):
                // 用户头像/注册登录入口的点击事件
                Intent intent = new Intent();
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
                onStop();
                break;
            default:
                break;
        }
    }
}

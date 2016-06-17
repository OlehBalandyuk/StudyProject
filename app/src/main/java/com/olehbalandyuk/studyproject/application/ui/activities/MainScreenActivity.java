package com.olehbalandyuk.studyproject.application.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.olehbalandyuk.studyproject.R;
import com.olehbalandyuk.studyproject.application.http.UserDetails;
import com.olehbalandyuk.studyproject.application.ui.fragments.cabinet.CabinetFragment;
import com.olehbalandyuk.studyproject.application.ui.fragments.contacts.ContactsFragment;
import com.olehbalandyuk.studyproject.application.ui.fragments.current_packet.CurrentPacketFragment;
import com.olehbalandyuk.studyproject.application.ui.fragments.messages.MessagesFragment;

public class MainScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainScreenActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;

    private static final int CURRENT_PACKET = 0;
    private static final int CABINET = 1;
    private static final int MESSAGES = 2;
    private static final int CONTACTS = 3;

    private boolean isLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mToolbar = (Toolbar) findViewById(R.id.main_screen_toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);

        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        Log.v(TAG, "<< Method: onCreate()");
    }

    public void onResume() {
        super.onResume();
        Log.v(TAG, ">> Method: onResume()");

        isLogged = UserDetails.isLoggedByLogin(this);
        Log.v(TAG, "--> Method: onResume(), is logged = " + isLogged);

        if (isLogged) {
            loadCabinetFragment();
        } else {
            redirectToLoginActivity();
        }
        Log.v(TAG, "<< Method: onResume()");
    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, ">> Method: onBackPressed()");

        if (drawerIsOpened()) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }

        Log.v(TAG, "<< Method: onBackPressed()");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.v(TAG, ">> Method: onNavigationItemSelected()");
        int id = item.getItemId();
        String[] titles = getResources().getStringArray(R.array.fragments);
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_current_packet:
                mToolbar.setTitle(titles[0]);
                fragment = new CurrentPacketFragment();
                break;
            case R.id.nav_cabinet:
                mToolbar.setTitle(titles[1]);
                fragment = new CabinetFragment();
                break;
            case R.id.nav_messages:
                mToolbar.setTitle(titles[2]);
                fragment = new MessagesFragment();
                break;
            case R.id.nav_contacts:
                mToolbar.setTitle(titles[3]);
                fragment = new ContactsFragment();
                break;
            case R.id.nav_log_out:
                UserDetails.logout(this);
                redirectToLoginActivity();
                finish();
                return true;
        }

        addFragment(fragment);

        closeDrawer();

        Log.v(TAG, "<< Method: onNavigationItemSelected()");
        return true;
    }


    private void addFragment(Fragment fragment) {
        Log.v(TAG, ">> Method: addFragment()");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        Log.v(TAG, "<< Method: addFragment()");
    }

    private void redirectToLoginActivity() {
        Intent loginScreen = new Intent(this, LoginActivity.class);
        startActivity(loginScreen);
    }

    private void loadCabinetFragment() {
        mNavigationView.getMenu().getItem(CABINET).setChecked(true);
        onNavigationItemSelected(mNavigationView.getMenu().getItem(CABINET));
    }

    private void closeDrawer() {
        mDrawer.closeDrawer(GravityCompat.START);
    }

    private boolean drawerIsOpened() {
        return mDrawer.isDrawerOpen(GravityCompat.START);
    }
}

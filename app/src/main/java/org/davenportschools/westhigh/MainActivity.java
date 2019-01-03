package org.davenportschools.westhigh;

import android.Manifest;
import android.content.DialogInterface;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private NewsFeedFragment newsFeedFragment;
    private StaffDirectoryFragment staffDirectoryFragment;
    private BellScheduleFragment bellScheduleFragment;
    private AcademicCalendarFragment academicCalendarFragment;
    private LunchMenuFragment lunchMenuFragment;
    private TitleCardFragment titleCardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        newsFeedFragment = new NewsFeedFragment();
        staffDirectoryFragment = new StaffDirectoryFragment();
        bellScheduleFragment = new BellScheduleFragment();
        academicCalendarFragment = new AcademicCalendarFragment();
        lunchMenuFragment = new LunchMenuFragment();
        titleCardFragment = new TitleCardFragment();

        setTitle("West High School");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, titleCardFragment).commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        boolean clearTitleCard = true;

        if (id == R.id.nav_news_feed) {
            setTitle(R.string.news_feed);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, newsFeedFragment).commit();
        } else if (id == R.id.nav_staff_directory) {
            setTitle(R.string.staff_directory);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, staffDirectoryFragment).commit();
        } else if (id == R.id.nav_bell_schedule) {
            setTitle(R.string.bell_schedule);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, bellScheduleFragment).commit();
        } else if (id == R.id.nav_academic_calendar) {
            setTitle(R.string.academic_calendar);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, academicCalendarFragment).commit();
        } else if (id == R.id.nav_lunch_menu) {
            setTitle(R.string.lunch_menu);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, lunchMenuFragment).commit();
        } else if (id == R.id.nav_communicate_twitter) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=DavenportWest")));
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/DavenportWest")));
            }

            clearTitleCard = false;
        } else if (id == R.id.nav_communicate_call) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
            } else {
                callSchool();
            }

            clearTitleCard = false;
        }

        if (clearTitleCard)
            titleCardFragment = null;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            callSchool();
        }
    }

    private void callSchool(){
        new AlertDialog.Builder(this)
            .setTitle("Call West")
            .setMessage("Are you sure you want to call Davenport West High School?")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:5637235600")));
                }
            })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    public void titleCardButtonPressed(View view) {
        Button button = (Button)view;
        String text = button.getText().toString();

        if (text.equalsIgnoreCase(getString(R.string.news_feed))) {
            setTitle(R.string.news_feed);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, newsFeedFragment).commit();
        } else if (text.equalsIgnoreCase(getString(R.string.staff_directory))) {
            setTitle(R.string.staff_directory);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, staffDirectoryFragment).commit();
        } else if (text.equalsIgnoreCase(getString(R.string.bell_schedule))) {
            setTitle(R.string.bell_schedule);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, bellScheduleFragment).commit();
        } else if (text.equalsIgnoreCase(getString(R.string.academic_calendar))) {
            setTitle(R.string.academic_calendar);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, academicCalendarFragment).commit();
        } else if (text.equalsIgnoreCase(getString(R.string.lunch_menu))) {
            setTitle(R.string.lunch_menu);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, lunchMenuFragment).commit();
        }
    }
}

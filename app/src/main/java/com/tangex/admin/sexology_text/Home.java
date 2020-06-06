package com.tangex.admin.sexology_text;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private SectionsPagerAdapter_Home mSectionPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mtoggle;

    private DatabaseReference RootRef;

    private SharedPreferences sharedPreferences;
    private SharedPreferences fisrt_open;


    private String iduser;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iduser = getIntent().getStringExtra("iduser");

        sharedPreferences = getSharedPreferences("place_id", Context.MODE_MULTI_PROCESS);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Home.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LOGIN", iduser);
        editor.commit();
        editor.apply();

        fisrt_open = getSharedPreferences("place_fisrt", Context.MODE_MULTI_PROCESS);
        fisrt_open = PreferenceManager.getDefaultSharedPreferences(Home.this);
        final Boolean check_first = fisrt_open.getBoolean("FIRST", true);
        SharedPreferences.Editor fisrt_edit = fisrt_open.edit();
        if (check_first != false) {
            editor.putBoolean("FIRST", true);
        }
        editor.commit();
        editor.apply();

        RootRef = FirebaseDatabase.getInstance().getReference();

        final TextView search_edit = (TextView) findViewById(R.id.search_inhome);


        search_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent k = new Intent(Home.this, Search_Act.class);
                startActivity(k);

            }
        });


        mToolbar = (Toolbar) findViewById(R.id.main_page_bar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.nagavition_menu);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);


// ----------------------Tao Tablayout-Viewpager--------------------
        CreateTabLayout();


// ----------------Tao Slide-Menu----------------------------
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mtoggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open, R.string.close);

        drawerLayout.setDrawerListener(mtoggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (getIntent().getIntExtra("gt", 0) == 1) {
            Menu item = navigationView.getMenu();
            item.findItem(R.id.action_notif).setVisible(false);
        }
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mtoggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mtoggle.onConfigurationChanged(newConfig);
    }

    //    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //------------------------CLICK IN NAVIGATION----------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//        if (mtoggle.onOptionsItemSelected(item)){
//            return true;
//        }
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(Home.this, Settings.class);
            intent.putExtra("iduser", iduser);
            startActivity(intent);
            onResume();
        } else if (item.getItemId() == R.id.action_convers) {
            Intent intentt = new Intent(Home.this, Conversation.class);
            intentt.putExtra("iduser", iduser);
            startActivity(intentt);

        } else if (item.getItemId() == R.id.action_notif) {
            Intent to_notif = new Intent(Home.this, Notification_act.class);
            to_notif.putExtra("iduser", iduser);
            startActivity(to_notif);
        } else if (
                item.getItemId() == R.id.action_thontin
                ) {
            Intent intent = new Intent(Home.this, ThongtinLHe.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_forum) {
            Intent toforum = new Intent(Home.this, Forum.class);
            toforum.putExtra("iduser", iduser);
            startActivity(toforum);
        } else if (item.getItemId() == R.id.action_mail) {
            final Intent mail = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","khuongan.tech@gmail.com",null));
            mail.putExtra(Intent.EXTRA_SUBJECT,"Phản hồi");
            startActivity(Intent.createChooser(mail,"Send mail ..."));
        } else if (item.getItemId() == R.id.action_logout) {
//            sharedPreferences = getSharedPreferences("place_id",Context.MODE_MULTI_PROCESS);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Home.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("LOGIN");
            editor.commit();
            editor.apply();
            Intent log_out = new Intent(Home.this, Login.class);
            startActivity(log_out);
            finish();

//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
//            drawer.closeDrawer(GravityCompat.START);
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void CreateTabLayout() {
        mViewPager = (ViewPager) findViewById(R.id.home_tabPager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabsPager);


        mSectionPagerAdapter = new SectionsPagerAdapter_Home(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setIcon(R.drawable.defaut_avt);
    }

    private void updateUserStatus(String state) {
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");

        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");

        saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();

        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date", saveCurrentDate);
        onlineStateMap.put("state", state);


//
        RootRef.child("User").child(iduser).child("userState").updateChildren(onlineStateMap);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    @Override
    protected void onStart() {
        super.onStart();


        if (isNetworkAvailable()) {
            updateUserStatus("online");
        }
        if (!isNetworkAvailable()) {
            updateUserStatus("offline");
        }

//
//        sharedPreferences = getSharedPreferences("place_id", Context.MODE_MULTI_PROCESS);
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Home.this);
        String login = sharedPreferences.getString("LOGIN", null);

        if ((login == null) && (iduser == null)) {
            final Intent i = new Intent(Home.this, Login.class);
            startActivity(i);
        }
    }
}



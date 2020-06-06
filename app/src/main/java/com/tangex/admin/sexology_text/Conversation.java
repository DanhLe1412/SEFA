package com.tangex.admin.sexology_text;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Conversation extends AppCompatActivity {
    private Toolbar mToolbar;
    private SectionsPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String iduser;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        iduser = getIntent().getStringExtra("iduser");


        mViewPager = (ViewPager) findViewById(R.id.chat_tabsPager);
        mTabLayout = (TabLayout) findViewById(R.id.chat_tabsLayout);

        mToolbar = (Toolbar) findViewById(R.id.chat_page_bar);
        setSupportActionBar(mToolbar);

        mSectionPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionPagerAdapter);


        mTabLayout.setupWithViewPager(mViewPager);
    }
}

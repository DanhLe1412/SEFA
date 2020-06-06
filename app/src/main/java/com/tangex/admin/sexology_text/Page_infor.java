package com.tangex.admin.sexology_text;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Page_infor extends AppCompatActivity {

  private  WebView webView;
  private   DatabaseReference ref;
  private  DatabaseReference erf;
  private String thutu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_infor);

        webView = (WebView) findViewById(R.id.webview);

        Intent lay_thu_tu = getIntent();
        final String laythutu = lay_thu_tu.getStringExtra("thu tu");

        if (laythutu.length() >100) {

            erf = FirebaseDatabase.getInstance().getReference();
            erf.child("Provide").orderByChild("file").equalTo(laythutu).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot Child : dataSnapshot.getChildren()) {
                        thutu = Child.getKey();

                        erf.keepSynced(true);
                        ref = FirebaseDatabase.getInstance().getReference().child("Provide").child(thutu);
                        ref.keepSynced(true);


                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String Url = dataSnapshot.child("file").getValue().toString();

//
//                webView.setWebViewClient(new WebViewClient());
//                webView.loadUrl(thutu);
//                WebSettings k = webView.getSettings();
//                k.setBuiltInZoomControls(true);
//                k.setDisplayZoomControls(false);
//                k.setJavaScriptEnabled(true);

                                webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
                                webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
                                webView.getSettings().setAllowFileAccess(true);
                                webView.getSettings().setAppCacheEnabled(true);
                                webView.getSettings().setJavaScriptEnabled(true);
                                webView.getSettings().setBuiltInZoomControls(true);
                                webView.getSettings().setDisplayZoomControls(false);
                                webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default

                                if (!isNetworkAvailable()) { // loading offline
                                    webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                                }
                                webView.loadUrl(Url);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {

//            erf.keepSynced(true);
            ref = FirebaseDatabase.getInstance().getReference().child("Provide").child(laythutu);
            ref.keepSynced(true);


            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Url = dataSnapshot.child("file").getValue().toString();

//
//                webView.setWebViewClient(new WebViewClient());
//                webView.loadUrl(thutu);
//                WebSettings k = webView.getSettings();
//                k.setBuiltInZoomControls(true);
//                k.setDisplayZoomControls(false);
//                k.setJavaScriptEnabled(true);

                    webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
                    webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
                    webView.getSettings().setAllowFileAccess(true);
                    webView.getSettings().setAppCacheEnabled(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.getSettings().setDisplayZoomControls(false);
                    webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default

                    if (!isNetworkAvailable()) { // loading offline
                        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    }
                    webView.loadUrl(Url);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//
        }


  }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

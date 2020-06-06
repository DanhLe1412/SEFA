package com.tangex.admin.sexology_text;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

public class Profile_user extends AppCompatActivity {

    private String visit_user_id;
    private String current_state;

    private ImageView mProfile_img;
    private Button btn_send,btn_dec;

    private DatabaseReference profile_ref;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        webView = (WebView) findViewById(R.id.infor_ex);

        Intent fr = getIntent();
        final String iduser = fr.getStringExtra("iduser");
        visit_user_id = fr.getStringExtra("visit_user_id");


        mProfile_img = (ImageView) findViewById(R.id.profile_image);
        btn_send = (Button) findViewById(R.id.profile_send_mess);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_mess = new Intent(Profile_user.this,message.class);
                to_mess.putExtra("iduser",iduser);
                to_mess.putExtra("user_want_to_mess",visit_user_id);
                startActivity(to_mess);

            }
        });

        profile_ref = FirebaseDatabase.getInstance().getReference().child("User").child(visit_user_id);

        profile_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String dis_name = dataSnapshot.child("userName").getValue().toString();


                String image_url = dataSnapshot.child("image").getValue().toString();
                Picasso.get().load(image_url).into(mProfile_img);
                if (dataSnapshot.hasChild("thongtin")){
                    String Url = dataSnapshot.child("thongtin").getValue().toString();

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.tangex.admin.sexology_text;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Forum extends AppCompatActivity {

    private Toolbar toolbar;

    private Spinner spinner_nav;

    private Dialog fDialog;

    private AdapterForum_RecyclerView forumAdapter;
    private RecyclerView list_diss;

    private List<disscusion_db> forum_list;

    private DatabaseReference ref_forum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        list_diss = (RecyclerView) findViewById(R.id.list_disscusion);

        forum_list = new ArrayList<>();

        forumAdapter = new AdapterForum_RecyclerView(forum_list);

        ref_forum = FirebaseDatabase.getInstance().getReference();


        list_diss.setLayoutManager(new LinearLayoutManager(Forum.this));
        list_diss.setAdapter(forumAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar_forum);
        spinner_nav = (Spinner) findViewById(R.id.spinner_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        addItemsToSpinner();


    }


    private void addItemsToSpinner() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Tất cả");
        list.add("Nam");
        list.add("Nữ");
        list.add("LGBT");

        SpinnerCustomAdapter spinnerCustomAdapter = new SpinnerCustomAdapter(Forum.this, list);

        final Query query = ref_forum.child("Forum");

        spinner_nav.setAdapter(spinnerCustomAdapter);


//        query.orderByChild("thoigianDang").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
//
//                final disscusion_db disscusionDb = dataSnapshot.getValue(disscusion_db.class);
//
//                spinner_nav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        String item = parent.getItemAtPosition(position).toString();
//                            if (item.equals("Nam")){
//                                if (dataSnapshot.child("nam").getValue().hashCode()==1){
//                                    forum_list.add(disscusionDb);
//                                    Toast.makeText(Forum.this, "okNam", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            else if (item.equals("Nữ")){
//                                if (disscusionDb.getNu()==1){forum_list.add(disscusionDb);}
//                            }
//                            else if (item.equals("LGBT")){
//                                if (disscusionDb.getLgbt()==1){forum_list.add(disscusionDb);}
//                            } else {
//                                if (forum_list.isEmpty()){
//                                    forum_list.add(disscusionDb);
//                                }else forum_list.clear();
//                            }
//                            forumAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.spiner_inf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_spinner_add) {
            Intent to_push = new Intent(Forum.this, Forum_push_question.class);
            to_push.putExtra("iduser", getIntent().getStringExtra("iduser"));
            to_push.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(to_push);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Query query_all = ref_forum.child("Forum");;
        spinner_nav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String item = parent.getItemAtPosition(position).toString();
                query_all.orderByChild("thoigianDang").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (item.equals("Nam")) {
                            forum_list.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final disscusion_db disscusionDb = snapshot.getValue(disscusion_db.class);
                                if (disscusionDb.getNam()==1){
                                    forum_list.add(disscusionDb);
                                }
                            }
                        } else if (item.equals("Nữ")) {
                            forum_list.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final disscusion_db disscusionDb = snapshot.getValue(disscusion_db.class);
                                if (disscusionDb.getNu()==1){
                                    forum_list.add(disscusionDb);
                                }
                            }
                        } else if (item.equals("LGBT")) {
                            forum_list.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final disscusion_db disscusionDb = snapshot.getValue(disscusion_db.class);
                                if (disscusionDb.getLgbt()==1){
                                    forum_list.add(disscusionDb);
                                }
                            }
                        } else {
                            forum_list.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final disscusion_db disscusionDb = snapshot.getValue(disscusion_db.class);
                                forum_list.add(disscusionDb);

                            }
                        }
                        forumAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

package com.tangex.admin.sexology_text;


import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment {

    private List<get_inf> blog_list;

    private View ContactView;
    private RecyclerView Inf_Home_list;
    private DatabaseReference ref;
    private DatabaseReference ref_user;
    private String iduser;
    private int gt;
    private Context context;

    private int currentPage = 0;

    private AdapterBlog_home_newfeed adapter;

    private static boolean calledAlready = false;


    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        ref = FirebaseDatabase.getInstance().getReference();
        ref_user = FirebaseDatabase.getInstance().getReference();
        ContactView = inflater.inflate(R.layout.fragment_home, container, false);
        blog_list = new ArrayList<>();

        Inf_Home_list = (RecyclerView) ContactView.findViewById(R.id.inf_home_list);

        adapter = new AdapterBlog_home_newfeed(blog_list);
        Inf_Home_list.setLayoutManager(new LinearLayoutManager(getContext()));
        Inf_Home_list.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        Inf_Home_list.setLayoutManager(linearLayoutManager);

        LoadData();

//        Inf_Home_list.setLayoutManager(new LinearLayoutManager(getContext()));
//        context = inflater.getContext();

        return ContactView;
    }

    private void LoadData() {
        String iduser = getActivity().getIntent().getStringExtra("iduser");
        ref_user.child(iduser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("gioitinh")) {
                    gt = dataSnapshot.child("gioitinh").getValue(Integer.class);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Query ref_da = ref.child("Provide");
        ref_da.orderByChild("stt").startAt(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.hasChildren()) {
//                    currentPage--;
//                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    get_inf post = postSnapshot.getValue(get_inf.class);
                    if (post.getTile() == 5) {
                        blog_list.add(post);
                    }
                    Collections.reverse(blog_list);
                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    get_inf post = postSnapshot.getValue(get_inf.class);
                    if (post.getTile() == gt) {
                        blog_list.add(post);
                    }
                    Collections.reverse(blog_list);
                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    get_inf post = postSnapshot.getValue(get_inf.class);
                    blog_list.add(post);

//                    Collections.reverse(blog_list);
                }
                adapter.notifyDataSetChanged();

//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

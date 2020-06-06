package com.tangex.admin.sexology_text;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Story extends Fragment {


    private List<get_inf> post_list;

    private View ContactView;
    private RecyclerView mHealthList;;
    private DatabaseReference reference;
    private Query query;
    private Context context;

    private AdapterBlog_Info adapter;

    public Fragment_Story() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ContactView = inflater.inflate(R.layout.fragment_story, container, false);


        post_list = new ArrayList<>();

        mHealthList = (RecyclerView)  ContactView.findViewById(R.id.story_list);


        reference = FirebaseDatabase.getInstance().getReference();
        query = reference.child("Provide");

        adapter = new AdapterBlog_Info(post_list);
        mHealthList.setLayoutManager(new LinearLayoutManager(getContext()));
        mHealthList.setAdapter(adapter);

        query.orderByChild("stt").startAt(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    get_inf post = postSnapshot.getValue(get_inf.class);
                    if (post.getTitle().equals("story")){post_list.add(post);}


                }

                Collections.reverse(post_list);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference.keepSynced(true);
        return ContactView;
    }

}

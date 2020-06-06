package com.tangex.admin.sexology_text;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Heath extends Fragment {

    private List<get_inf> post_list, b;

    private View ContactView;
    private RecyclerView mHealthList;
    ;
    private DatabaseReference reference;
    private Query query;
    private Context context;

    private AdapterBlog_Info adapter;


    private ViewPager frag_ViewPager;
    private TabLayout frag_TabLayout;

    public Fragment_Heath() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ContactView = inflater.inflate(R.layout.fragment_heath, container, false);

//        CreateTabInFragment(ContactView);

        post_list = new ArrayList<>();


        mHealthList = (RecyclerView) ContactView.findViewById(R.id.heal_list);

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
                    if (post.getTitle().equals("health")){post_list.add(post);}


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

    // private void CreateTabInFragment(View view){
//        frag_TabLayout = view.findViewById(R.id.result_tabs);
//        frag_ViewPager = view.findViewById(R.id.viewpager);
//        sectionPageAdapterInFragment = new SectionPageAdapterInFragment(getChildFragmentManager());
//        frag_ViewPager.setAdapter(sectionPageAdapterInFragment);
//        frag_TabLayout.setupWithViewPager(frag_ViewPager);
//
// }

}

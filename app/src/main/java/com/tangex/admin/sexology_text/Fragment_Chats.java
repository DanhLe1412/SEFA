package com.tangex.admin.sexology_text;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Chats.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Fragment_Chats extends Fragment {

    private View ContactView;
    private RecyclerView mChats_list;
    private DatabaseReference ref;
    private OnFragmentInteractionListener mListener;
    private String iduser;
    private List<String> hisList;

    private his_user_chat_adapter hisUserChatAdapter;

    public Fragment_Chats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        ContactView = inflater.inflate(R.layout.fragment_chats,container,false);
        mChats_list = (RecyclerView) ContactView.findViewById(R.id.his_chats_list);


        hisList = new ArrayList<>();


        hisUserChatAdapter = new his_user_chat_adapter(hisList,getContext());

        mChats_list.setLayoutManager(new LinearLayoutManager(getContext()));
        mChats_list.setAdapter(hisUserChatAdapter);

        iduser = getActivity().getIntent().getStringExtra("iduser");
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Mess").child(iduser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String hisID = snapshot.getKey();
                    Log.d("id",hisID);
                    hisList.add(hisID);
                }
                hisUserChatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return ContactView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}

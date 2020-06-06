package com.tangex.admin.sexology_text;

import android.content.Context;
import android.content.Intent;
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

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Friends.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Fragment_Friends extends Fragment {


    private View ContactView;
    private RecyclerView mFriendsList;
    private DatabaseReference ref;
    private DatabaseReference ref_user_onl;
    private OnFragmentInteractionListener mListener;


    public Fragment_Friends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ContactView = inflater.inflate(R.layout.fragment_friends, container, false);
        mFriendsList = (RecyclerView) ContactView.findViewById(R.id.friend_list);
        mFriendsList.setLayoutManager(new LinearLayoutManager(getContext()));


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
        final String iduser = getActivity().getIntent().getStringExtra("iduser");
        ref = FirebaseDatabase.getInstance().getReference().child("User");
        ref_user_onl = FirebaseDatabase.getInstance().getReference();
        final Query query = ref.orderByChild("loai").equalTo(1);
        final Query query1 = ref_user_onl.child("User").child(iduser);

//        Query query = ref;

        FirebaseRecyclerOptions<getUser> options = new FirebaseRecyclerOptions.Builder<getUser>().setQuery(query, getUser.class).build();


        FirebaseRecyclerAdapter<getUser, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<getUser, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UsersViewHolder holder, final int position, @NonNull final getUser model) {

                ref_user_onl.child("User").child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        holder.name.setText(model.getUserName());
                        Picasso.get().load(model.getImage()).into(holder.user_img);
                        holder.hmm.setText("xin chafo");

                        if (dataSnapshot.child("userState").hasChild("state")){
                            if (dataSnapshot.child("userState").child("state").getValue().toString() == "online"){
                                holder.logo_online.setVisibility(View.VISIBLE);
                            }else holder.hmm.setText("off");
                        }
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//
                                Intent get_uid = getActivity().getIntent();
                                String iduser = get_uid.getStringExtra("iduser");

                                String vist_user_id = getRef(position).getKey();

                                Intent profile_int = new Intent(getContext(), Profile_user.class);
                                profile_int.putExtra("visit_user_id", vist_user_id);
                                profile_int.putExtra("iduser", iduser);

                                startActivity(profile_int);

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, parent, false);
                UsersViewHolder viewHolder = new UsersViewHolder(view);
                return viewHolder;
            }
        };
        mFriendsList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private static class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView hmm;
        CircleImageView user_img;
        ImageView logo_online;

        public UsersViewHolder(View itemView) {
            super(itemView);
            hmm = itemView.findViewById(R.id.hmm);
            name = itemView.findViewById(R.id.user_single_name);
            user_img = itemView.findViewById(R.id.users_single_image);
            logo_online = itemView.findViewById(R.id.logo_online);
        }
    }
}

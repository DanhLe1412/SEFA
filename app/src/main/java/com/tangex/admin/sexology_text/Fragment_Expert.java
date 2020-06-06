package com.tangex.admin.sexology_text;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Expert.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Fragment_Expert extends Fragment {

    private View ContactView;
    private RecyclerView mExpert_List;
    private DatabaseReference ref;
    private OnFragmentInteractionListener mListener;

    public Fragment_Expert() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        ContactView = inflater.inflate(R.layout.fragment_expert, container, false);
        mExpert_List = (RecyclerView) ContactView.findViewById(R.id.expert_list);
        mExpert_List.setLayoutManager(new LinearLayoutManager(getContext()));


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

        ref = FirebaseDatabase.getInstance().getReference().child("User");
        Query query = ref.orderByChild("loai").equalTo(2);

//        Query query = ref;

        FirebaseRecyclerOptions<getUser> options = new FirebaseRecyclerOptions.Builder<getUser>().setQuery(query, getUser.class).build();


        FirebaseRecyclerAdapter<getUser, Fragment_Expert.Expert_ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<getUser, Expert_ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Fragment_Expert.Expert_ViewHolder holder, final int position, @NonNull final getUser model) {
                holder.name.setText(model.getUserName());
                Picasso.get().load(model.getImage()).into(holder.user_img);

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

            @NonNull
            @Override
            public Fragment_Expert.Expert_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, parent, false);
                Fragment_Expert.Expert_ViewHolder viewHolder = new Fragment_Expert.Expert_ViewHolder(view);
                return viewHolder;
            }
        };
        mExpert_List.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
    private static class Expert_ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView user_img;

        public Expert_ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_single_name);
            user_img = itemView.findViewById(R.id.users_single_image);
        }
    }
}

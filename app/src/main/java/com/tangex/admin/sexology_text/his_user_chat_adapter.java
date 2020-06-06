package com.tangex.admin.sexology_text;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class his_user_chat_adapter extends RecyclerView.Adapter<his_user_chat_adapter.ViewHolder> {
    private List<String> listUID;
    private Context context;
    private DatabaseReference ref;
    private SharedPreferences sharedPreferences;

    public his_user_chat_adapter(List<String> listUID, Context context) {
        this.listUID = listUID;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        sharedPreferences = context.getSharedPreferences("place_id", Context.MODE_MULTI_PROCESS);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String iduser = sharedPreferences.getString("LOGIN", null);

        final String idU = listUID.get(position);
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("User").child(idU).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String ima = dataSnapshot.child("image").getValue().toString();
                Picasso.get().load(ima).into(holder.circleImageView);
                String userName = dataSnapshot.child("userName").getValue().toString();
                holder.textView.setText(userName);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toMess = new Intent(context,message.class);
                        toMess.putExtra("user_want_to_mess",idU);
                        toMess.putExtra("iduser",iduser);
                        holder.itemView.getContext().startActivity(toMess);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return listUID.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.users_single_image);
            textView = itemView.findViewById(R.id.user_single_name);
        }
    }
}

package com.tangex.admin.sexology_text;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.MessageViewHolder> {
    private List<message_in_db> userMessageList;

    private SharedPreferences sharedPreferences;
    private Context context;

    private DatabaseReference dataref;

    public messageAdapter(List<message_in_db> userMessageList, Context context) {
        this.userMessageList = userMessageList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mess_single, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position) {

        sharedPreferences = context.getSharedPreferences("place_id", Context.MODE_MULTI_PROCESS);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String iduser = sharedPreferences.getString("LOGIN", null);

        final message_in_db message = userMessageList.get(position);

        final String fromUserID = message.getFrom();

        dataref = FirebaseDatabase.getInstance().getReference().child("User").child(fromUserID);

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String receiveIma = dataSnapshot.child("image").getValue().toString();

                Picasso.get().load(receiveIma).into(holder.receiverProfileImage);


                holder.receiverMessageText.setVisibility(View.INVISIBLE);
                holder.senderMessageText.setVisibility(View.INVISIBLE);

                if (fromUserID.equals(iduser)) {
                    holder.receiverProfileImage.setVisibility(View.INVISIBLE);
                    holder.senderMessageText.setVisibility(View.VISIBLE);
                    holder.senderMessageText.setBackgroundResource(R.drawable.background_send_mess);
                    holder.senderMessageText.setText(message.getMessage());
                } else {
                    holder.receiverMessageText.setVisibility(View.VISIBLE);
                    holder.receiverProfileImage.setVisibility(View.VISIBLE);
                    holder.receiverMessageText.setBackgroundResource(R.drawable.background_retrieve_mess);
                    holder.receiverMessageText.setText(message.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView senderMessageText, receiverMessageText;
        private CircleImageView receiverProfileImage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            senderMessageText = (TextView) itemView.findViewById(R.id.messSendText);
            receiverMessageText = (TextView) itemView.findViewById(R.id.messReceiText);
            receiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);

        }
    }
}

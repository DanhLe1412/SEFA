package com.tangex.admin.sexology_text;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class message extends AppCompatActivity {
    private int importance = NotificationManager.IMPORTANCE_HIGH;

    private String iduser;
    private String user_wants_to_chat;


    private Button sendBtn;
    private EditText inputMessText_edt;

    private DatabaseReference ref;
    private DatabaseReference ref_user_wants_to_chat;
    private DatabaseReference ref_content;
    private DatabaseReference ref_here_users;

    private RecyclerView mChatList;
    private List<message_in_db> messageList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private messageAdapter messageAdapter;

    private Toolbar mesToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        sendBtn = (Button) findViewById(R.id.send_mess_btn);
        inputMessText_edt = (EditText) findViewById(R.id.input_mess);
        messageAdapter = new messageAdapter(messageList,message.this);
        mChatList = (RecyclerView) findViewById(R.id.list_mess );
        linearLayoutManager = new LinearLayoutManager(this);
        mChatList.setLayoutManager(linearLayoutManager);
        mChatList.setAdapter(messageAdapter);


        Intent get_int = getIntent();
        iduser = get_int.getStringExtra("iduser");
        ref_content = FirebaseDatabase.getInstance().getReference();
        user_wants_to_chat = get_int.getStringExtra("user_want_to_mess");



        ref = FirebaseDatabase.getInstance().getReference();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
                inputMessText_edt.setText(null);
            }
        });

    }


    private void SendMessage() {
        String messageText = inputMessText_edt.getText().toString();
        if (TextUtils.isEmpty(messageText)) {
            Toast.makeText(this, "Hãy nhập tin nhắn", Toast.LENGTH_SHORT).show();
        } else {
            String messageSenderRef = "Mess/" + iduser + "/" + user_wants_to_chat;
            String messageReceiverRef = "Mess/" + user_wants_to_chat + "/" + iduser;

            ref_here_users = FirebaseDatabase.getInstance().getReference().child("Mess").child(user_wants_to_chat).child(iduser).push();
            ref_user_wants_to_chat = FirebaseDatabase.getInstance().getReference().child("Mess").child(iduser).child(user_wants_to_chat).push();
            String pushID = ref_user_wants_to_chat.getKey();

            Map messTextBody = new HashMap();
            messTextBody.put("message", messageText);
            messTextBody.put("from",iduser);


           ref_user_wants_to_chat.setValue(messTextBody);
           ref_here_users.setValue(messTextBody);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Mess").child(iduser).child(user_wants_to_chat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                message_in_db message=dataSnapshot.getValue(message_in_db.class);
                messageList.add(message);
                messageAdapter.notifyDataSetChanged();

                mChatList.smoothScrollToPosition(mChatList.getAdapter().getItemCount());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

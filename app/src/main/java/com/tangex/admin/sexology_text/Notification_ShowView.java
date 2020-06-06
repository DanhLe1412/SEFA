package com.tangex.admin.sexology_text;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Notification_ShowView extends AppCompatActivity {
        private DatabaseReference first_retr;
        private TextView lastDateTT_tv;
        private TextView GuestDateTT_tv;

        private Button updateDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__show_view);
        updateDate = (Button) findViewById(R.id.showView_update_date);
        lastDateTT_tv = (TextView) findViewById(R.id.ngayTT);
        GuestDateTT_tv = (TextView) findViewById(R.id.date_not);

        updateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent comeNotifAct = new Intent(Notification_ShowView.this,Notification_act.class);
                comeNotifAct.putExtra("vitringay",1);
                comeNotifAct.putExtra("iduser",getIntent().getStringExtra("iduser"));
                startActivity(comeNotifAct);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        first_retr = FirebaseDatabase.getInstance().getReference();
        final String iduser = getIntent().getStringExtra("iduser");

        first_retr.child("User").child(iduser).child("user_not_inf").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("ngayDuDoan") && dataSnapshot.hasChild("ngayGanDay")){
                    lastDateTT_tv.setText(dataSnapshot.child("ngayGanDay").getValue().toString());
                    GuestDateTT_tv.setText(dataSnapshot.child("ngayDuDoan").getValue().toString());
                } else {
                    Toast.makeText(Notification_ShowView.this, "Sắp fix được bug r", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

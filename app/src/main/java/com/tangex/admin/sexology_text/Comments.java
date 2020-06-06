package com.tangex.admin.sexology_text;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comments extends AppCompatActivity {

    private String idBaiDang;

    private DatabaseReference ref;
    private DatabaseReference ref_2;

    private TextView tieudeCmt;
    private TextView noidungCmt;

    private EditText edtCmt;
    private Button BtnCmt;

    private SharedPreferences sharedPreferences;

    private Comment_db comment_db = new Comment_db();

    private RecyclerView recycler_comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        recycler_comment = (RecyclerView) findViewById(R.id.list_comments);
        recycler_comment.setLayoutManager(new LinearLayoutManager(Comments.this));
        recycler_comment.setHasFixedSize(false);

        tieudeCmt = (TextView) findViewById(R.id.comment_tieude);
        noidungCmt = (TextView) findViewById(R.id.comment_noidung);
        edtCmt = (EditText) findViewById(R.id.input_cmt);
        BtnCmt = (Button) findViewById(R.id.push_cmt_btn);

        ref = FirebaseDatabase.getInstance().getReference();

        idBaiDang = getIntent().getStringExtra("idBaiDang");

        ref.child("Forum").child(idBaiDang).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tieudeCmt.setText(dataSnapshot.child("tieude").getValue().toString());
                noidungCmt.setText(dataSnapshot.child("noidung").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        BtnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String noidungCmt = edtCmt.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                if (!noidungCmt.equals(null)) {
                    sharedPreferences = getSharedPreferences("place_id", Context.MODE_MULTI_PROCESS);
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Comments.this);
                    final String iduser = sharedPreferences.getString("LOGIN", null);
                    comment_db.setIduserCmt(iduser);
                    comment_db.setNoidungCmt(noidungCmt);
                    comment_db.setThoigianCmt(calendar.getTimeInMillis());
                    ref.child("Forum").child(idBaiDang).child("comments").push().setValue(comment_db);
                    edtCmt.setText("");
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();



        final DatabaseReference ref_userCmt = FirebaseDatabase.getInstance().getReference();

        ref_2 = FirebaseDatabase.getInstance().getReference().child("Forum").child(idBaiDang).child("comments");

        final Query query = ref_2.orderByChild("thoigianCmt");

        FirebaseRecyclerOptions<Comment_db> options = new FirebaseRecyclerOptions.Builder<Comment_db>().setQuery(query,Comment_db.class).build();


        FirebaseRecyclerAdapter<Comment_db,CommentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Comment_db, CommentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CommentViewHolder holder, int position, @NonNull final Comment_db model) {
                Log.d("danhle", model + "");
                holder.noidung.setText(model.getNoidungCmt());
                ref_userCmt.child("User").child(model.getIduserCmt()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.ten.setText(dataSnapshot.child("userName").getValue().toString());
                        Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(holder.avt);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_single_comment_row,parent,false);
                CommentViewHolder commentViewHolder = new CommentViewHolder(view);
                return commentViewHolder;
            }
        } ;

        recycler_comment.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private static class CommentViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView avt;
        private TextView ten;
        private  TextView noidung;
        public CommentViewHolder(View itemView) {
            super(itemView);
            avt = itemView.findViewById(R.id.comment_single_avt);
            ten = itemView.findViewById(R.id.comment_single_username);
            noidung = itemView.findViewById(R.id.comment_single_noidung);
        }
    }
}

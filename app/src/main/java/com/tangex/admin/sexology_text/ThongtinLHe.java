package com.tangex.admin.sexology_text;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CALL_PHONE;

public class ThongtinLHe extends AppCompatActivity {

    private DatabaseReference ref_lienhe;
    private RecyclerView List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_lhe);
        ref_lienhe = FirebaseDatabase.getInstance().getReference().child("ThongTinLienHe");

        List = (RecyclerView) findViewById(R.id.lienhe_list);
        List.setHasFixedSize(true);

        List.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<ThongtinLienhe> options = new FirebaseRecyclerOptions.Builder<ThongtinLienhe>().setQuery(ref_lienhe, ThongtinLienhe.class).build();


        FirebaseRecyclerAdapter<ThongtinLienhe, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ThongtinLienhe, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, final int position, @NonNull final ThongtinLienhe model) {
                holder.name.setText(model.getTen());
                Picasso.get().load(model.getImage()).into(holder.user_img);
                holder.thontin.setVisibility(View.VISIBLE);
                holder.thontin.setText(model.getSdt());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {


                       Intent intent = new Intent();
                       intent.setAction(Intent.ACTION_CALL);
                       intent.setData(Uri.parse("tel:" + model.getSdt()));
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            startActivity(intent);
                        } else {
                            requestPermissions(new String[]{CALL_PHONE}, 1);
                        }



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
        List.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private static class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView thontin;
        CircleImageView user_img;

        public UsersViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_single_name);
            user_img = itemView.findViewById(R.id.users_single_image);
            thontin = itemView.findViewById(R.id.hmm);
        }

    }
}

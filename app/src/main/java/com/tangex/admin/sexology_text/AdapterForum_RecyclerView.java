package com.tangex.admin.sexology_text;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class AdapterForum_RecyclerView extends RecyclerView.Adapter<AdapterForum_RecyclerView.Forum_ViewHolder> {

    private List<disscusion_db> listDiss;

    private DatabaseReference ref;

    private Date date;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public AdapterForum_RecyclerView(List<disscusion_db> listDiss) {
        this.listDiss = listDiss;
    }

    @NonNull
    @Override
    public Forum_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_single_row, parent, false);
        return new Forum_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Forum_ViewHolder holder, final int position) {

        date = new Date();
        date.setTime(listDiss.get(position).getThoigianDang());
        final String thoigian = dateFormat.format(date);

        holder.setNgaydang(thoigian);

        if (listDiss.get(position).getNam() == 1) {
            holder.setLoai("Nam");
        } else if (listDiss.get(position).getNu() == 1) {
            holder.setLoai("Nữ");
        } else if (listDiss.get(position).getLgbt() == 1) {
            holder.setLoai("LGBT");
        }



        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Forum").child(listDiss.get(position).getIdBaiDang()).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int count;
                    count = (int) dataSnapshot.getChildrenCount();
                    holder.setSo_binhluan(Integer.toString(count));
                }else holder.setSo_binhluan("0");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.setTieuDe(listDiss.get(position).getTieude());
        // ------------------ INTENT TO COMMENTS----------------------

        holder.ngaydang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent to_comments = new Intent(holder.ngaydang.getContext(), Comments.class);
                to_comments.putExtra("thoigianDang", listDiss.get(position).getThoigianDang());
                to_comments.putExtra("idBaiDang",listDiss.get(position).getIdBaiDang());
                holder.ngaydang.getContext().startActivity(to_comments);
            }
        });


        holder.loai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent to_comments = new Intent(holder.ngaydang.getContext(), Comments.class);
                to_comments.putExtra("thoigianDang", listDiss.get(position).getThoigianDang());
                to_comments.putExtra("idBaiDang",listDiss.get(position).getIdBaiDang());
                holder.ngaydang.getContext().startActivity(to_comments);
            }
        });

        holder.tieude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent to_comments = new Intent(holder.ngaydang.getContext(), Comments.class);
                to_comments.putExtra("thoigianDang", listDiss.get(position).getThoigianDang());
                to_comments.putExtra("idBaiDang",listDiss.get(position).getIdBaiDang());
                holder.ngaydang.getContext().startActivity(to_comments);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDiss.size();
    }

    public class Forum_ViewHolder extends RecyclerView.ViewHolder {
        private TextView tieude;
        private TextView loai;
        private TextView so_binhluan;
        private TextView ngaydang;

        public Forum_ViewHolder(View itemView) {
            super(itemView);

        }

        private void setTieuDe(String tieuDe) {
            tieude = itemView.findViewById(R.id.forum_tieude);
            tieude.setText(tieuDe);
        }

        private void setLoai(String Loai) {
            loai = itemView.findViewById(R.id.forum_type);
            loai.setText(Loai);
        }

        private void setSo_binhluan(String soBinhluan) {
            so_binhluan = itemView.findViewById(R.id.bl);
            so_binhluan.setText(soBinhluan);
        }

        private void setNgaydang(String ngayDang) {
            ngaydang = itemView.findViewById(R.id.forum_date);
            ngaydang.setText(ngayDang);
        }
    }
}

package com.tangex.admin.sexology_text;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterBlog_home_newfeed extends RecyclerView.Adapter<AdapterBlog_home_newfeed.ViewHolder> {
    private List<get_inf> blog_list;
    private Context context;

    public AdapterBlog_home_newfeed(List<get_inf> blog_list){
        this.blog_list = blog_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_newsfeed_home,parent,false);
        context= parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {



        holder.nf_news_name.setText(blog_list.get(position).getNews_name());
        Picasso.get().load(blog_list.get(position).getIma()).into(holder.ima);
        holder.des.setText(blog_list.get(position).getMota());

        if (blog_list.get(position).getType().toString().equals("3")){
            holder.imaType.setBackgroundResource(R.drawable.type2);
        } else if (blog_list.get(position).getType().toString().equals("2")){
            holder.imaType.setBackgroundResource(R.drawable.type3);
        } else if (blog_list.get(position).getType().toString().equals("1")){
            holder.imaType.setBackgroundResource(R.drawable.type1);
        }

        holder.ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.ima.getContext(), Page_infor.class);
                intent.putExtra("thu tu",blog_list.get(position).getFile());
                holder.ima.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ima;
        private ImageView imaType;
        private TextView des,nf_news_name;
        public ViewHolder(View itemView) {
            super(itemView);
            imaType = itemView.findViewById(R.id.newsfeed_type);
            ima = itemView.findViewById(R.id.newsfeed_ima);
            nf_news_name = itemView.findViewById(R.id.newsfeed_newsname);
            des = itemView.findViewById(R.id.newsfeed_des);
        }
    }
}

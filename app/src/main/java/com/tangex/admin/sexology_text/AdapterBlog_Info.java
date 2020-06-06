package com.tangex.admin.sexology_text;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterBlog_Info extends RecyclerView.Adapter<AdapterBlog_Info.ViewHolder> {

    private List<get_inf> post_List;

    public AdapterBlog_Info(List<get_inf> post_List) {
        this.post_List = post_List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.infor_single_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final int size;
        size = post_List.get(position).getNews_name().length();
        if (size >= 26){
            holder.news_name.setText(post_List.get(position).getNews_name().substring(0,26)+"...");
        }else {
            holder.news_name.setText(post_List.get(position).getNews_name());}
        if (post_List.get(position).getMota().length() >= 138) {
            holder.mo_ta.setText(post_List.get(position).getMota().substring(0, 138) + "...");
        } else {
            holder.mo_ta.setText(post_List.get(position).getMota());
        }
        Picasso.get().load(post_List.get(position).getIma()).into(holder.ima_news);

        holder.ima_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.ima_news.getContext(),Page_infor.class);
                intent.putExtra("thu tu",post_List.get(position).getFile());
                holder.ima_news.getContext().startActivity(intent);
            }
        });

        holder.mo_ta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.mo_ta.getContext(),Page_infor.class);
                intent.putExtra("thu tu",post_List.get(position).getFile());
                holder.mo_ta.getContext().startActivity(intent);
            }
        });
        holder.news_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.news_name .getContext(),Page_infor.class);
                intent.putExtra("thu tu",post_List.get(position).getFile());
                holder.news_name.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return post_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView news_name, mo_ta;
        ImageView ima_news;
        public ViewHolder(View itemView) {
            super(itemView);

            news_name = itemView.findViewById(R.id.inf_single_newsname);
            mo_ta = itemView.findViewById(R.id.inf_single_desc);
            ima_news = itemView.findViewById(R.id.inf_single_ImageView);
        }
    }
}



package com.tangex.admin.sexology_text;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class Search_Act extends AppCompatActivity {
    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_act);

        Client client = new Client("6Q0HE2P7A5","93e9b24f3b1a23efa2213feff91bf32");
        Index index = client.getIndex("getIndex");

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Provide");


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);


        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }

    private void firebaseUserSearch(String searchText) {



        Query firebaseSearchQuery = mUserDatabase.orderByChild("news_name").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<get_inf>().setQuery(firebaseSearchQuery,get_inf.class).build();

       FirebaseRecyclerAdapter<get_inf,InfViewHolder_Search> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<get_inf, InfViewHolder_Search>(options) {
           @Override
           protected void onBindViewHolder(@NonNull InfViewHolder_Search holder, final int position, @NonNull get_inf model) {

               holder.news_name.setText(model.getNews_name());
               holder.mo_ta.setText(model.getMota());
               Picasso.get().load(model.getIma()).into(holder.ima_news);

               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       String thu_tu = getRef(position).getKey();
                       Intent to_webview;
                       to_webview = new Intent(Search_Act.this,Page_infor.class);
                       to_webview.putExtra("thu tu",thu_tu);
                       startActivity(to_webview);

                   }
               });
           }

           @NonNull
           @Override
           public InfViewHolder_Search onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.infor_single_layout,parent,false);
               InfViewHolder_Search viewHolder_search = new InfViewHolder_Search(view);
               return viewHolder_search;
           }
       };
       mResultList.setAdapter(firebaseRecyclerAdapter);
       firebaseRecyclerAdapter.startListening();
    }


// View Holder Class

    public static class InfViewHolder_Search extends RecyclerView.ViewHolder {

        View mView;

        TextView news_name, mo_ta;
        ImageView ima_news;

        public InfViewHolder_Search(View itemView) {
            super(itemView);

            news_name = itemView.findViewById(R.id.inf_single_newsname);
            mo_ta = itemView.findViewById(R.id.inf_single_desc);
            ima_news = itemView.findViewById(R.id.inf_single_ImageView);

        }

//        public void setDetails(Context ctx, String userName, String userStatus, String userImage) {
//
//            ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);
//        }
    }
}

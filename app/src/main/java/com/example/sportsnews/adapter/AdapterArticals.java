
package com.example.sportsnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsnews.ui.activity.DetailsActivity;
import com.example.sportsnews.R;
import com.example.sportsnews.models.Article;


import java.util.ArrayList;

public class AdapterArticals extends RecyclerView.Adapter<AdapterArticals.ViewHolderMenu> {
    Context mContext;
    private ArrayList<Article> arrayList;
    String user_id;
    DataPassListener mCallback;

    public interface DataPassListener {
        public void passData(String data);
    }

    public AdapterArticals(Context mContext, ArrayList<Article> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;


    }


    @NonNull
    @Override
    public ViewHolderMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderMenu(LayoutInflater.from(mContext).inflate(R.layout.cell_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMenu holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        for (int i = 0; i < arrayList.size(); i++) {
            requestOptions.placeholder(R.drawable.color);
            requestOptions.error(R.drawable.color);

            Glide.with(mContext)
                    .setDefaultRequestOptions(requestOptions)
                    .load(arrayList.get(position).getUrlToImage()).placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
            if (arrayList.get(position).getAuthor() == null || arrayList.get(position).getAuthor().isEmpty()) {
                holder.tv_artical_auther.setText("");
            } else {
                holder.tv_artical_auther.setText(arrayList.get(position).getAuthor());

            }
            if (arrayList.get(position).getTitle() == null || arrayList.get(position).getTitle().isEmpty()) {
                holder.tv_artical_title.setText("");
            } else {
                holder.tv_artical_title.setText(arrayList.get(position).getTitle());

            }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("Object", arrayList.get(position));
                    mContext.startActivity(intent);


                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolderMenu extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_artical_auther;
        TextView tv_artical_title;
        CardView cardView;

        public ViewHolderMenu(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_profile);
            tv_artical_auther = itemView.findViewById(R.id.tv_auther);
            tv_artical_title = itemView.findViewById(R.id.tv_title);
            cardView = itemView.findViewById(R.id.card);
        }
    }


}

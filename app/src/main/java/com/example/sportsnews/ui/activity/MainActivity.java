package com.example.sportsnews.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.example.sportsnews.R;
import com.example.sportsnews.adapter.AdapterArticals;
import com.example.sportsnews.models.Article;
import com.example.sportsnews.models.Data;
import com.example.sportsnews.service.ServiceOfSupportArticals;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ServiceOfSupportArticals serviceOfSupportArticals;
    AdapterArticals adapterArticals;
    Context mContext;

    private ArrayList<Article> menuModels1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rec_articals);
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.HORIZONTAL);
        Drawable verticalDivider = ContextCompat.getDrawable(getApplicationContext(), R.drawable.recycler_view_divider);
        verticalDecoration.setDrawable(verticalDivider);
        recyclerView.addItemDecoration(verticalDecoration);
        this.menuModels1 = new ArrayList<Article>();
        serviceOfSupportArticals = new ServiceOfSupportArticals();
        serviceOfSupportArticals.SupportArticals(mContext, new ServiceOfSupportArticals.FininshLogin() {
            @Override
            public void finish(Data registerMember) {

                Log.d("WWWWWWWWWWWWWWWWWW", String.valueOf(registerMember.getArticles().size()));
                for (int i = 0; i < registerMember.getArticles().size(); i++) {
                    menuModels1.add(registerMember.getArticles().get(i));
                }


                adapterArticals = new AdapterArticals(getApplicationContext(), menuModels1);

                adapterArticals.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

                recyclerView.setAdapter(adapterArticals);

            }


        }, "gb", "sports", "f444b99088e04071a3c206cdb814dc2f");


    }
}
package com.example.sportsnews.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsnews.R;
import com.example.sportsnews.models.Article;

public class DetailsActivity extends AppCompatActivity {
    TextView articaltitle,articalauther,publisheddate,articaldesc,detailsnews;
    ImageView imgdetails;
    Article article;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cell_details);
        articaltitle=findViewById(R.id.tv_title);
        articalauther=findViewById(R.id.tv_auther);
        publisheddate=findViewById(R.id.tv_published_date);
        articaldesc=findViewById(R.id.tv_artical_desc);
        detailsnews=findViewById(R.id.tv_details);
        imgdetails=findViewById(R.id.img_detail_news);
        imageButton=findViewById(R.id.img_butt);

        try{

            article=(Article) getIntent().getSerializableExtra("Object");

        } catch(Exception ex){
            article = null; //Or some error status //
        }

        Log.d("WWWWWWWWWWWWWWWWWWWW",article.getUrlToImage().toString());

        if (article.getAuthor()==null || article.getAuthor().isEmpty()){
          articalauther.setText("");
        }else{
           articalauther.setText(article.getAuthor());

        }
        //check if artical title  equals null

        if (article.getTitle()==null || article.getTitle().isEmpty()){
           articaltitle.setText("");
        }else{
            articaltitle.setText(article.getTitle());

        }
        //check if describtion  equals null

        if (article.getDescription()==null || article.getDescription().isEmpty()){
            articaldesc.setText("");
        }else{
            articaldesc.setText(article.getDescription());

        }
        //check if puplish date equals null
        if (article.getPublishedAt()==null || article.getPublishedAt().isEmpty()){
            publisheddate.setText("");
        }else{
            publisheddate.setText(article.getPublishedAt());

        }
        //check if content  equals null
        if (article.getContent()==null || article.getContent().isEmpty()){
            detailsnews.setText("");
        }else{
            detailsnews.setText(article.getContent());

        }
        //check if image date equals null

        if (article.getUrlToImage()!=null || ! article.getUrlToImage().isEmpty()){
            RequestOptions requestOptions = new RequestOptions();

            requestOptions.placeholder(R.drawable.color);
            requestOptions.error(R.drawable.color);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(article.getUrlToImage()).placeholder(R.drawable.ic_launcher_background)
                    .into(imgdetails);

        }

        Log.d("data",article.getAuthor());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
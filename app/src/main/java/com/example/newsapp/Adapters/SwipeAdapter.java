package com.example.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.BuildConfig;
import com.example.newsapp.Models.Articles;
import com.example.newsapp.Models.Articles;
import com.example.newsapp.R;
import com.example.newsapp.Utils.AppConstants;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class SwipeAdapter extends BaseAdapter {

    private static final String TAG = "SwipeAdapter";
    private List<Articles> articleList;
    private Context mContext;


    public SwipeAdapter(List<Articles> articleList, Context mContext) {
        this.articleList = articleList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int i) {
        return articleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.card_item,viewGroup,false);

        }
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress);
        TextView title = (TextView)view.findViewById(R.id.newsTitle);
        TextView description = (TextView)view.findViewById(R.id.description);
        TextView url = (TextView)view.findViewById(R.id.url);
        TextView author = (TextView)view.findViewById(R.id.website_title);
        ImageView image = view.findViewById(R.id.news_image);
        ImageView share = view.findViewById(R.id.share);
        RelativeTimeTextView timeTextView = (RelativeTimeTextView)view.findViewById(R.id.newsTime);

        if (articleList.get(i) != null && articleList.get(i).getUrlToImage().length() > 0){
            Picasso.get().load(articleList.get(i).getUrlToImage())
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }

        if (articleList.get(i).getTitle().length() > 65){
            title.setText(articleList.get(i).getTitle().substring(0,65)+"...");
        }else {
            title.setText(articleList.get(i).getTitle());
        }

        if (articleList.get(i).getPublishedAt() != null){
            Date date = null;
            try {
                date = AppConstants.parse(articleList.get(i).getPublishedAt());
                timeTextView.setReferenceTime(date.getTime());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        author.setText(articleList.get(i).getAuthor());
        description.setText(articleList.get(i).getDescription());
        url.setText(articleList.get(i).getUrl());
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "share with your friends", Toast.LENGTH_SHORT).show();

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nWatch this now\n\n";
                    shareMessage = shareMessage + url.getText().toString();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    mContext.startActivity(Intent.createChooser(shareIntent, "choose one"));
                }catch (Exception e){
                    Log.e(TAG,"onClick: Erorr  "+e.getMessage() );
                }
            }
        });
        return view;
    }
}

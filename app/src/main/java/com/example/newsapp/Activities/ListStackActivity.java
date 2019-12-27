package com.example.newsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.newsapp.Adapters.SwipeAdapter;
import com.example.newsapp.Models.Articles;
import com.example.newsapp.Models.News;
import com.example.newsapp.R;
import com.example.newsapp.Retrofit.ApiClient;
import com.example.newsapp.Retrofit.ApiInterface;
import com.example.newsapp.Utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.fls.swipestack.SwipeStack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStackActivity extends AppCompatActivity implements SwipeStack.SwipeStackListener {
    private static final String TAG = "ListStackActivity";
    @BindView(R.id.swipeStack) SwipeStack swipeStack;
    @BindView(R.id.relativeNotFound) RelativeLayout relativeLayout;

    ApiInterface apiInterface;
    List<Articles>articleList;

    SwipeAdapter swipeAdapter;

    String sourceId,hotUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stack);

        ButterKnife.bind(this);
        init();

        getDataFromPrev();
    }

    private void getDataFromPrev() {
        sourceId = getIntent().getStringExtra("sourceid");
        if (!sourceId.isEmpty()){
            loadNews(sourceId);
            Log.e(TAG, "getDataFromPrev: source"+sourceId );
        }
    }

    private void loadNews(String sourceId) {


        apiInterface.getNewestArticles(ApiClient.getApiUrl(sourceId,AppConstants.API_KEY))
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        if (response.isSuccessful()){
                        articleList.clear();
                            Log.e(TAG, "onResponse: arr"+response.body().getTotalResults() );
                        articleList.addAll(response.body().getArticles());
                        Log.e(TAG, "onResponse: error1"+response.errorBody()+ "  "+ response.body() );
                        swipeAdapter.notifyDataSetChanged();
                        }
                        else {
                            Log.e(TAG, "onResponse: "+"errorrr"+response.errorBody() );
                            //.makeText(ListStackActivity.this, "error"+response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {
                        Toast.makeText(ListStackActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure:: "+t.getMessage() );
                    }
                });

    }

    private void init() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        articleList= new ArrayList<>();

        swipeAdapter = new SwipeAdapter(articleList,this);
        swipeStack.setAdapter(swipeAdapter);
        swipeStack.setListener(this);
    }

    @Override
    public void onViewSwipedToLeft(int position) {

    }

    @Override
    public void onViewSwipedToRight(int position) {

    }

    @Override
    public void onStackEmpty() {
        swipeStack.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
    }
}

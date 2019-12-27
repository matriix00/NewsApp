package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.newsapp.Adapters.SourcesAdapter;
import com.example.newsapp.Models.Sources;
import com.example.newsapp.Models.Website;
import com.example.newsapp.Retrofit.ApiClient;
import com.example.newsapp.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.recycleViewMainActivity)
    RecyclerView recyclerView;

    ApiInterface apiInterface;
    List<Sources> sourcesList;
    SourcesAdapter adapter;

    public AlertDialog dialog ;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        
        init();

        loadWebsiteSources();
    }

    private void loadWebsiteSources() {

        dialog.show();
        Call<Website> call = apiInterface.getSources();
        Log.e(TAG, "loadWebsiteSources1: "+apiInterface.getSources() );
        Log.e(TAG, "loadWebsiteSources2: "+call );
        call.enqueue(new Callback<Website>() {
            @Override
            public void onResponse(Call<Website> call, Response<Website> response) {
                dialog.dismiss();
                Website website = response.body();

                if (website!=null && response.body().getSources().size()>0){
                    sourcesList.clear();
                    sourcesList.addAll(website.getSources());

                }else {
                    Toast.makeText(MainActivity.this, "No Sources found !", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<Website>call, Throwable t) {
                Toast.makeText(MainActivity.this, "Errorrr : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure:sss"+t.getMessage() );
                dialog.dismiss();
            }
        });
    }

    private void init() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dialog = new SpotsDialog.Builder().setContext(this).build();
        dialog.show();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sourcesList = new ArrayList<>();

        adapter = new SourcesAdapter(this,sourcesList);
        recyclerView.setAdapter(adapter);
    }
}

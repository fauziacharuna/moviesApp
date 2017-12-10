package com.android.fauziachmadharunadev.moviesapp.ui.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.fauziachmadharunadev.moviesapp.R;
import com.android.fauziachmadharunadev.moviesapp.ui.adapter.MoviesRVAdapter;
import com.android.fauziachmadharunadev.moviesapp.ui.model.Movies;
import com.android.fauziachmadharunadev.moviesapp.ui.model.MoviesResult;
import com.android.fauziachmadharunadev.moviesapp.ui.util.RestAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "7f0abe452a1fa589837918e54e91514e";
    private static final String LANGUAGE = "en-US";
    private static final String PAGE = "1";


    private List<MoviesResult> moviesResults = new ArrayList<>();
    private RestAdapter restAdapter;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private MoviesRVAdapter moviesRVAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        moviesRVAdapter=new MoviesRVAdapter(getApplicationContext(),moviesResults);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView=(RecyclerView)findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(moviesRVAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesResults.clear();
                fetchData();
                moviesRVAdapter.notifyDataSetChanged();
            }
        });

        restAdapter=new RestAdapter();
        fetchData();
    }
    private void fetchData(){
        restAdapter.getEndpoint().callPopular(API_KEY,LANGUAGE,PAGE).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()){
                    moviesResults.addAll(response.body().getResults());
                    moviesRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this,"Load data error!",Toast.LENGTH_SHORT).show();
                Log.e("MainActivity","LOAD ERROR : "+t.getMessage());
            }
        });
    }


}

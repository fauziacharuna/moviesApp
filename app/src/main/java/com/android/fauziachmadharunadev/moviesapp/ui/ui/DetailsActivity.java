package com.android.fauziachmadharunadev.moviesapp.ui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fauziachmadharunadev.moviesapp.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FauziAchmad on 5/23/17.
 */

public class DetailsActivity extends AppCompatActivity {
    private Bundle bundle;

    private String poster, title, dateFormatted = "", dateString, rating, backdrop, voted, overview,review;

    private ImageView ivBackdrop, ivPoster;
    private TextView tvTitle, tvDate, tvRating, tvVoted, tvOverview,tvReview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ivBackdrop = (ImageView) findViewById(R.id.iv_backdrop);
        ivPoster = (ImageView) findViewById(R.id.iv_poster);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDate = (TextView) findViewById(R.id.tv_release_date);
        tvRating = (TextView) findViewById(R.id.tv_rating_user);
        tvVoted = (TextView) findViewById(R.id.tv_voted_user);
        tvOverview = (TextView) findViewById(R.id.tv_synopsis);
        tvReview=(TextView)findViewById(R.id.tv_review);

        bundle = getIntent().getBundleExtra("details");

        poster = bundle.getString("poster", "");
        title = bundle.getString("title", "");
        dateString = bundle.getString("dateString", "");
        rating = bundle.getString("rating", "");
        backdrop = bundle.getString("backdrop", "");
        voted = bundle.getString("voted", "");
        overview = bundle.getString("overview", "");
        review=bundle.getString("review","");

        try {
            SimpleDateFormat fmtDefault = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmtDefault.parse(dateString);
            SimpleDateFormat fmtNew = new SimpleDateFormat("dd MMM yyyy");
            dateFormatted = fmtNew.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Glide.with(this).load(backdrop).into(ivBackdrop);
        Glide.with(this).load(poster).into(ivPoster);
        tvTitle.setText(title);
        tvDate.setText(dateFormatted);
        tvRating.setText(rating);
        tvVoted.setText(voted);
        tvOverview.setText(overview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

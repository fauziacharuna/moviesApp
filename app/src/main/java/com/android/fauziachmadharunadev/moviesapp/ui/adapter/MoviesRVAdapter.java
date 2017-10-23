package com.android.fauziachmadharunadev.moviesapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fauziachmadharunadev.moviesapp.R;
import com.android.fauziachmadharunadev.moviesapp.ui.model.Movies;
import com.android.fauziachmadharunadev.moviesapp.ui.model.MoviesResult;
import com.android.fauziachmadharunadev.moviesapp.ui.ui.DetailsActivity;
import com.android.fauziachmadharunadev.moviesapp.ui.ui.MainActivity;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by FauziAchmad on 5/23/17.
 */

public class MoviesRVAdapter extends RecyclerView.Adapter<MoviesRVAdapter.MoviesViewHolder> {
    private static final String POSTER_URL="https://image.tmdb.org/t/p/w92/";
    private static final String BACKDROP_URL="https://image.tmdb.org/t/p/w500/";

    List<MoviesResult> moviesResultsList;
    Context context;

    public MoviesRVAdapter( Context context,List<MoviesResult> moviesResultsList) {
        this.moviesResultsList = moviesResultsList;
        this.context = context;
    }
    public class MoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPoster;
        TextView tvTitle, tvDate, tvRating,tvReview;
        CardView cvMovie;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            cvMovie = (CardView)itemView.findViewById(R.id.cardView);
            ivPoster = (ImageView)itemView.findViewById(R.id.iv_poster);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            tvDate = (TextView)itemView.findViewById(R.id.tv_date);
            tvRating = (TextView)itemView.findViewById(R.id.tv_rating);
            tvReview=(TextView)itemView.findViewById(R.id.tv_review);
        }
    }

    @Override
    public MoviesRVAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movies,parent,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesRVAdapter.MoviesViewHolder holder, int position) {
        MoviesResult moviesResult=moviesResultsList.get(position);
        String poster,review, title,dateFormatted="",dateString,rating;
        poster=POSTER_URL + moviesResult.getPosterPath();
        title=moviesResult.getTitle();
        dateString=moviesResult.getReleaseDate();
        rating=String.valueOf(moviesResult.getVoteAverage());
        review=moviesResult.getReview();
        try{
            SimpleDateFormat fmtDefault=new SimpleDateFormat("yyyy-MM-dd");
            Date date=fmtDefault.parse(dateString);
            SimpleDateFormat fmtNew=new SimpleDateFormat("MMM yyyy");
            dateFormatted=fmtNew.format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        holder.tvTitle.setText(title);
        holder.tvDate.setText(dateFormatted);
        holder.tvRating.setText(rating);
        Glide.with(context).load(poster).into(holder.ivPoster);

        String backdrop,voted,overview;

        backdrop=BACKDROP_URL +moviesResult.getBackdrop_path();
        voted=String.valueOf(moviesResult.getVoteCount());
        overview=moviesResult.getOverview();

        final Bundle bundle = new Bundle();
        bundle.putString("backdrop",backdrop);
        bundle.putString("voted",voted);
        bundle.putString("overview",overview);
        bundle.putString("poster",poster);
        bundle.putString("title",title);
        bundle.putString("dateString",dateString);
        bundle.putString("rating",rating);
        bundle.putString("review",review);

        holder.cvMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("details",bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesResultsList==null ? 0 : moviesResultsList.size();
    }
}

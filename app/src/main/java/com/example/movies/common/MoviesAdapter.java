package com.example.movies.common;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.R;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<MoviePreview> movies = new ArrayList<>();

    private OnMovieItemClickListener onMovieItemClickListener;
    private OnReachEndListener onReachEndListener;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false
        );

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MoviePreview movie = this.movies.get(position);

        ImageView imageViewPoster = holder.getImageViewPoster();
        TextView textViewRating = holder.getTextViewRating();

        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);

        double rating = movie.getRating().getKp();
        int backgroundId = this.getBackgroundId(rating);

        Drawable background = ContextCompat.getDrawable(
                holder.itemView.getContext(),
                backgroundId
        );

        textViewRating.setBackground(background);
        textViewRating.setText(String.format("%.1f", rating));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieItemClickListener != null) {
                    onMovieItemClickListener.onClick(movie);
                }
            }
        });

        if (this.onReachEndListener != null && position == this.movies.size() - 10) {
            this.onReachEndListener.onReachEnd();
        }
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

    public void setMovies(List<MoviePreview> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnMovieItemClickListener(OnMovieItemClickListener onMovieItemClickListener) {
        this.onMovieItemClickListener = onMovieItemClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    private int getBackgroundId(double rating) {
        int id;

        if (rating >= 7) {
            id = R.drawable.circle_green;
        } else if (rating >= 5) {
            id = R.drawable.circle_orange;
        } else {
            id = R.drawable.circle_red;
        }

        return id;
    }

    public interface OnMovieItemClickListener {

        void onClick(MoviePreview movie);
    }

    public interface OnReachEndListener {

        void onReachEnd();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewPoster;
        private TextView textViewRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            this.initViews();
        }

        public ImageView getImageViewPoster() {
            return this.imageViewPoster;
        }

        public TextView getTextViewRating() {
            return this.textViewRating;
        }

        private void initViews() {
            this.imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            this.textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }
}

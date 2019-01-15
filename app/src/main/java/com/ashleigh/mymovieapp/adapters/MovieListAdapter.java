package com.ashleigh.mymovieapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashleigh.mymovieapp.R;
import com.ashleigh.mymovieapp.models.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private static final String TAG = MovieListAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<Movie> mMovies;
    private MovieListCallback callback;
    private Movie mMovieItem;

    public MovieListAdapter(Context context, ArrayList<Movie> movieList, MovieListCallback listCallback) {
        this.mContext = context;
        this.mMovies = movieList;
        this.callback = listCallback;

    }

    public interface MovieListCallback {
        void OpenDetail(Movie movie, int position);
    }

    @NonNull
    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item,
                parent, false);

        int gridNumber = 2;
        view.getLayoutParams().height = (int) (parent.getWidth() / gridNumber * Movie.POSTER_ASPECT_RATIO);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieListAdapter.ViewHolder holder, int position) {
        final Movie currentMovie = mMovies.get(position);

        holder.mTitle.setText(currentMovie.getmTitle());

        if (currentMovie.getPosterUrl() == null) {
            holder.mTitle.setVisibility(View.VISIBLE);
        } else {
            Glide.with(mContext)
                    .load(currentMovie.getPosterUrl())
                    .apply(new RequestOptions().error(R.drawable.ic_launcher_background))
                    .into(holder.mImageView);
        }

        if (holder.mView != null) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.OpenDetail(currentMovie, holder.getAdapterPosition());
                    //Log.i(TAG, "view item: " + currentMovie.getmId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        @BindView(R.id.title_itemview)
        TextView mTitle;
        @BindView(R.id.image_itemview)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }

    public void add(List<Movie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getmMovies() {
        return mMovies;
    }

    public Movie getItem(int position) {

        return mMovies.get(position);
    }
}

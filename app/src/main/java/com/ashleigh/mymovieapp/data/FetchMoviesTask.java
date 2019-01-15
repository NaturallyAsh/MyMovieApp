package com.ashleigh.mymovieapp.data;

import android.os.AsyncTask;
import android.util.Log;

import com.ashleigh.mymovieapp.BuildConfig;
import com.ashleigh.mymovieapp.models.Movie;
import com.ashleigh.mymovieapp.models.Movies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.StringDef;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

    private static final String TAG = FetchMoviesTask.class.getSimpleName();

    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";

    private final NotifyMovieFetchComplete mComplete;


    @StringDef({POPULAR, TOP_RATED})
    public @interface SORT_BY {}

    @SORT_BY
    String mSortBy;

    public interface Callback {
        void OnMoviesFetched(Complete complete);
    }

    public static class NotifyMovieFetchComplete implements Complete {

        private FetchMoviesTask.Callback mCallback;
        private List<Movie> mMovies;

        public NotifyMovieFetchComplete(FetchMoviesTask.Callback callback) {
            this.mCallback = callback;
        }

        @Override
        public void execute() {
            mCallback.OnMoviesFetched(this);
        }

        public List<Movie> getMovies() {
            return mMovies;
        }
    }

    public FetchMoviesTask(@SORT_BY String sortBy, NotifyMovieFetchComplete complete) {
        this.mSortBy = sortBy;
        this.mComplete = complete;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            mComplete.mMovies = movies;
        } else {
            mComplete.mMovies = new ArrayList<>();
        }
        mComplete.execute();
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieListData data = retrofit.create(MovieListData.class);
        Call<Movies> movieCall = data.fetchMovies(mSortBy, BuildConfig.Movie_API_key);
        try {
            Response<Movies> movieResponse = movieCall.execute();
            Movies movies = movieResponse.body();
            return movies.getAllMovies();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

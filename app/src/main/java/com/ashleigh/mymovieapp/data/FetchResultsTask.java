package com.ashleigh.mymovieapp.data;

import android.os.AsyncTask;
import android.util.Log;

import com.ashleigh.mymovieapp.BuildConfig;
import com.ashleigh.mymovieapp.models.Movie;
import com.ashleigh.mymovieapp.models.Movies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchResultsTask extends AsyncTask<Void, Void, List<Movie>> {

    private static final String TAG = FetchResultsTask.class.getSimpleName();

    private String mQuery;
    private OnSearchResultListener callback;

    public interface OnSearchResultListener {
        void Results(List<Movie> movies);
    }

    public FetchResultsTask(OnSearchResultListener listener, String query) {
        mQuery = query;
        callback = listener;
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieListData data = retrofit.create(MovieListData.class);
        Call<Movies> movieCall = data.fetchSimilarMovies(BuildConfig.Movie_API_key, mQuery);
        try {
            Response<Movies> response = movieCall.execute();
            Movies movies = response.body();
            return movies.getAllMovies();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            callback.Results(movies);
        } else {
            callback.Results(new ArrayList<Movie>());
        }
    }
}

package com.ashleigh.mymovieapp.data;

import android.os.AsyncTask;
import android.util.Log;

import com.ashleigh.mymovieapp.BuildConfig;
import com.ashleigh.mymovieapp.models.Detail;
import com.ashleigh.mymovieapp.models.Details;
import com.ashleigh.mymovieapp.models.Movie;
import com.ashleigh.mymovieapp.models.Movies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchDetailsTask extends AsyncTask<Long, Void, List<Detail>> {

    private static final String TAG = FetchDetailsTask.class.getSimpleName();

    private String mAppend;
    private OnDetailsFetchedListener listener;

    public interface OnDetailsFetchedListener {
        void DetailsFetched(List<Detail> movies);
    }

    public FetchDetailsTask(String append, OnDetailsFetchedListener listener) {
        this.mAppend = append;
        this.listener = listener;
    }

    @Override
    protected List<Detail> doInBackground(Long... params) {

        if (params.length == 0) {
            return null;
        }
        long mId = params[0];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieListData data = retrofit.create(MovieListData.class);
        Call<Details> moviesCall = data.fetchMovieDetails(mId, BuildConfig.Movie_API_key);
        try {
            Response<Details> response = moviesCall.execute();
            Details details = response.body();
            return details.getAllDetails();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(List<Detail> details) {
        if (details != null) {
            listener.DetailsFetched(details);
        } else {
            listener.DetailsFetched(new ArrayList<Detail>());
        }
    }
}

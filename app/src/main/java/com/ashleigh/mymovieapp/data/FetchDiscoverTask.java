package com.ashleigh.mymovieapp.data;

import android.os.AsyncTask;

import com.ashleigh.mymovieapp.BuildConfig;
import com.ashleigh.mymovieapp.models.Discover;
import com.ashleigh.mymovieapp.models.Discovers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchDiscoverTask extends AsyncTask<Void, Void, List<Discover>> {

    private static final String TAG = FetchDiscoverTask.class.getSimpleName();

    private OnDiscoverListener listener;

    public interface OnDiscoverListener {
        void DiscoverFetched(List<Discover> discovers);
    }

    public FetchDiscoverTask(OnDiscoverListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Discover> doInBackground(Void... voids) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieListData data = retrofit.create(MovieListData.class);
        Call<Discovers> discoversCall = data.fetchDiscoverMovies(BuildConfig.Movie_API_key, "US",
                "popularity.desc", false, false, 1, 2019, 3);

        try {
            Response<Discovers> discoversResponse = discoversCall.execute();
            Discovers discovers = discoversResponse.body();
            return discovers.getAllDiscovers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Discover> discovers) {
        if (discovers != null) {
            listener.DiscoverFetched(discovers);
        } else {
            listener.DiscoverFetched(new ArrayList<Discover>());
        }
    }
}

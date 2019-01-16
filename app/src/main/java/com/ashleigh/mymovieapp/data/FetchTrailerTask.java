package com.ashleigh.mymovieapp.data;

import android.os.AsyncTask;

import com.ashleigh.mymovieapp.BuildConfig;
import com.ashleigh.mymovieapp.models.Trailer;
import com.ashleigh.mymovieapp.models.Trailers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchTrailerTask extends AsyncTask<Long, Void, List<Trailer>> {

    private static final String TAG = FetchTrailerTask.class.getSimpleName();

    private OnTrailersFetchedListener listener;

    public FetchTrailerTask(OnTrailersFetchedListener listener) {
        this.listener = listener;
    }

    public interface OnTrailersFetchedListener {
        void FetchedTrailers(List<Trailer> trailers);
    }

    @Override
    protected List<Trailer> doInBackground(Long... params) {
        if (params.length == 0) {
            return null;
        }
        long mId = params[0];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieListData data = retrofit.create(MovieListData.class);
        Call<Trailers> trailersCall = data.fetchTrailers(mId, BuildConfig.Movie_API_key);
        try {
            Response<Trailers> response = trailersCall.execute();
            Trailers trailers = response.body();
            return trailers.getAllTrailers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Trailer> trailers) {
        if (trailers != null) {
            listener.FetchedTrailers(trailers);
        } else {
            listener.FetchedTrailers(new ArrayList<Trailer>());
        }
    }
}

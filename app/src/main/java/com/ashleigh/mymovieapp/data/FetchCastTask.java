package com.ashleigh.mymovieapp.data;

import android.os.AsyncTask;

import com.ashleigh.mymovieapp.BuildConfig;
import com.ashleigh.mymovieapp.adapters.CastAdapter;
import com.ashleigh.mymovieapp.models.Cast;
import com.ashleigh.mymovieapp.models.Casts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchCastTask extends AsyncTask<Long, Void, List<Cast>> {

    private static final String TAG = FetchCastTask.class.getSimpleName();

    private OnCastCallListener listener;

    public interface OnCastCallListener {
        void OnCastCalled(List<Cast> casts);
    }

    public FetchCastTask(OnCastCallListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Cast> doInBackground(Long... params) {

        if (params.length == 0) {
            return null;
        }
        long mId = params[0];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieListData data = retrofit.create(MovieListData.class);
        Call<Casts> castsCall = data.fetchCasts(mId, BuildConfig.Movie_API_key);

        try {
            Response<Casts> response = castsCall.execute();
            Casts casts = response.body();
            return casts.getAllCast();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Cast> casts) {
        if (casts != null) {
            listener.OnCastCalled(casts);
        } else {
            listener.OnCastCalled(new ArrayList<Cast>());
        }
    }
}

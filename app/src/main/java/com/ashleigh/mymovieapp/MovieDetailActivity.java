package com.ashleigh.mymovieapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashleigh.mymovieapp.adapters.CastAdapter;
import com.ashleigh.mymovieapp.adapters.TrailerAdapter;
import com.ashleigh.mymovieapp.data.FetchCastTask;
import com.ashleigh.mymovieapp.data.FetchDetailsTask;
import com.ashleigh.mymovieapp.data.FetchTrailerTask;
import com.ashleigh.mymovieapp.models.Cast;
import com.ashleigh.mymovieapp.models.Detail;
import com.ashleigh.mymovieapp.models.Movie;
import com.ashleigh.mymovieapp.models.SubDetails;
import com.ashleigh.mymovieapp.models.Trailer;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity
        implements FetchTrailerTask.OnTrailersFetchedListener, TrailerAdapter.OnTrailerClickedListener,
        FetchDetailsTask.OnDetailsFetchedListener, CastAdapter.OnCastClickedListener,
        FetchCastTask.OnCastCallListener {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private Movie movie;
    private Context context;
    private TrailerAdapter trailerAdapter;
    private CastAdapter castAdapter;
    private ArrayList<Cast> castArrayList = new ArrayList<>();
    private ArrayList<Trailer> trailerList = new ArrayList<>();
    @BindView(R.id.detail_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.detail_CT)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_imageView)
    ImageView movieBackdrop;
    @BindView(R.id.detail_poster_IV)
    ImageView posterIV;
    @BindView(R.id.detail_reviews_TV)
    TextView reviewsTV;
    @BindView(R.id.detail_overviewTv)
    TextView overviewTV;
    @BindView(R.id.trailers_recyclerview)
    RecyclerView trailerRecyclerView;
    @BindView(R.id.mpaa)
    TextView mMPAA_TV;
    @BindView(R.id.genres)
    TextView mGenres;
    @BindView(R.id.cast_recyclerview)
    RecyclerView castRecyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Intent intent = getIntent();
        if (intent != null) {
            movie = intent.getExtras().getParcelable(MainActivity.ARG_MOVIE_ITEM);
            assert movie != null;
            //Log.i(TAG, "movie detail: " + movie.getmId());
        }
        collapsingToolbar.setTitle(movie.getmTitle());

        loadBackdrop();

        initViews();

        LinearLayoutManager linearLayout =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        LinearLayoutManager castLinearLayout =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        trailerRecyclerView.setLayoutManager(linearLayout);
        trailerAdapter = new TrailerAdapter(MovieDetailActivity.this, trailerList, this);
        trailerRecyclerView.setAdapter(trailerAdapter);

        castRecyclerview.setLayoutManager(castLinearLayout);
        castAdapter = new CastAdapter(MovieDetailActivity.this, castArrayList, this);
        castRecyclerview.setAdapter(castAdapter);

        fetchTrailers(movie);
        fetchDetails(movie);
        fetchCast(movie);
        setGenre();
    }

    private void loadBackdrop() {
        if (movie.getmBackdrop() != null) {
            Glide.with(getApplicationContext())
                    .load(movie.getBackdropUrl())
                    .into(movieBackdrop);
        }
    }

    private void initViews() {
        if (movie != null) {
            Glide.with(getApplicationContext())
                    .load(movie.getPosterUrl())
                    .into(posterIV);

            reviewsTV.setText(movie.getmPopularity());

            overviewTV.setText(movie.getmOverview());
        }
    }

    private void setGenre() {
        Resources r = getResources();
        int[] res = r.getIntArray(R.array.genres);
        int[] gen = movie.getmGenres();
        String[] genStr = r.getStringArray(R.array.genre_names);
        ArrayList<Integer> result = new ArrayList<>();
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < res.length; i++) {
            map.put(res[i], genStr[i]);
        }

        for(int i = 0; i < gen.length; i++) {
            int con = gen[i];
            if (res[i] == con) {
                result.add(gen[i]);
            }
        }

        String join = "";

        Set set = map.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            //System.out.print(me.getKey() + ": ");
            Integer key = (Integer) me.getKey();
            for (int j = 0; j < result.size(); j++) {
                if (result.get(j).equals(key)) {
                    join += (map.get(key) + " - ");
                }
            }
        }
        joinGenre(join);

    }

    private void joinGenre(String gStr) {

        String split = StringUtils.removeEnd(gStr, " - ");
        mGenres.setText(split);

    }

    private void fetchTrailers(Movie movie) {
        new FetchTrailerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, movie.getmId());
    }

    @Override
    public void FetchedTrailers(List<Trailer> trailers) {
        trailerAdapter.addTrailer(trailers);
    }

    @Override
    public void TrailerClicked(Trailer trailer, int position) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getmKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getTrailerUrl()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException e) {
            startActivity(webIntent);
        }
    }

    private void fetchDetails(Movie movie) {
        new FetchDetailsTask(movie.getmReleaseDate(), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, movie.getmId());
    }

    @Override
    public void DetailsFetched(List<Detail> details) {
        for (int i = 0; i < details.size(); i++)
            if (details.get(i).getmIso().contains("US")) {
                List<SubDetails> subDetails = details.get(i).getmRelease();
                for (int j = 0; j < subDetails.size(); i++) {
                    mMPAA_TV.setText(subDetails.get(j).getmCert());
                    //Log.i(TAG, "mpaa: " + mMPAA_TV.getText().toString());
                    break;
                }
            }
    }

    private void fetchCast(Movie movie) {
        new FetchCastTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, movie.getmId());
    }

    @Override
    public void OnCastClicked(int position) {

    }

    @Override
    public void OnCastCalled(List<Cast> casts) {
        castAdapter.add(casts);
    }
}

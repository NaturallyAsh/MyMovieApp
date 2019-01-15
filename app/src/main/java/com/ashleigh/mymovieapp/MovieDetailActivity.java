package com.ashleigh.mymovieapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashleigh.mymovieapp.models.Movie;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private Movie movie;
    private Context context;
    @BindView(R.id.detail_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.detail_CT)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_imageView)
    ImageView movieBackdrop;

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
            Log.i(TAG, "movie detail: " + movie.getmId());
        }
        collapsingToolbar.setTitle(movie.getmTitle());

        loadBackdrop();


    }

    private void loadBackdrop() {
        if (movie.getmBackdrop() != null) {
            Glide.with(getApplicationContext())
                    .load(movie.getBackdropUrl())
                    .into(movieBackdrop);
        }
    }
}

package com.ashleigh.mymovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ashleigh.mymovieapp.adapters.MovieListAdapter;
import com.ashleigh.mymovieapp.data.FetchResultsTask;
import com.ashleigh.mymovieapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsActivity extends AppCompatActivity implements
        FetchResultsTask.OnSearchResultListener, MovieListAdapter.MovieListCallback {

    private static final String TAG = SearchResultsActivity.class.getSimpleName();

    public static final String ARG_SEARCH_ITEM = "search_movie_item";

    @BindView(R.id.empty_search_tv)
    TextView emptyTv;
    @BindView(R.id.search_results_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.search_toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_results_progress)
    ProgressBar progressBar;
    private MovieListAdapter adapter;
    private ArrayList<Movie> movieList = new ArrayList<>();
    private String mSearchQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent searchIntent = getIntent();
        if (searchIntent != null) {
            mSearchQuery = searchIntent.getStringExtra(MainActivity.ARG_SEARCH);
            //Log.i(TAG, "received query: " + mSearchQuery);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MovieListAdapter(SearchResultsActivity.this, movieList, this);
        recyclerView.setAdapter(adapter);

        if (mSearchQuery != null) {
            FetchResults(mSearchQuery);
        }
    }

    private void FetchResults(String query) {
        progressBar.setVisibility(View.VISIBLE);
        new FetchResultsTask(this, query).execute();
    }

    @Override
    public void Results(List<Movie> movies) {
        progressBar.setVisibility(View.GONE);
        //Log.i(TAG, "movie id: " + movies.size());
        adapter.add(movies);
    }

    @Override
    public void OpenDetail(Movie movie, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.ARG_MOVIE_ITEM, movie);
        Intent intent = new Intent(SearchResultsActivity.this, MovieDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

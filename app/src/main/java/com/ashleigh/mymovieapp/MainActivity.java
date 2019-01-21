package com.ashleigh.mymovieapp;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.ashleigh.mymovieapp.adapters.MovieListAdapter;
import com.ashleigh.mymovieapp.data.FetchMoviesTask;
import com.ashleigh.mymovieapp.models.Movie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    static final int STORAGE_PERMS = 175;
    private static final String EXTRA_SORT = "sort_extra";
    private static final String EXTRA_MOVIES = "movies_extra";
    public static final String ARG_MOVIE_ITEM = "movie_item";
    public static final String ARG_SEARCH = "search_query";

    MovieTrendingFragment mTrendingFragment;
    private MovieListAdapter movieListAdapter;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    String mSort;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.error_tv)
    TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT > 26) {
            checkPermission();
        }

        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            MovieTrendingFragment trendingFragment = new MovieTrendingFragment();
            transaction
                    .add(R.id.movie_container, trendingFragment, TAG)
                    .commit();
        } else {
            getSupportFragmentManager().findFragmentByTag(TAG);
        }

        /*recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieListAdapter = new MovieListAdapter(MainActivity.this, movieArrayList, this);
        recyclerView.setAdapter(movieListAdapter);

        if (savedInstanceState != null) {
            mSort = savedInstanceState.getString(EXTRA_SORT);
            if (savedInstanceState.containsKey(EXTRA_MOVIES)) {
                List<Movie> movies = savedInstanceState.getParcelableArrayList(EXTRA_MOVIES);
                movieListAdapter.add(movies);
            }
        } else {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                fetchMovies(mSort);
            } else {
                errorTv.setVisibility(View.VISIBLE);
            }
        }
        */
        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String searchQuery = searchIntent.getStringExtra(SearchManager.QUERY);
            Log.i(TAG, "query: " + searchQuery);

            Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
            intent.putExtra(ARG_SEARCH, searchQuery);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*outState.putString(EXTRA_SORT, mSort);
        ArrayList<Movie> movies = movieListAdapter.getmMovies();
        if (movies != null && !movies.isEmpty()) {
            outState.putParcelableArrayList(EXTRA_MOVIES, movieArrayList);
        }*/
    }

    /*@Override
    public void OpenDetail(Movie movie, int position) {
        //Log.i(TAG, "movie item: " + movie.getmId());
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MOVIE_ITEM, movie);
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        /*switch (mSort) {
            case FetchMoviesTask.POPULAR:
                menu.findItem(R.id.menu_popular).setChecked(false);
                break;
            case FetchMoviesTask.TOP_RATED:
                menu.findItem(R.id.menu_top_rated).setChecked(false);
                break;
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment newFragment = null;
        switch (item.getItemId()) {
            case R.id.menu_popular:
                mSort = FetchMoviesTask.POPULAR;
                newFragment = MovieFragment.getInstance(mSort);
                //fetchMovies(mSort);
                //item.setChecked(true);
                break;
            case R.id.menu_top_rated:
                mSort = FetchMoviesTask.TOP_RATED;
                newFragment = MovieFragment.getInstance(mSort);
                //fetchMovies(mSort);
                //item.setChecked(true);
                break;
        }
        if (newFragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction
                    .replace(R.id.movie_container, newFragment)
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void fetchMovies(String sortBy) {
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        FetchMoviesTask.NotifyMovieFetchComplete complete = new FetchMoviesTask.NotifyMovieFetchComplete(this);
        new FetchMoviesTask(sortBy, complete).execute();
    }

    @Override
    public void OnMoviesFetched(Complete complete) {
        if (complete instanceof FetchMoviesTask.NotifyMovieFetchComplete) {
            movieListAdapter.add(((FetchMoviesTask.NotifyMovieFetchComplete) complete).getMovies());
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }*/

    private void checkPermission()
    {
        ArrayList<String> arrayPerm = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED)
        {
            arrayPerm.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED)
        {
            arrayPerm.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            arrayPerm.add(Manifest.permission.INTERNET);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            arrayPerm.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (!arrayPerm.isEmpty())
        {
            String[] permissions = new String[arrayPerm.size()];
            permissions = arrayPerm.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, STORAGE_PERMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults)
    {
        switch (requestCode)
        {
            case STORAGE_PERMS:
            {
                if (grantResults.length > 0)
                {
                    for (int i = 0; i < grantResults.length; i++)
                    {
                        String permission = permissions[i];
                        if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission))
                        {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                            {

                            }
                        }
                        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission))
                        {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                            {

                            }
                        }
                        if (Manifest.permission.INTERNET.equals(permission)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                            }
                        }
                        if (Manifest.permission.ACCESS_NETWORK_STATE.equals(permission)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                            }
                        }
                    }
                }
            }
        }
    }
}

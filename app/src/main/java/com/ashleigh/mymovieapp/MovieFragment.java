package com.ashleigh.mymovieapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.ashleigh.mymovieapp.adapters.MovieListAdapter;
import com.ashleigh.mymovieapp.data.Complete;
import com.ashleigh.mymovieapp.data.FetchMoviesTask;
import com.ashleigh.mymovieapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment implements MovieListAdapter.MovieListCallback,
        FetchMoviesTask.Callback {

    private static final String TAG = MovieFragment.class.getSimpleName();

    static final int STORAGE_PERMS = 175;
    private static final String EXTRA_SORT = "sort_extra";
    private static final String EXTRA_MOVIES = "movies_extra";
    public static final String ARG_MOVIE_ITEM = "movie_item";
    public static final String ARG_SORT = "arg_sort";

    private MainActivity mMainActivity;
    private MovieListAdapter movieListAdapter;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private String mSort = FetchMoviesTask.POPULAR;

    Toolbar toolbar;
    @BindView(R.id.fragment_movie_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_error_tv)
    TextView errorTv;

    public MovieFragment(){}

    public static MovieFragment getInstance(String sort) {
        MovieFragment f = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SORT, sort);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainActivity = (MainActivity)getActivity();
        mMainActivity.getSupportActionBar();
        //setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View rootView = inflater.inflate(R.layout.movie_fragment, parent, false);

        ButterKnife.bind(this, rootView);


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        movieListAdapter = new MovieListAdapter(getContext(), movieArrayList, this);
        recyclerView.setAdapter(movieListAdapter);

        if (savedInstanceState != null) {
            mSort = savedInstanceState.getString(EXTRA_SORT);
            if (savedInstanceState.containsKey(EXTRA_MOVIES)) {
                List<Movie> movies = savedInstanceState.getParcelableArrayList(EXTRA_MOVIES);
                movieListAdapter.add(movies);
            }
        } else {
            mSort = getArguments().getString(ARG_SORT);
        }
        fetchMovies(mSort);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_SORT, mSort);
        ArrayList<Movie> movies = movieListAdapter.getmMovies();
        if (movies != null && !movies.isEmpty()) {
            outState.putParcelableArrayList(EXTRA_MOVIES, movieArrayList);
        }
    }

    @Override
    public void OpenDetail(Movie movie, int position) {
        //Log.i(TAG, "movie item: " + movie.getmId());
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_MOVIE_ITEM, movie);
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);

        /*SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);*/
        switch (mSort) {
            case FetchMoviesTask.POPULAR:
                menu.findItem(R.id.menu_popular).setChecked(true);
                break;
            case FetchMoviesTask.TOP_RATED:
                menu.findItem(R.id.menu_top_rated).setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_popular:
                mSort = FetchMoviesTask.POPULAR;
                fetchMovies(mSort);
                item.setChecked(true);
                break;
            case R.id.menu_top_rated:
                mSort = FetchMoviesTask.TOP_RATED;
                fetchMovies(mSort);
                item.setChecked(true);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchMovies(String sortBy) {
        mMainActivity.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        FetchMoviesTask.NotifyMovieFetchComplete complete = new FetchMoviesTask.NotifyMovieFetchComplete(this);
        new FetchMoviesTask(sortBy, complete).execute();
    }

    @Override
    public void OnMoviesFetched(Complete complete) {
        if (complete instanceof FetchMoviesTask.NotifyMovieFetchComplete) {
            movieListAdapter.add(((FetchMoviesTask.NotifyMovieFetchComplete) complete).getMovies());
            mMainActivity.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }
}

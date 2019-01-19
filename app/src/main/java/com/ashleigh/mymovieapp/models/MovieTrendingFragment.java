package com.ashleigh.mymovieapp.models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashleigh.mymovieapp.MainActivity;
import com.ashleigh.mymovieapp.R;

import androidx.fragment.app.Fragment;

public class MovieTrendingFragment extends Fragment {

    private static final String TAG = MovieTrendingFragment.class.getSimpleName();

    private MainActivity mMainActivity;


    public MovieTrendingFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainActivity = (MainActivity)getActivity();
        mMainActivity.getSupportActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_trending, parent, false);



        return rootView;
    }
}

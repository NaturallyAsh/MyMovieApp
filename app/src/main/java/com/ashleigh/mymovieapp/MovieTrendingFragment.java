package com.ashleigh.mymovieapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import com.ashleigh.mymovieapp.data.FetchDiscoverTask;
import com.ashleigh.mymovieapp.models.Discover;
import com.ashleigh.mymovieapp.views.MyDefaultSliderView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieTrendingFragment extends Fragment implements
        FetchDiscoverTask.OnDiscoverListener, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    private static final String TAG = MovieTrendingFragment.class.getSimpleName();

    Context mContext;
    private MainActivity mMainActivity;
    private List<String> backdropUrls = new ArrayList<>();
    private List<String> posterUrls = new ArrayList<>();
    @BindView(R.id.trend_CT)
    CollapsingToolbarLayout CT;
    @BindView(R.id.trend_backdrop)
    SliderLayout backdropIV;
    @BindView(R.id.trend_poster)
    SliderLayout posterIV;

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
        ButterKnife.bind(this, rootView);

        //backdropIV.getLayoutParams().height = (int) (parent.getWidth() / Discover.POSTER_ASPECT_RATIO);

        fetchDiscover();

        return rootView;
    }

    private void fetchDiscover() {
        new FetchDiscoverTask(this).execute();
    }

    @Override
    public void DiscoverFetched(List<Discover> discovers) {
        for (int i = 0; i < discovers.size(); i++) {
            Log.i(TAG, "backdrop discovers: " + discovers.get(i).getmBackdrop());
            Log.i(TAG, "poster discovers: " + discovers.get(i).getmPoster());

            backdropUrls.add(discovers.get(i).getmBackdrop());
            posterUrls.add(discovers.get(i).getmPoster());

            if (discovers.get(i).getmBackdrop() == null) {
                discovers.remove(i).getmBackdrop();
                discovers.remove(i -1).getmPoster();
            } else if (discovers.get(i).getmPoster() == null) {
                discovers.remove(i).getmPoster();
                discovers.remove(i + 1).getmBackdrop();
            }

        }
        if (backdropUrls != null) {
            loadBackdrop(backdropUrls);
        }
        if (posterUrls != null) {
            loadPoster(posterUrls);
        }
    }

    private void loadBackdrop(List<String> backdropUrls) {

        for (int i = 0; i < backdropUrls.size(); i++) {
            /*if (backdropUrls.get(i) == null) {
                continue;
            }*/

            MyDefaultSliderView sliderView = new MyDefaultSliderView(mMainActivity.getApplicationContext());

            sliderView
                    .image(backdropUrls.get(i))
                    .setRequestOption(new RequestOptions())
                    .setOnSliderClickListener(this);

            backdropIV.addSlider(sliderView);
        }
        backdropIV.setPresetTransformer(SliderLayout.Transformer.Accordion);
        backdropIV.setDuration(4000);
        backdropIV.addOnPageChangeListener(this);
    }

    private void loadPoster(List<String> posterUrls) {

        //Log.i(TAG, "poster urls: " + posterUrls.size());
        for (int i = 0; i < posterUrls.size(); i++) {
            /*if (posterUrls.get(i) == null) {
                continue;
            }*/

            MyDefaultSliderView sliderView = new MyDefaultSliderView(mMainActivity.getApplicationContext());

            sliderView
                    .image(posterUrls.get(i))
                    .setOnSliderClickListener(this);

            posterIV.addSlider(sliderView);
        }
        posterIV.setPresetTransformer(SliderLayout.Transformer.Accordion);
        posterIV.setDuration(4000);
        backdropIV.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStop() {
        backdropIV.stopAutoCycle();
        posterIV.stopAutoCycle();
        super.onStop();
    }
}

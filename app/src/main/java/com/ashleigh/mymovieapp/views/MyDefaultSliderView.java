package com.ashleigh.mymovieapp.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ashleigh.mymovieapp.R;
import com.glide.slider.library.SliderTypes.BaseSliderView;

import androidx.appcompat.widget.AppCompatImageView;

public class MyDefaultSliderView extends BaseSliderView {

    public MyDefaultSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.slider_default_layout, null);
        AppCompatImageView target = view.findViewById(R.id.my_glide_slider_image);
        bindEventAndShow(view, target);
        return view;
    }
}

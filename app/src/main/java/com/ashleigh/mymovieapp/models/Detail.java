package com.ashleigh.mymovieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Detail {

    @SerializedName("iso_3166_1")
    private String mIso;

    @SerializedName("release_dates")
    private List<SubDetails> mRelease;



    public List<SubDetails> getmRelease() {
        return mRelease;
    }

    public String getmIso() {
        return mIso;
    }
}

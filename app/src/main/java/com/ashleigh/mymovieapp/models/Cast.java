package com.ashleigh.mymovieapp.models;

import com.google.gson.annotations.SerializedName;

public class Cast {

    @SerializedName("character")
    private String mCharacter;
    @SerializedName("name")
    private String mName;
    @SerializedName("order")
    private int mOrder;
    @SerializedName("profile_path")
    private String mProfile;

    public String getmName() {
        return mName;
    }

    public String getmCharacter() {
        return mCharacter;
    }

    public int getmOrder() {
        return mOrder;
    }

    public String getmProfile() {
        if (mProfile != null && !mProfile.isEmpty()) {
            return "http://image.tmdb.org/t/p/w342" + mProfile;
        }
        return null;
    }
}

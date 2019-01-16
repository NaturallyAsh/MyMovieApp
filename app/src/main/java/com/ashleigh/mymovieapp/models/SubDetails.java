package com.ashleigh.mymovieapp.models;

import com.google.gson.annotations.SerializedName;

public class SubDetails {

    @SerializedName("certification")
    private String mCert;

    public String getmCert() {
        return mCert;
    }

    public void setmCert(String mCert) {
        this.mCert = mCert;
    }
}

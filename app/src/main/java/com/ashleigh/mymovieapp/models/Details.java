package com.ashleigh.mymovieapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Details {

    @SerializedName("results")
    private List<Detail> getDetails = new ArrayList<>();

    public List<Detail> getAllDetails() {
        return getDetails;
    }
}

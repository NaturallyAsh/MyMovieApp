package com.ashleigh.mymovieapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Trailers {

    @SerializedName("results")
    private List<Trailer> allTrailers = new ArrayList<>();

    public List<Trailer> getAllTrailers() {
        return allTrailers;
    }
}

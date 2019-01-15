package com.ashleigh.mymovieapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movies {

    @SerializedName("results")
    private List<Movie> allMovies = new ArrayList<>();

    public List<Movie> getAllMovies() {
        return allMovies;
    }
}

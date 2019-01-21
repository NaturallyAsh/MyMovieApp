package com.ashleigh.mymovieapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Discovers {

    @SerializedName("results")
    private List<Discover> getDiscovers = new ArrayList<>();

    public List<Discover> getAllDiscovers() {
        return getDiscovers;
    }
}

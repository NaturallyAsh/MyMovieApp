package com.ashleigh.mymovieapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Casts {

    @SerializedName("cast")
    private List<Cast> getCast = new ArrayList<>();

    public List<Cast> getAllCast() {
        return getCast;
    }
}

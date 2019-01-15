package com.ashleigh.mymovieapp.data;

import com.ashleigh.mymovieapp.models.Movies;
import com.ashleigh.mymovieapp.models.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieListData {

    @GET("3/movie/{sort_by}")
    Call<Movies> fetchMovies(@Path("sort_by") String sortBy, @Query("api_key") String api_key);
    @GET("3/movie/{movie_id}/videos")
    Call<Trailers> fetchTrailers(@Path("movie_id") long id, @Query("api_key") String api_key);
    @GET("3/search/movie")
    Call<Movies> fetchSimilarMovies(@Query("api_key") String api_key, @Query("query") String query);
}

package com.ashleigh.mymovieapp.data;

import com.ashleigh.mymovieapp.models.Details;
import com.ashleigh.mymovieapp.models.Discovers;
import com.ashleigh.mymovieapp.models.Movies;
import com.ashleigh.mymovieapp.models.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieListData {

    @GET("3/movie/{sort_by}")
    Call<Movies> fetchMovies(@Path("sort_by") String sortBy, @Query("api_key") String api_key);
    @GET("3/movie/{movie_id}/release_dates")
    Call<Details> fetchMovieDetails(@Path("movie_id") long id, @Query("api_key") String api_key);
    @GET("3/movie/{movie_id}/videos")
    Call<Trailers> fetchTrailers(@Path("movie_id") long id, @Query("api_key") String api_key);
    @GET("3/search/movie")
    Call<Movies> fetchSimilarMovies(@Query("api_key") String api_key, @Query("query") String query);
    @GET("3/discover/movie")
    Call<Discovers> fetchDiscoverMovies(@Query("api_key") String api_key,
                                        @Query("region") String region,
                                        @Query("sort_by") String sort_by,
                                        @Query("include_adult") boolean adult,
                                        @Query("include_video") boolean video,
                                        @Query("page") int page,
                                        @Query("primary_release_year") int release_year,
                                        @Query("with_release_type") int release_type);
}

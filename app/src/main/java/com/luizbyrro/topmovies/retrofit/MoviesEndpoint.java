package com.luizbyrro.topmovies.retrofit;


import com.luizbyrro.topmovies.json.RetornoConfiguration;
import com.luizbyrro.topmovies.json.RetornoMovieInfo;
import com.luizbyrro.topmovies.json.RetornoTMDB;
import com.luizbyrro.topmovies.json.RetornoTrailer;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Luiz Byrro on 03/02/2016.
 */

public interface MoviesEndpoint {

    String BASE_URL = "http://api.themoviedb.org/3";

    String APIKEY = "c4cd8d181d2a547a8b7ec7cdb9df1f9b";
    String API_KEY_URL ="/{movieId}?api_key=c4cd8d181d2a547a8b7ec7cdb9df1f9b";
    String API_GET_MOVIE = "/movie/{movieId}?api_key=c4cd8d181d2a547a8b7ec7cdb9df1f9b";
    String Configuration_URL ="/configuration?api_key=";
    String MoviesPopularity_URL ="/discover/movie?api_key=c4cd8d181d2a547a8b7ec7cdb9df1f9b&sort_by=popularity.desc";
    String MoviesRating_URL ="/discover/movie?api_key=c4cd8d181d2a547a8b7ec7cdb9df1f9b&sort_by=vote_average.desc";
    String MoviesTrailer_URL ="/movie/{movieId}/videos?api_key=c4cd8d181d2a547a8b7ec7cdb9df1f9b";


    @GET( MoviesEndpoint.Configuration_URL+MoviesEndpoint.APIKEY)
    void getConfiguration(Callback<RetornoConfiguration> callback);

    @GET( MoviesPopularity_URL)
    void getTopPopularityMovies(@Query("page") String nPage, Callback<RetornoTMDB> callback);


    @GET( MoviesRating_URL)
    void getTopRatedMovies(@Query("page") String nPage, Callback<RetornoTMDB> callback);

    @GET(API_GET_MOVIE)
    void getMovieById(@Path("movieId") String movieId, Callback<RetornoMovieInfo> callback);

    @GET(MoviesTrailer_URL)
    void getTrailerById(@Path("movieId") String movieId, Callback<RetornoTrailer> callback);


}

package com.luizbyrro.topmovies.retrofit;



import com.luizbyrro.topmovies.json.RetornoConfiguration;
import com.luizbyrro.topmovies.json.RetornoMovieInfo;
import com.luizbyrro.topmovies.json.RetornoTMDB;
import com.luizbyrro.topmovies.json.RetornoTrailer;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by Luiz Byrro on 03/02/2016.
 */

public class MoviesService {

    /* retrofit rest adapter for creating interfaces */
    private static RestAdapter moviesAdapter = new RestAdapter.Builder()
            .setEndpoint(MoviesEndpoint.BASE_URL)
            .setLogLevel(RestAdapter.LogLevel.BASIC)
            .build();


    public static void getConfiguration(Callback<RetornoConfiguration> callback) {
        MoviesEndpoint service = moviesAdapter.create(MoviesEndpoint.class);
        service.getConfiguration(callback);
    }

    public static void getTopPopularityMovies(String nPage, Callback<RetornoTMDB> callback) {
        MoviesEndpoint service = moviesAdapter.create(MoviesEndpoint.class);
        service.getTopPopularityMovies(nPage, callback);
    }

    public static void getTopRatedMovies(String nPage,Callback<RetornoTMDB> callback) {
        MoviesEndpoint service = moviesAdapter.create(MoviesEndpoint.class);
        service.getTopRatedMovies(nPage, callback);
    }

    public static void getMovieById(String movieID, Callback<RetornoMovieInfo> callback) {
        MoviesEndpoint service = moviesAdapter.create(MoviesEndpoint.class);
        service.getMovieById(movieID, callback);
    }

    public static void getTrailerById(String movieID, Callback<RetornoTrailer> callback) {
        MoviesEndpoint service = moviesAdapter.create(MoviesEndpoint.class);
        service.getTrailerById(movieID, callback);
    }



}

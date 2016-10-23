package com.kupferwerk.moviedb.webservice;

import com.kupferwerk.moviedb.webservice.model.MovieDBList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieDBEndpoints {

   @GET ("movie/{endpoint}")
   Observable<MovieDBList> getMoviesByPath(@Path ("endpoint") String endpoint,
         @Query ("api_key") String apiKey);
}

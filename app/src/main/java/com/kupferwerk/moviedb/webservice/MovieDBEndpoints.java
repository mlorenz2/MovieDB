package com.kupferwerk.moviedb.webservice;

import com.kupferwerk.moviedb.webservice.model.MovieDBList;
import com.kupferwerk.moviedb.webservice.model.MovieDBReviewList;
import com.kupferwerk.moviedb.webservice.model.MovieDBVideoList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface MovieDBEndpoints {

   @GET ("movie/{endpoint}")
   Observable<MovieDBList> getMoviesByPath(@Path ("endpoint") String endpoint);

   @GET ("movie/{id}/reviews")
   Observable<MovieDBReviewList> getReviews(@Path ("id") int id);

   @GET ("movie/{id}/videos")
   Observable<MovieDBVideoList> getTrailers(@Path ("id") int id);
}

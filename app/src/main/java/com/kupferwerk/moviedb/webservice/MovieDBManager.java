package com.kupferwerk.moviedb.webservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kupferwerk.moviedb.Injector;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.utils.EndpointUtils;
import com.kupferwerk.moviedb.webservice.model.MovieDB;
import com.kupferwerk.moviedb.webservice.model.MovieDBList;
import com.kupferwerk.moviedb.webservice.model.MovieDBReviewList;
import com.kupferwerk.moviedb.webservice.model.MovieDBVideoList;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func2;

public class MovieDBManager {

   @Inject
   Context context;
   @Inject
   MovieDBEndpoints movieDBEndpoints;

   public MovieDBManager() {
      Injector.getAppComponent()
            .inject(this);
   }

   public Observable<MovieDBList> getMovies() {
      if (EndpointUtils.isFavoriteEndpoint(context)) {
         return Observable.zip(
               movieDBEndpoints.getMoviesByPath(context.getString(R.string.pref_key_top_rated)),
               movieDBEndpoints.getMoviesByPath(context.getString(R.string.pref_key_most_popular)),
               new Func2<MovieDBList, MovieDBList, MovieDBList>() {
                  @Override
                  public MovieDBList call(MovieDBList movieDBList, MovieDBList movieDBList2) {
                     MovieDBList newList = new MovieDBList();
                     newList.setMovieDBList(new ArrayList<MovieDB>());
                     newList.getMovieDBList()
                           .addAll(movieDBList.getMovieDBList());
                     newList.getMovieDBList()
                           .addAll(movieDBList2.getMovieDBList());
                     return newList;
                  }
               });
      }
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
      String endpoint = prefs.getString(context.getString(R.string.pref_endpoint_key),
            context.getString(R.string.pref_key_top_rated));
      return movieDBEndpoints.getMoviesByPath(endpoint);
   }

   public Observable<MovieDBReviewList> getReviews(int movieDBId) {
      return movieDBEndpoints.getReviews(movieDBId);
   }

   public Observable<MovieDBVideoList> getVideos(int movieDBId) {
      return movieDBEndpoints.getTrailers(movieDBId);
   }
}

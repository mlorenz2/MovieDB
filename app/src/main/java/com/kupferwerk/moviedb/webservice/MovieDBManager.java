package com.kupferwerk.moviedb.webservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kupferwerk.moviedb.BuildConfig;
import com.kupferwerk.moviedb.Injector;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.webservice.model.MovieDBList;

import javax.inject.Inject;

import rx.Observable;

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
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
      String endpoint = prefs.getString(context.getString(R.string.pref_endpoint_key),
            context.getString(R.string.pref_key_top_rated));

      return movieDBEndpoints.getMoviesByPath(endpoint, BuildConfig.MOVIE_DB_API_KEY);
   }
}

package com.kupferwerk.moviedb.modules;

import com.kupferwerk.moviedb.BaseApplication;
import com.kupferwerk.moviedb.moviedetail.MovieDetailActivity;
import com.kupferwerk.moviedb.moviedetail.MovieDetailFragment;
import com.kupferwerk.moviedb.movielist.ImageAdapter;
import com.kupferwerk.moviedb.movielist.MovieListActivity;
import com.kupferwerk.moviedb.webservice.MovieDBManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = { ApplicationModule.class })
public interface AppComponent {
   void inject(BaseApplication baseApplication);

   void inject(MovieDetailActivity movieDetailActivity);

   void inject(MovieListActivity movieListActivity);

   void inject(ImageAdapter imageAdapter);

   void inject(MovieDBManager movieDBManager);

   void inject(MovieDetailFragment movieDetailFragment);
}

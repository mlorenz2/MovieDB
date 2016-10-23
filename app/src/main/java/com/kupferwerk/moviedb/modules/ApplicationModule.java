package com.kupferwerk.moviedb.modules;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.webservice.CacheControlInterceptor;
import com.kupferwerk.moviedb.webservice.MovieDBEndpoints;
import com.kupferwerk.moviedb.webservice.MovieDBManager;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

   private static final int CACHE_SIZE_100MB = 100 * 1024 * 1024;
   private static final int CACHE_SIZE_24MB = 24 * 1024 * 1024;
   private final Context context;

   public ApplicationModule(Application application) {
      context = application.getApplicationContext();
   }

   @Provides
   @Singleton
   Context provideContext() {
      return context;
   }

   @Provides
   @Singleton
   MovieDBEndpoints provideMovieDBEndpoints(Retrofit retrofit) {
      return retrofit.create(MovieDBEndpoints.class);
   }

   @Singleton
   @Provides
   MovieDBManager provideMovieDBEndpointsManager() {
      return new MovieDBManager();
   }

   @Singleton
   @Provides
   OkHttpClient provideOkHttpClient() {
      return new OkHttpClient.Builder().cache(new Cache(context.getCacheDir(), CACHE_SIZE_24MB))
            .build();
   }

   @Provides
   @Singleton
   Picasso providePicasso(Context context) {
      File imageCacheDir =
            new File(context.getCacheDir(), context.getString(R.string.config_image_cache_path));

      OkHttpClient.Builder client = new OkHttpClient.Builder();
      client.cache(new Cache(imageCacheDir, CACHE_SIZE_100MB));
      client.networkInterceptors()
            .add(new CacheControlInterceptor());
      Picasso.Builder picasso = new Picasso.Builder(context);
      picasso.downloader(new OkHttp3Downloader(client.build()));

      return picasso.build();
   }

   @Provides
   @Singleton
   Retrofit provideRetrofit(Context context, OkHttpClient client) {
      GsonBuilder gsonBuilder = new GsonBuilder();
      Gson gson = gsonBuilder.create();
      Retrofit.Builder builder =
            new Retrofit.Builder().baseUrl(context.getString(R.string.moviedb_base_url))
                  .addConverterFactory(GsonConverterFactory.create(gson))
                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
      builder.client(client);
      return builder.build();
   }
}

package com.kupferwerk.moviedb.movielist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kupferwerk.moviedb.Injector;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.moviedetail.MovieDetailActivity;
import com.kupferwerk.moviedb.moviedetail.MovieDetailFragment;
import com.kupferwerk.moviedb.settings.SettingsActivity;
import com.kupferwerk.moviedb.webservice.MovieDBManager;
import com.kupferwerk.moviedb.webservice.model.MovieDB;
import com.kupferwerk.moviedb.webservice.model.MovieDBList;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MovieListActivity extends AppCompatActivity {

   private static final String KEY_PARCELABLE_MOVIEDB = "key.parcelable.moviedb";
   @Bind (R.id.frameLayout)
   ViewGroup contentContainer;
   @Inject
   Context context;
   @Bind (R.id.loading_spinner)
   View loadingSpinner;
   @Inject
   MovieDBManager movieDBManager;
   private ImageAdapter adapter;
   private boolean mTwoPane;
   private Subscription subscription;

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.movielist_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case R.id.settings_item:
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_movie_list);
      Injector.getAppComponent()
            .inject(this);
      ButterKnife.bind(this);

      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      toolbar.setTitle(getTitle());

      checkForTwoPaneLayout();

      initGridLayout();

      if (savedInstanceState != null && savedInstanceState.containsKey(KEY_PARCELABLE_MOVIEDB)) {
         List<MovieDB> tmpList =
               Parcels.unwrap(savedInstanceState.getParcelable(KEY_PARCELABLE_MOVIEDB));

         if (tmpList.isEmpty()) {
            return;
         }
         adapter.update(tmpList);
      }
   }

   @Override
   protected void onPause() {
      super.onPause();
      if (subscription != null && !subscription.isUnsubscribed()) {
         subscription.unsubscribe();
      }
   }

   @Override
   protected void onResume() {
      super.onResume();
      fetchMovies();
   }

   @Override
   protected void onSaveInstanceState(Bundle outState) {
      outState.putParcelable(KEY_PARCELABLE_MOVIEDB, Parcels.wrap(adapter.getMovieDBList()));
      super.onSaveInstanceState(outState);
   }

   private void checkForTwoPaneLayout() {
      if (findViewById(R.id.movie_detail_container) != null) {
         mTwoPane = true;
      }
   }

   private void fetchMovies() {
      loadingSpinner.setVisibility(View.VISIBLE);
      subscription = movieDBManager.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<MovieDBList>() {
               @Override
               public void call(MovieDBList movieDBList) {
                  loadingSpinner.setVisibility(View.GONE);
                  adapter.update(movieDBList.getMovieDBList());
               }
            }, new Action1<Throwable>() {
               @Override
               public void call(Throwable throwable) {
                  loadingSpinner.setVisibility(View.GONE);
                  final Snackbar snack = Snackbar.make(contentContainer, R.string.no_internet,
                        Snackbar.LENGTH_INDEFINITE);
                  snack.setAction(R.string.retry, new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                        fetchMovies();
                        snack.dismiss();
                     }
                  });
                  snack.show();
               }
            });
   }

   private void initGridLayout() {
      GridView gridView = ButterKnife.findById(this, R.id.gridView);
      adapter = new ImageAdapter(new ArrayList<MovieDB>());
      gridView.setAdapter(adapter);
      gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Parcelable clickedWrappedItem = Parcels.wrap(adapter.getItem(position));
            if (mTwoPane) {
               MovieDetailFragment fragment = MovieDetailFragment.newInstance(clickedWrappedItem);
               getSupportFragmentManager().beginTransaction()
                     .replace(R.id.movie_detail_container, fragment)
                     .commit();
            } else {
               Intent movieDetailActivity =
                     new Intent(MovieListActivity.this, MovieDetailActivity.class);
               movieDetailActivity.putExtra(MovieDetailActivity.EXTRA_MOVIEDB_ITEM,
                     clickedWrappedItem);
               startActivity(movieDetailActivity);
            }
         }
      });
   }
}

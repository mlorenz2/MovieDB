package com.kupferwerk.moviedb.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kupferwerk.moviedb.Injector;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.movielist.MovieListActivity;

import javax.inject.Inject;

public class MovieDetailActivity extends AppCompatActivity {

   public static final String EXTRA_MOVIEDB_ITEM = "arg.moviedb.item";

   @Inject
   Context context;

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == android.R.id.home) {
         // This ID represents the Home or Up button. In the case of this
         // activity, the Up button is shown. For
         // more details, see the Navigation pattern on Android Design:
         //
         // http://developer.android.com/design/patterns/navigation.html#up-vs-back
         //
         navigateUpTo(new Intent(this, MovieListActivity.class));
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Injector.getAppComponent()
            .inject(this);
      setContentView(R.layout.activity_movie_detail);
      Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
      setSupportActionBar(toolbar);

      // Show the Up button in the action bar.
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
         actionBar.setDisplayHomeAsUpEnabled(true);
      }

      // savedInstanceState is non-null when there is fragment state
      // saved from previous configurations of this activity
      // (e.g. when rotating the screen from portrait to landscape).
      // In this case, the fragment will automatically be re-added
      // to its container so we don't need to manually add it.
      // For more information, see the Fragments API guide at:
      //
      // http://developer.android.com/guide/components/fragments.html
      //
      if (savedInstanceState == null) {
         // Create the detail fragment and add it to the activity
         // using a fragment transaction.
         MovieDetailFragment fragment = MovieDetailFragment.newInstance(
               getIntent().getParcelableExtra(MovieDetailFragment.ARG_MOVIEDB_ITEM));
         getSupportFragmentManager().beginTransaction()
               .add(R.id.movie_detail_container, fragment)
               .commit();
      }
   }
}

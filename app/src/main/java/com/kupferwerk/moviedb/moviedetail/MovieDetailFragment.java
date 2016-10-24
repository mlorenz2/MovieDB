package com.kupferwerk.moviedb.moviedetail;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kupferwerk.moviedb.Injector;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.webservice.model.MovieDB;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment {

   public static final String ARG_MOVIEDB_ITEM = "arg.moviedb.item";
   @Bind (R.id.overview)
   TextView movieOverview;
   @Bind (R.id.user_rating)
   TextView movieRating;
   @Bind (R.id.release_date)
   TextView movieReleaseDate;
   @Bind (R.id.movie_thumbnail)
   ImageView movieThumbnail;
   @Bind (R.id.movie_title)
   TextView movieTitle;
   @Inject
   Picasso picasso;
   private MovieDB currentMovieDBItem;

   public static MovieDetailFragment newInstance(Parcelable parcelableData) {
      Bundle args = new Bundle();
      args.putParcelable(ARG_MOVIEDB_ITEM, parcelableData);
      MovieDetailFragment fragment = new MovieDetailFragment();
      fragment.setArguments(args);
      return fragment;
   }

   public MovieDetailFragment() {
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Injector.getAppComponent()
            .inject(this);
      if (getArguments().containsKey(ARG_MOVIEDB_ITEM)) {
         currentMovieDBItem = Parcels.unwrap(getArguments().getParcelable(ARG_MOVIEDB_ITEM));
      }
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.movie_detail, container, false);
      ButterKnife.bind(this, rootView);
      updateUI();
      return rootView;
   }

   private void updateUI() {
      if (currentMovieDBItem != null) {
         Activity activity = this.getActivity();
         CollapsingToolbarLayout appBarLayout = ButterKnife.findById(activity, R.id.toolbar_layout);
         ImageView appBarImage = ButterKnife.findById(activity, R.id.backdrop);

         if (appBarLayout != null) {
            appBarLayout.setTitle(currentMovieDBItem.getTitle());
            String url = activity.getString(R.string.moviedb_image_url) +
                  currentMovieDBItem.getBackdropPath();
            picasso.load(url)
                  .fit()
                  .centerInside()
                  .into(appBarImage);
         }

         String thumbnail = activity.getString(R.string.moviedb_thumbnail_url) +
               currentMovieDBItem.getPosterPath();
         picasso.load(thumbnail)
               .fit()
               .centerInside()
               .into(movieThumbnail);

         movieTitle.setText(currentMovieDBItem.getTitle());
         movieOverview.setText(currentMovieDBItem.getOverview());
         movieRating.setText(String.valueOf(currentMovieDBItem.getVoteAverage()));
         movieReleaseDate.setText(currentMovieDBItem.getReleaseDate());
      }
   }
}

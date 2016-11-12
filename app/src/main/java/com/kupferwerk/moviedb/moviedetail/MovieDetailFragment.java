package com.kupferwerk.moviedb.moviedetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kupferwerk.moviedb.Injector;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.moviedetail.model.SectionHeader;
import com.kupferwerk.moviedb.webservice.MovieDBManager;
import com.kupferwerk.moviedb.webservice.model.MovieDB;
import com.kupferwerk.moviedb.webservice.model.MovieDBReviewList;
import com.kupferwerk.moviedb.webservice.model.MovieDBVideo;
import com.kupferwerk.moviedb.webservice.model.MovieDBVideoList;
import com.kupferwerk.moviedb.webservice.model.MovieDetailItem;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MovieDetailFragment extends Fragment {

   public static final String ARG_MOVIEDB_ITEM = "arg.moviedb.item";
   public static final String KEY_SAVED_ITEMS = "key.saved.items";

   @Inject
   Context context;
   @Inject
   MovieDBManager movieDBManager;
   @Inject
   Picasso picasso;
   @Bind (R.id.recycler_view)
   RecyclerView recyclerView;
   private MovieDB currentMovieDBItem;
   private MovieDetailAdapter movieDetailAdapter;

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
         fetchDetailData(currentMovieDBItem.getId());
      }
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.movie_detail, container, false);
      ButterKnife.bind(this, rootView);
      initRecyclerView();
      updateUI();
      return rootView;
   }

   private void fetchDetailData(int movieDBId) {
      Observable.zip(movieDBManager.getVideos(movieDBId), movieDBManager.getReviews(movieDBId),
            new Func2<MovieDBVideoList, MovieDBReviewList, List<MovieDetailItem>>() {
               @Override
               public List<MovieDetailItem> call(MovieDBVideoList movieDBVideoList,
                     MovieDBReviewList movieDBReviewList) {
                  List<MovieDetailItem> movieDetailItemList = new ArrayList<>();
                  if (!movieDBVideoList.getMovieDBVideoList()
                        .isEmpty()) {
                     movieDetailItemList.add(new SectionHeader(
                           context.getString(R.string.movie_detail_section_header_video)));
                     movieDetailItemList.addAll(movieDBVideoList.getMovieDBVideoList());
                  }
                  if (!movieDBReviewList.getMovieDBReviewList()
                        .isEmpty()) {
                     movieDetailItemList.add(new SectionHeader(
                           context.getString(R.string.movie_detail_section_header_review)));
                     movieDetailItemList.addAll(movieDBReviewList.getMovieDBReviewList());
                  }
                  return movieDetailItemList;
               }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<MovieDetailItem>>() {
               @Override
               public void call(List<MovieDetailItem> movieDetailItems) {
                  movieDetailAdapter.updateAdapter(movieDetailItems);
               }
            }, new Action1<Throwable>() {
               @Override
               public void call(Throwable throwable) {
                  // TODO: 11.11.16 handle useful error case such as no internet
               }
            });
   }

   private void initRecyclerView() {
      movieDetailAdapter = new MovieDetailAdapter(currentMovieDBItem);
      recyclerView.setAdapter(movieDetailAdapter);
      final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
      recyclerView.setLayoutManager(layoutManager);
      recyclerView.addItemDecoration(
            new DividerItemDecoration(context, layoutManager.getOrientation()));

      movieDetailAdapter.setOnClickVideo(new MovieDetailAdapter.OnClickItem() {
         @Override
         public void onClickAddToFavorite(MovieDB movieDB, int position) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            //            Set<String> savedKeys = prefs.getStringSet(KEY_SAVED_ITEMS, null);
            // http://stackoverflow.com/a/14034804
            Set<String> savedKeys =
                  new HashSet<>(prefs.getStringSet(KEY_SAVED_ITEMS, new HashSet<String>()));

            TextView addToFavorite =
                  ButterKnife.findById(layoutManager.findViewByPosition(position),
                        R.id.add_to_favorite);

            //save key
            String keyToSave = String.valueOf(currentMovieDBItem.getId());
            if (!savedKeys.contains(keyToSave)) {
               savedKeys.add(keyToSave);
               prefs.edit()
                     .putStringSet(KEY_SAVED_ITEMS, savedKeys)
                     .apply();
               addToFavorite.setText(context.getString(R.string.favorite));
               addToFavorite.setCompoundDrawablesWithIntrinsicBounds(0,
                     R.drawable.ic_star_orange_48dp, 0, 0);
               //               movieDetailAdapter.notifyItemChanged(position);
            } else {
               savedKeys.remove(keyToSave);
               prefs.edit()
                     .putStringSet(KEY_SAVED_ITEMS, savedKeys)
                     .apply();
               addToFavorite.setText(context.getString(R.string.add_to_favorite));
               addToFavorite.setCompoundDrawablesWithIntrinsicBounds(0,
                     R.drawable.ic_star_border_orange_48dp, 0, 0);
               //               movieDetailAdapter.notifyItemChanged(position);
            }
         }

         @Override
         public void onClickVideo(MovieDBVideo movieDBVideo) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                  Uri.parse(getString(R.string.youtube) + movieDBVideo.getKey())));
         }
      });
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
      }
   }
}

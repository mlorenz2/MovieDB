package com.kupferwerk.moviedb.movielist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kupferwerk.moviedb.Injector;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.webservice.model.MovieDB;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class ImageAdapter extends BaseAdapter {

   @Inject
   Context context;
   @Inject
   Picasso picasso;
   private List<MovieDB> movieDBList;

   public ImageAdapter(List<MovieDB> movieDBList) {
      Injector.getAppComponent()
            .inject(this);
      this.movieDBList = movieDBList;
   }

   @Override
   public int getCount() {
      return movieDBList.size();
   }

   @Override
   public MovieDB getItem(int i) {
      return movieDBList.get(i);
   }

   @Override
   public long getItemId(int i) {
      return movieDBList.get(i)
            .getId();
   }

   public List<MovieDB> getMovieDBList() {
      return movieDBList;
   }

   @Override
   public View getView(int i, View view, ViewGroup viewGroup) {
      ImageView imageView;
      if (view == null) {
         imageView = new ImageView(context);
         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      } else {
         imageView = (ImageView) view;
      }

      picasso.load(createImageUrl(i))
            .into(imageView);
      return imageView;
   }

   public void update(List<MovieDB> newItems) {
      this.movieDBList = newItems;
      notifyDataSetChanged();
   }

   private String createImageUrl(int i) {
      MovieDB currentItem = movieDBList.get(i);
      return context.getString(R.string.moviedb_image_url) + currentItem.getPosterPath();
   }
}

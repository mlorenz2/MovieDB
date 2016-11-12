package com.kupferwerk.moviedb.movielist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kupferwerk.moviedb.Injector;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.webservice.model.MovieDB;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   interface OnItemClickedListener {
      void onItemClicked(MovieDB movieDB);
   }

   public static class PosterViewHolder extends RecyclerView.ViewHolder {

      @Bind (R.id.movie_poster)
      ImageView moviePoster;

      public PosterViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }
   }

   @Inject
   Context context;
   @Inject
   Picasso picasso;
   private List<MovieDB> movieDBList;
   private OnItemClickedListener onItemClickedListener;

   public ImgAdapter(List<MovieDB> movieDBList) {
      Injector.getAppComponent()
            .inject(this);
      this.movieDBList = movieDBList;
   }

   @Override
   public int getItemCount() {
      return movieDBList.size();
   }

   public List<MovieDB> getMovieDBList() {
      return movieDBList;
   }

   public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
      this.onItemClickedListener = onItemClickedListener;
   }

   @Override
   public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      if (onItemClickedListener != null) {
         onItemClickedListener.onItemClicked(movieDBList.get(position));
      }
      picasso.load(createImageUrl(position))
            .into(((PosterViewHolder) holder).moviePoster);
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.movie_poster, parent, false);
      return new PosterViewHolder(view);
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

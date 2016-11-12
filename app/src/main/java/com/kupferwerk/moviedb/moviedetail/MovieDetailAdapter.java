package com.kupferwerk.moviedb.moviedetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kupferwerk.moviedb.Injector;
import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.moviedetail.model.SectionHeader;
import com.kupferwerk.moviedb.webservice.model.MovieDB;
import com.kupferwerk.moviedb.webservice.model.MovieDBReview;
import com.kupferwerk.moviedb.webservice.model.MovieDBVideo;
import com.kupferwerk.moviedb.webservice.model.MovieDetailItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   interface OnClickItem {
      void onClickAddToFavorite(MovieDB movieDB, int position);

      void onClickVideo(MovieDBVideo movieDBVideo);
   }

   public static class HeaderViewHolder extends RecyclerView.ViewHolder {
      @Bind (R.id.add_to_favorite)
      TextView addToFavorite;
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

      public HeaderViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }
   }

   public static class ReviewViewHolder extends RecyclerView.ViewHolder {

      @Bind (R.id.movie_detail_review)
      TextView review;

      public ReviewViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }
   }

   public static class SectionViewHolder extends RecyclerView.ViewHolder {

      @Bind (R.id.item_movie_section_header)
      TextView sectionHeader;

      public SectionViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }
   }

   public static class VideoViewHolder extends RecyclerView.ViewHolder {

      @Bind (R.id.item_movie_video)
      View video;
      @Bind (R.id.movie_detail_video_title)
      TextView videoTitle;

      public VideoViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }
   }

   @Inject
   Context context;
   @Inject
   Picasso picasso;
   private List<MovieDetailItem> detailItems;
   private OnClickItem onClickItem;

   MovieDetailAdapter(MovieDetailItem headerItem) {
      Injector.getAppComponent()
            .inject(this);
      this.detailItems = new ArrayList<>();
      this.detailItems.add(headerItem);
   }

   @Override
   public int getItemCount() {
      return detailItems.size();
   }

   @Override
   public int getItemViewType(int position) {
      int detailItem = detailItems.get(position)
            .getLayout();

      switch (detailItem) {
         case R.layout.item_movie_detail:
            return R.layout.item_movie_detail;
         case R.layout.item_movie_section_header:
            return R.layout.item_movie_section_header;
         case R.layout.item_movie_video:
            return R.layout.item_movie_video;
         default:
         case R.layout.item_movie_review:
            return R.layout.item_movie_review;
      }
   }

   @Override
   public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
      switch (getItemViewType(position)) {
         default:
         case R.layout.movie_detail:

            HeaderViewHolder movieDetail = ((HeaderViewHolder) holder);
            final MovieDB currentMovieDBItem = (MovieDB) detailItems.get(position);

            String thumbnail = context.getString(R.string.moviedb_thumbnail_url) +
                  currentMovieDBItem.getPosterPath();
            picasso.load(thumbnail)
                  .fit()
                  .centerInside()
                  .into(movieDetail.movieThumbnail);

            movieDetail.movieTitle.setText(currentMovieDBItem.getTitle());
            movieDetail.movieOverview.setText(currentMovieDBItem.getOverview()
                  .trim());
            movieDetail.movieRating.setText(String.valueOf(currentMovieDBItem.getVoteAverage()));
            movieDetail.movieReleaseDate.setText(currentMovieDBItem.getReleaseDate());

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            Set<String> keys = prefs.getStringSet(MovieDetailFragment.KEY_SAVED_ITEMS, null);
            if (keys != null && keys.contains(String.valueOf(currentMovieDBItem.getId()))) {
               movieDetail.addToFavorite.setText(context.getString(R.string.favorite));
               movieDetail.addToFavorite.setCompoundDrawablesWithIntrinsicBounds(0,
                     R.drawable.ic_star_orange_48dp, 0, 0);
            }
            if (onClickItem != null) {
               movieDetail.addToFavorite.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                     onClickItem.onClickAddToFavorite(currentMovieDBItem,
                           holder.getAdapterPosition());
                  }
               });
            }

            break;
         case R.layout.item_movie_section_header:
            SectionHeader currentSectionHeaderItem = (SectionHeader) detailItems.get(position);
            ((SectionViewHolder) holder).sectionHeader.setText(currentSectionHeaderItem.getTitle());
            break;
         case R.layout.item_movie_video:
            final MovieDBVideo currentVideoItem = (MovieDBVideo) detailItems.get(position);
            VideoViewHolder video = ((VideoViewHolder) holder);
            video.videoTitle.setText(currentVideoItem.getName());
            if (onClickItem != null) {
               video.video.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                     onClickItem.onClickVideo(currentVideoItem);
                  }
               });
            }
            break;
         case R.layout.item_movie_review:
            MovieDBReview currentReviewItem = (MovieDBReview) detailItems.get(position);
            ((ReviewViewHolder) holder).review.setText(currentReviewItem.getContent());
            break;
      }
   }

   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
            .inflate(viewType, parent, false);
      switch (viewType) {
         case R.layout.item_movie_detail:
            return new HeaderViewHolder(view);
         case R.layout.item_movie_section_header:
            return new SectionViewHolder(view);
         case R.layout.item_movie_video:
            return new VideoViewHolder(view);
         default:
         case R.layout.item_movie_review:
            return new ReviewViewHolder(view);
      }
   }

   public void setOnClickVideo(OnClickItem onClickItem) {
      this.onClickItem = onClickItem;
   }

   void updateAdapter(List<MovieDetailItem> detailItems) {
      this.detailItems.addAll(detailItems);
      notifyItemRangeChanged(1, detailItems.size());
   }
}

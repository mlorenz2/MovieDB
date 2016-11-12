package com.kupferwerk.moviedb.webservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kupferwerk.moviedb.R;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class MovieDB implements MovieDetailItem {

   @Expose
   private boolean adult;
   @SerializedName ("backdrop_path")
   @Expose
   private String backdropPath;
   @SerializedName ("genre_ids")
   @Expose
   private List<Integer> genreIdList;
   @Expose
   private int id;
   @SerializedName ("original_language")
   @Expose
   private String originalLanguage;
   @SerializedName ("original_title")
   @Expose
   private String originalTitle;
   @Expose
   private String overview;
   @Expose
   private double popularity;
   @SerializedName ("poster_path")
   @Expose
   private String posterPath;
   @SerializedName ("release_date")
   @Expose
   private String releaseDate;
   @Expose
   private String title;
   @Expose
   private String video;
   @SerializedName ("vote_average")
   @Expose
   private double voteAverage;
   @SerializedName ("vote_count")
   @Expose
   private int voteCount;

   public String getBackdropPath() {
      return backdropPath;
   }

   public void setBackdropPath(String backdropPath) {
      this.backdropPath = backdropPath;
   }

   public List<Integer> getGenreIdList() {
      return genreIdList;
   }

   public void setGenreIdList(List<Integer> genreIdList) {
      this.genreIdList = genreIdList;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   @Override
   public int getLayout() {
      return R.layout.item_movie_detail;
   }

   public String getOriginalLanguage() {
      return originalLanguage;
   }

   public void setOriginalLanguage(String originalLanguage) {
      this.originalLanguage = originalLanguage;
   }

   public String getOriginalTitle() {
      return originalTitle;
   }

   public void setOriginalTitle(String originalTitle) {
      this.originalTitle = originalTitle;
   }

   public String getOverview() {
      return overview;
   }

   public void setOverview(String overview) {
      this.overview = overview;
   }

   public double getPopularity() {
      return popularity;
   }

   public void setPopularity(double popularity) {
      this.popularity = popularity;
   }

   public String getPosterPath() {
      return posterPath;
   }

   public void setPosterPath(String posterPath) {
      this.posterPath = posterPath;
   }

   public String getReleaseDate() {
      return releaseDate;
   }

   public void setReleaseDate(String releaseDate) {
      this.releaseDate = releaseDate;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getVideo() {
      return video;
   }

   public void setVideo(String video) {
      this.video = video;
   }

   public double getVoteAverage() {
      return voteAverage;
   }

   public void setVoteAverage(double voteAverage) {
      this.voteAverage = voteAverage;
   }

   public int getVoteCount() {
      return voteCount;
   }

   public void setVoteCount(int voteCount) {
      this.voteCount = voteCount;
   }

   public boolean isAdult() {
      return adult;
   }

   public void setAdult(boolean adult) {
      this.adult = adult;
   }
}

package com.kupferwerk.moviedb.webservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDBReviewList {

   @Expose
   private int id;
   @SerializedName ("results")
   @Expose
   private List<MovieDBReview> movieDBReviewList;
   @Expose
   private int page;
   @SerializedName ("total_pages")
   @Expose
   private int totalPages;
   @SerializedName ("total_results")
   @Expose
   private int totalResults;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public List<MovieDBReview> getMovieDBReviewList() {
      return movieDBReviewList;
   }

   public void setMovieDBReviewList(List<MovieDBReview> movieDBReviewList) {
      this.movieDBReviewList = movieDBReviewList;
   }

   public int getPage() {
      return page;
   }

   public void setPage(int page) {
      this.page = page;
   }

   public int getTotalPages() {
      return totalPages;
   }

   public void setTotalPages(int totalPages) {
      this.totalPages = totalPages;
   }

   public int getTotalResults() {
      return totalResults;
   }

   public void setTotalResults(int totalResults) {
      this.totalResults = totalResults;
   }
}

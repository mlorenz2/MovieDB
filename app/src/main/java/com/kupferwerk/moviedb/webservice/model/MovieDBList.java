package com.kupferwerk.moviedb.webservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDBList {

   @SerializedName ("results")
   @Expose
   private List<MovieDB> movieDBList;
   @Expose
   private int page;
   @SerializedName ("total_pages")
   @Expose
   private int totalPages;
   @SerializedName ("total_results")
   @Expose
   private int totalResults;

   public List<MovieDB> getMovieDBList() {
      return movieDBList;
   }

   public void setMovieDBList(List<MovieDB> movieDBList) {
      this.movieDBList = movieDBList;
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

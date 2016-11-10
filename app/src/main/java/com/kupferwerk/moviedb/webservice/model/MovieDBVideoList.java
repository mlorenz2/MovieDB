package com.kupferwerk.moviedb.webservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDBVideoList {
   @Expose
   private int id;
   @SerializedName ("results")
   @Expose
   private List<MovieDBVideo> movieDBVideoList;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public List<MovieDBVideo> getMovieDBVideoList() {
      return movieDBVideoList;
   }

   public void setMovieDBVideoList(List<MovieDBVideo> movieDBVideoList) {
      this.movieDBVideoList = movieDBVideoList;
   }
}

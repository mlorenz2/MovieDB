package com.kupferwerk.moviedb.moviedetail;

import com.kupferwerk.moviedb.webservice.model.MovieDBReviewList;
import com.kupferwerk.moviedb.webservice.model.MovieDBVideoList;

public class DetailViewModel {
   private MovieDBReviewList movieDBReviewList;
   private MovieDBVideoList movieDBVideoList;

   public DetailViewModel(MovieDBVideoList movieDBVideoList, MovieDBReviewList movieDBReviewList) {
      this.movieDBVideoList = movieDBVideoList;
      this.movieDBReviewList = movieDBReviewList;
   }

   public MovieDBReviewList getMovieDBReviewList() {
      return movieDBReviewList;
   }

   public void setMovieDBReviewList(MovieDBReviewList movieDBReviewList) {
      this.movieDBReviewList = movieDBReviewList;
   }

   public MovieDBVideoList getMovieDBVideoList() {
      return movieDBVideoList;
   }

   public void setMovieDBVideoList(MovieDBVideoList movieDBVideoList) {
      this.movieDBVideoList = movieDBVideoList;
   }
}

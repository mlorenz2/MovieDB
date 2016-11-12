package com.kupferwerk.moviedb.webservice.model;

import com.google.gson.annotations.Expose;
import com.kupferwerk.moviedb.R;

public class MovieDBReview implements MovieDetailItem {

   @Expose
   private String author;
   @Expose
   private String content;
   @Expose
   private String id;
   @Expose
   private String url;

   public String getAuthor() {
      return author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   @Override
   public int getLayout() {
      return R.layout.item_movie_review;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }
}

package com.kupferwerk.moviedb.moviedetail.model;

import com.kupferwerk.moviedb.R;
import com.kupferwerk.moviedb.webservice.model.MovieDetailItem;

public class SectionHeader implements MovieDetailItem {
   private String title;

   public SectionHeader(String title) {
      this.title = title;
   }

   @Override
   public int getLayout() {
      return R.layout.item_movie_section_header;
   }

   public String getTitle() {
      return title;
   }
}

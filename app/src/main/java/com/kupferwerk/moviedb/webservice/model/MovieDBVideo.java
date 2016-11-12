package com.kupferwerk.moviedb.webservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kupferwerk.moviedb.R;

import org.parceler.Parcel;

@Parcel
public class MovieDBVideo implements MovieDetailItem {

   @SerializedName ("iso_639_1")
   @Expose
   private String country;
   @Expose
   private String id;
   @Expose
   private String key;
   @SerializedName ("iso_3166_1")
   @Expose
   private String language;
   @Expose
   private String name;
   @Expose
   private String site;
   @Expose
   private int size;
   @Expose
   private String type;

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getLanguage() {
      return language;
   }

   public void setLanguage(String language) {
      this.language = language;
   }

   @Override
   public int getLayout() {
      return R.layout.item_movie_video;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSite() {
      return site;
   }

   public void setSite(String site) {
      this.site = site;
   }

   public int getSize() {
      return size;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }
}

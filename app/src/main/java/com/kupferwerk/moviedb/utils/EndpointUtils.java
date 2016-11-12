package com.kupferwerk.moviedb.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.kupferwerk.moviedb.R;

public class EndpointUtils {
   public static boolean isFavoriteEndpoint(Context context) {
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
      String endpoint = prefs.getString(context.getString(R.string.pref_endpoint_key),
            context.getString(R.string.pref_key_top_rated));
      return TextUtils.equals(endpoint, context.getString(R.string.pref_key_favorites));
   }
}

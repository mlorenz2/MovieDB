package com.kupferwerk.moviedb.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kupferwerk.moviedb.R;

public class SettingsActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_settings);
      getFragmentManager().beginTransaction()
            .replace(R.id.container, new SettingsFragment())
            .commit();
   }
}

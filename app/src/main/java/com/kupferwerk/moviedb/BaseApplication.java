package com.kupferwerk.moviedb;

import android.app.Application;
import android.support.multidex.MultiDex;

public class BaseApplication extends Application {

   @Override
   public void onCreate() {
      super.onCreate();
      Injector.init(this);
      Injector.getAppComponent()
            .inject(this);
      MultiDex.install(this);
   }
}

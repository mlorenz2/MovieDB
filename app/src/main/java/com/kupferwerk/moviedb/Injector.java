package com.kupferwerk.moviedb;

import com.kupferwerk.moviedb.modules.AppComponent;
import com.kupferwerk.moviedb.modules.ApplicationModule;
import com.kupferwerk.moviedb.modules.DaggerAppComponent;

public final class Injector {
   private static AppComponent appComponent;

   public static AppComponent getAppComponent() {
      return appComponent;
   }

   public static void init(BaseApplication application) {
      appComponent = DaggerAppComponent.builder()
            .applicationModule(new ApplicationModule(application))
            .build();
   }
}

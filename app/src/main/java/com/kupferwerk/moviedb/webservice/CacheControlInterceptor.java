package com.kupferwerk.moviedb.webservice;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CacheControlInterceptor implements Interceptor {
   @Override
   public Response intercept(Chain chain) throws IOException {
      Response originalResponse = chain.proceed(chain.request());
      return originalResponse.newBuilder()
            .removeHeader("Cache-Control")
            .removeHeader("Cache-control")
            .build();
   }
}

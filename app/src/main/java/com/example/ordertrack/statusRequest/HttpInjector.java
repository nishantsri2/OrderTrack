package com.example.ordertrack.statusRequest;

import com.example.ordertrack.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpInjector {
    public static final String API_BASE_URL = BuildConfig.API_URL;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    private static Retrofit retrofit;
    public static  Retrofit getRetrofit() {
        if(retrofit == null) {
            synchronized (HttpInjector.class) {
                if(retrofit == null) {
                    if(BuildConfig.DEBUG){
                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        httpClient.addInterceptor(interceptor);
                    }
                    httpClient.connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS);

                    OkHttpClient okHttpClient = httpClient.build();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                }
            }
        }
        return  retrofit;

    }
}

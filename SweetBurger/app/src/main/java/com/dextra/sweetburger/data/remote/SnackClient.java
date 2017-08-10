package com.dextra.sweetburger.data.remote;


import android.content.Context;
import android.util.Log;

import com.dextra.sweetburger.utilities.Constants;
import com.dextra.sweetburger.utilities.Utility;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * Created by henriquescutari on 8/7/17.
 */

public class SnackClient {


    private static final String TAG = SnackClient.class.getName();
    private static final String BASE_URL = Constants.API_URL;
    private static final String CACHE_CONTROL = "Cache-Control";
    private static IBurgerAPI burger;
    private static IIngredientAPI ingredient;
    private static IOrderAPI order;
    private static IPromotionAPI promotion;

    private static Retrofit getInstance(Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(context))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static IBurgerAPI getBurgerData(Context context){
        if(burger == null) {
            burger = getInstance(context).create(IBurgerAPI.class);
        }

        return burger;
    }

    public static IIngredientAPI getIngredient(Context context){
        if(ingredient == null) {
            ingredient = getInstance(context).create(IIngredientAPI.class);
        }

        return ingredient;
    }

    public static IOrderAPI getOrder(Context context){
        if(order == null) {
            order = getInstance(context).create(IOrderAPI.class);
        }

        return order;
    }

    public static IPromotionAPI getPromotion(Context context){
        if(promotion == null) {
            promotion = getInstance(context).create(IPromotionAPI.class);
        }

        return promotion;
    }

    private static OkHttpClient getOkHttpClient(Context context) {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okClientBuilder.addInterceptor(httpLoggingInterceptor);


        okClientBuilder.addInterceptor(provideOfflineCacheInterceptor(context));
        okClientBuilder.addNetworkInterceptor(provideCacheInterceptor());
        okClientBuilder.cache(provideCache(context));

        return okClientBuilder.build();
    }

    private static Cache provideCache(Context context) {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e(TAG, "Could not create Cache!");
        }
        return cache;
    }

    private static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(1, TimeUnit.HOURS)
                        .build();

                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    private static Interceptor provideOfflineCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!Utility.isNetworkAvailable(context)) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }
}


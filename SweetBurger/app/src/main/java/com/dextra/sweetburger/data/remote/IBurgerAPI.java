package com.dextra.sweetburger.data.remote;

import com.dextra.sweetburger.model.Burger;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by henriquescutari on 8/7/17.
 */

public interface IBurgerAPI {

    @GET("api/lanche")
    Observable<List<Burger>> getBurger();

    @GET("api/lanche/{idBurger}")
    Observable<Burger> getBurger(@Path("idBurger")long idBurger);
}



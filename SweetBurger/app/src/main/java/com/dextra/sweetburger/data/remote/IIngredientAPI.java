package com.dextra.sweetburger.data.remote;

import com.dextra.sweetburger.model.Ingredient;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by henriquescutari on 8/8/17.
 */

public interface IIngredientAPI {

    @GET("api/ingrediente/de/{idBurger}")
    Observable<List<Ingredient>> getIngredient(@Path("idBurger")long idBurger);

    @GET("api/ingrediente")
    Observable<List<Ingredient>> getIngredient();
}

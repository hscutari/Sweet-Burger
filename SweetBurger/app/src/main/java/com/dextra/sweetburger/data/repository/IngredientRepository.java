package com.dextra.sweetburger.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dextra.sweetburger.data.remote.IIngredientAPI;
import com.dextra.sweetburger.model.Ingredient;

import java.util.List;

import rx.Observable;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class IngredientRepository {
    @Nullable
    private static IngredientRepository _instance = null;

    @NonNull
    private final IIngredientAPI _IngredientAPI;

    private IngredientRepository(@NonNull IIngredientAPI ingredientAPI) {
        this._IngredientAPI = ingredientAPI;
    }

    public static IngredientRepository getInstance(@NonNull IIngredientAPI ingredientAPI) {
        if (_instance == null) {
            _instance = new IngredientRepository(ingredientAPI);
        }
        return _instance;
    }

    public Observable<List<Ingredient>> getIngredient(long idBurger) {
        return _IngredientAPI.getIngredient(idBurger);
    }

    public Observable<List<Ingredient>> getIngredient() {
        return _IngredientAPI.getIngredient();
    }
}

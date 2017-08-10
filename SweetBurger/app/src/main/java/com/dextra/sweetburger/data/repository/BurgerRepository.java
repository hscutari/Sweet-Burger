package com.dextra.sweetburger.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dextra.sweetburger.data.BaseCalculation;
import com.dextra.sweetburger.data.remote.IBurgerAPI;
import com.dextra.sweetburger.model.Burger;

import java.util.List;

import rx.Observable;

/**
 * Created by henriquescutari on 8/7/17.
 */

public class BurgerRepository extends BaseCalculation {

    @Nullable
    private static BurgerRepository _instanceBurger = null;

    @NonNull
    private final IBurgerAPI _burgerAPI;

    private BurgerRepository(@NonNull IBurgerAPI burgerAPI) {
        this._burgerAPI = burgerAPI;
    }

    public static BurgerRepository getInstance(@NonNull IBurgerAPI burgerAPI) {
        if (_instanceBurger == null) {
            _instanceBurger = new BurgerRepository(burgerAPI);
        }
        return _instanceBurger;
    }

    public Observable<List<Burger>> getBurger() {
        return _burgerAPI.getBurger();
    }

    public Observable<Burger> getBurger(long idBurger) {
        return _burgerAPI.getBurger(idBurger);
    }

}

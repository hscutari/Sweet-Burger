package com.dextra.sweetburger.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dextra.sweetburger.data.BaseCalculation;
import com.dextra.sweetburger.data.remote.IOrderAPI;
import com.dextra.sweetburger.model.Extras;
import com.dextra.sweetburger.model.Order;

import java.util.List;

import rx.Observable;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class OrderRepository extends BaseCalculation {
    @Nullable
    private static OrderRepository _instance = null;

    @NonNull
    private final IOrderAPI _orderAPI;

    private OrderRepository(@NonNull IOrderAPI orderAPI) {
        this._orderAPI = orderAPI;
    }

    public static OrderRepository getInstance(@NonNull IOrderAPI orderAPI) {
        if (_instance == null) {
            _instance = new OrderRepository(orderAPI);
        }
        return _instance;
    }

    public Observable<List<Order>> getOrder() {
        return _orderAPI.getOrder();
    }

    public Observable<Order> setOrder(long idBurger){ return _orderAPI.setOrder(idBurger); }

    public Observable<Order> setOrder(long idBurger, Extras extras){
        return _orderAPI.setOrder(idBurger, extras);
    }
}

package com.dextra.sweetburger.data.remote;

import com.dextra.sweetburger.model.Extras;
import com.dextra.sweetburger.model.Order;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by henriquescutari on 8/8/17.
 */

public interface IOrderAPI {

    @GET("api/pedido")
    Observable<List<Order>> getOrder();

    @PUT("api/pedido/{idBurger}")
    Observable<Order> setOrder(@Path("idBurger")long idBurger);

    @PUT("api/pedido/{idBurger}")
    Observable<Order> setOrder(@Path("idBurger")long idBurger, @Body Extras extras);
}

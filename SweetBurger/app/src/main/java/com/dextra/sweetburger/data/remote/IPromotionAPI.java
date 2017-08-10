package com.dextra.sweetburger.data.remote;

import com.dextra.sweetburger.model.Promotion;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by henriquescutari on 8/8/17.
 */

public interface IPromotionAPI {

    @GET("api/promocao")
    Observable<List<Promotion>> getPromotion();
}

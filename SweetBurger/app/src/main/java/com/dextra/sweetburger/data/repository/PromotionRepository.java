package com.dextra.sweetburger.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dextra.sweetburger.data.remote.IPromotionAPI;
import com.dextra.sweetburger.model.Promotion;

import java.util.List;

import rx.Observable;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class PromotionRepository {
    @Nullable
    private static PromotionRepository _instance = null;

    @NonNull
    private final IPromotionAPI _promotionAPI;

    private PromotionRepository(@NonNull IPromotionAPI promotionAPI) {
        this._promotionAPI = promotionAPI;
    }

    public static PromotionRepository getInstance(@NonNull IPromotionAPI promotionAPI) {
        if (_instance == null) {
            _instance = new PromotionRepository(promotionAPI);
        }
        return _instance;
    }

    public Observable<List<Promotion>> getPromotion() {
        return _promotionAPI.getPromotion();
    }
}

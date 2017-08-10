package com.dextra.sweetburger;

import android.content.Context;

import com.dextra.sweetburger.data.repository.IngredientRepository;
import com.dextra.sweetburger.data.repository.BurgerRepository;
import com.dextra.sweetburger.data.repository.OrderRepository;
import com.dextra.sweetburger.data.repository.PromotionRepository;
import com.dextra.sweetburger.data.remote.SnackClient;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by henriquescutari on 8/7/17.
 */

public class Injection {
    public static BurgerRepository provideTasksBurger(@NonNull Context context) {
        checkNotNull(context);
        return BurgerRepository.getInstance(SnackClient.getBurgerData(context));
    }

    public static IngredientRepository provideTasksIngredient(@NonNull Context context) {
        checkNotNull(context);
        return IngredientRepository.getInstance(SnackClient.getIngredient(context));
    }

    public static OrderRepository provideTasksOrder(@NonNull Context context) {
        checkNotNull(context);
        return OrderRepository.getInstance(SnackClient.getOrder(context));
    }

    public static PromotionRepository provideTasksPromotion(@NonNull Context context) {
        checkNotNull(context);
        return PromotionRepository.getInstance(SnackClient.getPromotion(context));
    }
}

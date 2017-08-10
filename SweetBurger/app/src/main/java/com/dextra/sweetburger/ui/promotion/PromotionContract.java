package com.dextra.sweetburger.ui.promotion;

import com.dextra.sweetburger.model.Promotion;

import java.util.List;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class PromotionContract {
    public interface View{
        void showPromotions(List<Promotion> promotions);
        void showMessage(int message);
    }

    public interface Presenter {
        List<Promotion> getPromotion();
    }
}

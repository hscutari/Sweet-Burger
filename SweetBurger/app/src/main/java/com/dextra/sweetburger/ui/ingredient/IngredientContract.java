package com.dextra.sweetburger.ui.ingredient;

import com.dextra.sweetburger.model.Ingredient;
import com.dextra.sweetburger.ui.IBasePresenter;

import java.util.List;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class IngredientContract {
    public interface View{
        void showIngredients(List<Ingredient> ingredients);
        void showMessage(int message);
    }

    public interface Presenter extends IBasePresenter{

        void getIngredient();
    }
}

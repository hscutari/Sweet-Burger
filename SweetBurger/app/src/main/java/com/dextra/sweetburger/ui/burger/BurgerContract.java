package com.dextra.sweetburger.ui.burger;

import com.dextra.sweetburger.model.Burger;
import com.dextra.sweetburger.model.Extras;
import com.dextra.sweetburger.ui.IBasePresenter;

import java.util.List;

/**
 * Created by henriquescutari on 8/7/17.
 */

public class BurgerContract {

    public interface View{
        void showBurgers(List<Burger> burgers);

        void showMessage(int idMessage);

        void openIngredient(Burger burger);

        void updateSelectedItem(Burger burger);
    }

    public interface Presenter extends IBasePresenter {

        void getBurger();

        void setOrderWithExtras(Burger burger);

        void setOrder(long idBurger);

        void openIngredient(Burger burger);

        void updateBurger(Burger burger);
    }
}



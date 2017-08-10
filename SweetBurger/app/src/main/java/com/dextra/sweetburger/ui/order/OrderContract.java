package com.dextra.sweetburger.ui.order;

import com.dextra.sweetburger.model.Order;
import com.dextra.sweetburger.ui.IBasePresenter;

import java.util.List;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class OrderContract {
    public interface View{
        void showBurgersOrder(List<Order> Orders);
        void showMessage(int message);
    }

    public interface Presenter extends IBasePresenter {

        void getOrder();
    }
}

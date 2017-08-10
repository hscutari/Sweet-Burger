package com.dextra.sweetburger.ui.order;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dextra.sweetburger.R;
import com.dextra.sweetburger.data.repository.BurgerRepository;
import com.dextra.sweetburger.data.repository.IngredientRepository;
import com.dextra.sweetburger.data.repository.OrderRepository;
import com.dextra.sweetburger.model.Burger;
import com.dextra.sweetburger.model.Ingredient;
import com.dextra.sweetburger.model.Order;
import com.dextra.sweetburger.utilities.IBaseSchedulerProvider;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

import static android.content.ContentValues.TAG;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class OrderPresenter implements OrderContract.Presenter {

    @NonNull
    private OrderRepository _orderRepository;

    @NonNull
    private BurgerRepository _burgerRepository;

    @NonNull
    private IngredientRepository _IngredientsRepository;

    @NonNull
    private final CompositeSubscription _subscriptions;

    @NonNull
    private final OrderContract.View _view;

    @NonNull
    private final IBaseSchedulerProvider _schedulerProvider;

    public OrderPresenter(@NonNull OrderRepository order,
                          @NonNull BurgerRepository burger,
                          @NonNull IngredientRepository ingredient,
                          @NonNull OrderContract.View view,
                          @NonNull IBaseSchedulerProvider schedulerProvider) {

        this._orderRepository = order;
        this._burgerRepository = burger;
        this._IngredientsRepository = ingredient;
        this._view = view;
        this._subscriptions = new CompositeSubscription();
        this._schedulerProvider = schedulerProvider;

    }

    @Override
    public void getOrder() {
        Observable<List<Order>> orders = _orderRepository.getOrder();

        Subscription subscription = orders
            .flatMap(new Func1<List<Order>, Observable<Order>>() {
                @Override
                public Observable<Order> call(List<Order> strOrders) {
                    return Observable.from(strOrders);
                }
            })
            .flatMap(new Func1<Order, Observable<Burger>>() {
                @Override
                public Observable<Burger> call(Order order) {
                    return _burgerRepository.getBurger(order.idBurger);
                }
            }, new Func2<Order, Burger, Order>() {
                @Override
                public Order call(Order order, Burger burgers) {
                    order.burger = burgers;
                    return order;
                }
            })
            .flatMap(new Func1<Order, Observable<List<Ingredient>>>() {
                @Override
                public Observable<List<Ingredient>> call(Order order) {
                    return _IngredientsRepository.getIngredient(order.idBurger);
                }
            }, new Func2<Order, List<Ingredient>, Order>() {
                @Override
                public Order call(Order order, List<Ingredient> ingredients) {
                    order.burger.ingredientsDetail = ingredients;
                    order.value = _burgerRepository.getIngredientsPrice(order.burger.ingredientsDetail);
                    return order;
                }
            })
            .flatMap(new Func1<Order, Observable<List<Ingredient>>>() {
                @Override
                public Observable<List<Ingredient>> call(Order order) {
                    return _IngredientsRepository.getIngredient();
                }
            }, new Func2<Order, List<Ingredient>, Order>() {
                @Override
                public Order call(Order order, List<Ingredient> ingredients) {
                    order.burger.allIngredientsDetail = ingredients;
                    return order;
                }
            })
            .toList()
            .subscribeOn(_schedulerProvider.io())
            .observeOn(_schedulerProvider.ui())
            .subscribe(new Subscriber<List<Order>>() {
                @Override
                public void onCompleted() {
                    //TODO:IMPLEMENTAR O METODO
                }

                @Override
                public void onError(Throwable e) {
                    //TODO:TRATAR MELHOR O ERRO
                    Log.e(TAG, e.getMessage());
                    _view.showMessage(R.string.msg_erro);
                }

                @Override
                public void onNext(List<Order> Orders) {
                    if (Orders != null && Orders.size() > 0) {
                        _view.showBurgersOrder(Orders);
                    } else {
                        _view.showMessage(R.string.msg_erro);
                    }
                }
            });

        _subscriptions.add(subscription);
    }

    @Override
    public void unsubscribe() {
        this._subscriptions.clear();
    }
}


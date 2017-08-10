package com.dextra.sweetburger.ui.burger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dextra.sweetburger.R;
import com.dextra.sweetburger.data.repository.BurgerRepository;
import com.dextra.sweetburger.data.repository.IngredientRepository;
import com.dextra.sweetburger.data.repository.OrderRepository;
import com.dextra.sweetburger.model.Burger;
import com.dextra.sweetburger.model.Extras;
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
 *
 * Created by henriquescutari on 8/7/17.
 */

public class BurgerPresenter implements BurgerContract.Presenter {

    @NonNull
    private BurgerRepository _burgerRepository;

    @NonNull
    private OrderRepository _OrderRepository;

    @NonNull
    private IngredientRepository _IngredientsRepository;

    @NonNull
    private final CompositeSubscription _subscriptions;

    @NonNull
    private final BurgerContract.View _view;

    @NonNull
    private final IBaseSchedulerProvider _schedulerProvider;

    public BurgerPresenter(@NonNull BurgerRepository burger,
                           @NonNull OrderRepository order,
                           @NonNull IngredientRepository ingredient,
                           @NonNull BurgerContract.View view,
                           @NonNull IBaseSchedulerProvider schedulerProvider) {

        this._burgerRepository = burger;
        this._OrderRepository = order;
        this._IngredientsRepository = ingredient;
        this._view = view;
        this._subscriptions = new CompositeSubscription();
        this._schedulerProvider = schedulerProvider;

    }

    @Override
    public void getBurger() {
        Observable<List<Burger>> burgers = _burgerRepository.getBurger();

        Subscription subscription = burgers
            .flatMap(new Func1<List<Burger>, Observable<Burger>>() {
                @Override
                public Observable<Burger> call(List<Burger> strBurgers) {
                    return Observable.from(strBurgers);
                }
            })
            .flatMap(new Func1<Burger, Observable<List<Ingredient>>>() {
                 @Override
                 public Observable<List<Ingredient>> call(Burger burger) {
                     return _IngredientsRepository.getIngredient(burger.id);
                 }
             }, new Func2<Burger, List<Ingredient>, Burger>() {
                 @Override
                 public Burger call(Burger burger, List<Ingredient> ingredients) {
                     burger.ingredientsDetail = ingredients;
                     burger.value = _burgerRepository.getIngredientsPrice(burger.ingredientsDetail);
                     return burger;
                 }
             })
            .flatMap(new Func1<Burger, Observable<List<Ingredient>>>() {
                @Override
                public Observable<List<Ingredient>> call(Burger burger) {
                    return _IngredientsRepository.getIngredient();
                }
            }, new Func2<Burger, List<Ingredient>, Burger>() {
                @Override
                public Burger call(Burger burger, List<Ingredient> ingredients) {
                    burger.allIngredientsDetail = ingredients;
                    return burger;
                }
            }).toList()
            .subscribeOn(_schedulerProvider.io())
            .observeOn(_schedulerProvider.ui())
            .subscribe(new Subscriber<List<Burger>>() {
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
                public void onNext(List<Burger> burgers) {
                    if (burgers != null && burgers.size() > 0) {
                        _view.showBurgers(burgers);
                    } else {
                        _view.showMessage(R.string.msg_erro);
                    }
                }
            });

        _subscriptions.add(subscription);
    }

    @Override
    public void setOrderWithExtras(Burger burger) {

        Observable<Order> order = _OrderRepository.setOrder(burger.id, burger.getExtras());

        Subscription subscription = order
            .subscribeOn(_schedulerProvider.io())
            .observeOn(_schedulerProvider.ui())
            .subscribe(new Subscriber<Order>() {
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
                public void onNext(Order Order) {
                    if(Order.id > 0){
                        _view.showMessage(R.string.msg_order_add);
                    }
                }
            });

        _subscriptions.add(subscription);
    }

    @Override
    public void setOrder(long idBurger) {

        Observable<Order> order = _OrderRepository.setOrder(idBurger);

        Subscription subscription = order
                .subscribeOn(_schedulerProvider.io())
                .observeOn(_schedulerProvider.ui())
                .subscribe(new Subscriber<Order>() {
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
                    public void onNext(Order Order) {
                        if(Order.id > 0){
                            _view.showMessage(R.string.msg_order_add);
                        }
                    }
                });

        _subscriptions.add(subscription);
    }

    @Override
    public void openIngredient(Burger burger){
        _view.openIngredient(burger);
    }

    @Override
    public void unsubscribe() {
        this._subscriptions.clear();
    }

    public void updateBurger(Burger burger){
        burger.value = _burgerRepository.getExtraIngredientsPrice(burger.allIngredientsDetail, burger.getAllIngredients());

        if(burger.extraIngredients.size() > 0 && !burger.name.contains("do seu jeito"))
            burger.name = String.format("%s - do seu jeito", burger.name);

        _view.updateSelectedItem(burger);
    }
}

package com.dextra.sweetburger.ui.ingredient;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dextra.sweetburger.R;
import com.dextra.sweetburger.data.repository.IngredientRepository;
import com.dextra.sweetburger.model.Ingredient;
import com.dextra.sweetburger.utilities.IBaseSchedulerProvider;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static android.content.ContentValues.TAG;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class IngredientPresenter implements IngredientContract.Presenter {

    @NonNull
    private IngredientRepository _ingredientRepository;

    @NonNull
    private final CompositeSubscription _subscriptions;

    @NonNull
    private final IngredientContract.View _view;

    @NonNull
    private final IBaseSchedulerProvider _schedulerProvider;

    public IngredientPresenter(@NonNull IngredientRepository ingerdient,
                           @NonNull IngredientContract.View view,
                           @NonNull IBaseSchedulerProvider schedulerProvider) {

        this._ingredientRepository = ingerdient;
        this._view = view;
        this._subscriptions = new CompositeSubscription();
        this._schedulerProvider = schedulerProvider;

    }

    @Override
    public void getIngredient() {
        Observable<List<Ingredient>> ingredient = _ingredientRepository.getIngredient();

        Subscription subscription = ingredient
            .subscribeOn(_schedulerProvider.io())
            .observeOn(_schedulerProvider.ui())
            .subscribe(new Subscriber<List<Ingredient>>() {
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
                public void onNext(List<Ingredient> ingredients) {
                    if (ingredients != null && ingredients.size() > 0) {
                        _view.showIngredients(ingredients);
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


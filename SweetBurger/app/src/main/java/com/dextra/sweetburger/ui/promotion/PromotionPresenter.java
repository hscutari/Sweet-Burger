package com.dextra.sweetburger.ui.promotion;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dextra.sweetburger.R;
import com.dextra.sweetburger.data.repository.PromotionRepository;
import com.dextra.sweetburger.model.Promotion;
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

public class PromotionPresenter implements PromotionContract.Presenter{

    @NonNull
    private PromotionRepository _promotionData;

    @NonNull
    private final CompositeSubscription _subscriptions;

    @NonNull
    private final PromotionContract.View _view;

    @NonNull
    private final IBaseSchedulerProvider _schedulerProvider;

    public PromotionPresenter(@NonNull PromotionRepository promotion,
                          @NonNull PromotionContract.View view,
                          @NonNull IBaseSchedulerProvider schedulerProvider) {

        this._promotionData = promotion;
        this._view = view;
        this._subscriptions = new CompositeSubscription();
        this._schedulerProvider = schedulerProvider;

    }

    @Override
    public List<Promotion> getPromotion() {
        final Observable<List<Promotion>> promotions = _promotionData.getPromotion();

        Subscription subscription = promotions
                .subscribeOn(_schedulerProvider.io())
                .observeOn(_schedulerProvider.ui())
                .subscribe(new Subscriber<List<Promotion>>() {
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
                    public void onNext(List<Promotion> Promotions) {
                        if (Promotions != null && Promotions.size() > 0) {
                            _view.showPromotions(Promotions);
                        } else {
                            _view.showMessage(R.string.msg_erro);
                        }
                    }
                });

        _subscriptions.add(subscription);

        return null;
    }
}

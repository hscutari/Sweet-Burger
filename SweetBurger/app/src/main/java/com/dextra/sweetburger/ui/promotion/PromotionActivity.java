package com.dextra.sweetburger.ui.promotion;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.dextra.sweetburger.Injection;
import com.dextra.sweetburger.R;
import com.dextra.sweetburger.model.Promotion;
import com.dextra.sweetburger.utilities.SchedulerProvider;

import java.util.List;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class PromotionActivity extends AppCompatActivity implements PromotionContract.View {

    private RelativeLayout relativeLayout;
    private PromotionAdapter adapter;
    private PromotionContract.Presenter _presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        setFindViewById();
        initialize();
        setupRecyclerView();
        getData();
    }


    private void setFindViewById(){
        relativeLayout = (RelativeLayout) findViewById(R.id.container);
    }

    private void initialize() {
        _presenter = new PromotionPresenter(Injection.provideTasksPromotion(getApplicationContext()),
                this,
                SchedulerProvider.getInstance());
    }

    private void setupRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PromotionAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        _presenter.getPromotion();
    }

    @Override
    public void showPromotions(List<Promotion> promotions) {
        adapter.addData(promotions);
    }

    @Override
    public void showMessage(int message){
        Snackbar.make(relativeLayout, getResources().getString(message), Snackbar.LENGTH_LONG).show();
    }
}

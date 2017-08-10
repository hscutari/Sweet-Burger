package com.dextra.sweetburger.ui.order;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.dextra.sweetburger.Injection;
import com.dextra.sweetburger.R;
import com.dextra.sweetburger.model.Order;
import com.dextra.sweetburger.utilities.SchedulerProvider;

import java.util.List;

/**
 * Created by henriquescutari on 8/9/17.
 */

public class OrderActivity extends AppCompatActivity implements OrderContract.View {

    private RelativeLayout relativeLayout;
    private OrderAdapter adapter;
    private OrderContract.Presenter _presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setFindViewById();
        initialize();
        setupRecyclerView();
        getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        _presenter.unsubscribe();
    }

    private void setFindViewById(){
        relativeLayout = (RelativeLayout) findViewById(R.id.container);
    }

    private void initialize() {
        _presenter = new OrderPresenter(Injection.provideTasksOrder(getApplicationContext()),
                Injection.provideTasksBurger(getApplicationContext()),
                Injection.provideTasksIngredient(getApplicationContext()),
                this,
                SchedulerProvider.getInstance());
    }

    private void setupRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new OrderAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        _presenter.getOrder();
    }

    @Override
    public void showBurgersOrder(List<Order> Orders) {
        adapter.addData(Orders);
    }

    @Override
    public void showMessage(int message){
        Snackbar.make(relativeLayout, getResources().getString(message), Snackbar.LENGTH_LONG).show();
    }
}

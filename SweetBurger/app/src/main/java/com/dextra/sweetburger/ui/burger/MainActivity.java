package com.dextra.sweetburger.ui.burger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dextra.sweetburger.Injection;
import com.dextra.sweetburger.R;
import com.dextra.sweetburger.model.Burger;
import com.dextra.sweetburger.model.Extras;
import com.dextra.sweetburger.ui.ingredient.IngredientActivity;
import com.dextra.sweetburger.ui.order.OrderActivity;
import com.dextra.sweetburger.ui.promotion.PromotionActivity;
import com.dextra.sweetburger.utilities.SchedulerProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BurgerAdapter.BurgerAdapterListener, BurgerContract.View {

    private static final String EXTRA_BURGER = "burger";
    private static final int EDIT_BURGER = 01;

    private FloatingActionButton _fabCart;
    private AppCompatButton _acbPromotion;
    private BurgerContract.Presenter _presenter;
    private BurgerAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    private Burger selectedBurger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFindViewById();
        setOnclick();
        initialize();
        setupRecyclerView();
        getData();
    }

    private void setFindViewById(){
        _fabCart = (FloatingActionButton) findViewById(R.id.cart_fab);
        _acbPromotion = (AppCompatButton) findViewById(R.id.acbPromotion);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);
    }

    private void setOnclick(){
        final Activity activity = this;
        _acbPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigatePromotion(activity);
            }
        });

        _fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateOrder(activity);
            }
        });
    }

    private void initialize() {
        _presenter = new BurgerPresenter(Injection.provideTasksBurger(getApplicationContext()),
                Injection.provideTasksOrder(getApplicationContext()),
                Injection.provideTasksIngredient(getApplicationContext()),
                this,
                SchedulerProvider.getInstance());
    }

    private void setupRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BurgerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        _presenter.getBurger();
    }

    @Override
    public void showBurgers(List<Burger> burgers) {
        adapter.addData(burgers);
    }

    @Override
    public void showMessage(int idMessage){
        Snackbar.make(coordinatorLayout, getResources().getString(idMessage), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void updateSelectedItem(Burger burger){
        adapter.updateItem(selectedBurger, burger);
    }

    @Override
    public void openIngredient(Burger burger){
        selectedBurger = burger;
        Intent i = new Intent(this, IngredientActivity.class);
        i.putExtra(EXTRA_BURGER, burger);
        startActivityForResult(i, EDIT_BURGER);
    }

    @Override
    public void onClickAddItem(Burger burger) {
        if(burger.extraIngredients != null){
            _presenter.setOrderWithExtras(burger);
        }else {
            _presenter.setOrder(burger.id);
        }
    }

    @Override
    public void onClickListItem(Burger burger) {
        _presenter.openIngredient(burger);
    }

    @Override
    public void onPause() {
        super.onPause();
        _presenter.unsubscribe();
    }

    public static void navigatePromotion(Activity activity) {
        Intent it = new Intent(activity, PromotionActivity.class);
        activity.startActivity(it);
    }

    public static void navigateOrder(Activity activity) {
        Intent it = new Intent(activity, OrderActivity.class);

        activity.startActivity(it);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_BURGER){
            if(data != null) {
                Burger updateBurger = data.getParcelableExtra(EXTRA_BURGER);

                if (updateBurger != null)
                    _presenter.updateBurger(updateBurger);
            }
        }
    }
}

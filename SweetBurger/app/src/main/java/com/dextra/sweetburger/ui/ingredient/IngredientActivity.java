package com.dextra.sweetburger.ui.ingredient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.dextra.sweetburger.Injection;
import com.dextra.sweetburger.R;
import com.dextra.sweetburger.model.Burger;
import com.dextra.sweetburger.model.Ingredient;
import com.dextra.sweetburger.utilities.SchedulerProvider;

import java.util.List;

/**
 * Created by henriquescutari on 8/9/17.
 */

public class IngredientActivity extends AppCompatActivity implements IngredientContract.View, IngredientAdapter.IngredientChangeListener {

    private static final String EXTRA_BURGER = "burger";

    private RelativeLayout relativeLayout;
    private IngredientAdapter adapter;
    private IngredientContract.Presenter _presenter;
    private Burger _burger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        setFindViewById();
        initExtras();
        initialize();
        setupRecyclerView();
        getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        _presenter.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_BURGER, _burger);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFindViewById(){
        relativeLayout = (RelativeLayout) findViewById(R.id.container);
    }

    private void initExtras() {
        _burger = getIntent().getParcelableExtra(EXTRA_BURGER);
    }

    private void initialize() {
        _presenter = new IngredientPresenter(Injection.provideTasksIngredient(getApplicationContext()),
                this,
                SchedulerProvider.getInstance());
    }

    private void setupRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new IngredientAdapter(_burger, this);
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        _presenter.getIngredient();
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        adapter.addData(ingredients);
    }

    @Override
    public void onChange(long idIngredient, long value) {
        _burger.extraIngredients.put(idIngredient, value);
    }

    @Override
    public void onRemove(long idIngredient) {
        _burger.extraIngredients.remove(idIngredient);
        Ingredient ingredient = null;
        for (Ingredient item :_burger.ingredientsDetail) {
            if(item.id == idIngredient){
                ingredient = item;
            }
        }
        if(ingredient != null)
            _burger.ingredientsDetail.remove(ingredient);
    }

    @Override
    public void showMessage(int message){
        Snackbar.make(relativeLayout, getResources().getString(message), Snackbar.LENGTH_LONG).show();
    }
}

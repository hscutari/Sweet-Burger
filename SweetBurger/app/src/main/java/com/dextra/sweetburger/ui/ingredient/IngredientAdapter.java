package com.dextra.sweetburger.ui.ingredient;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dextra.sweetburger.R;
import com.dextra.sweetburger.model.Burger;
import com.dextra.sweetburger.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henriquescutari on 8/9/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    interface IngredientChangeListener {
        void onChange(long idIngredient, long value);
        void onRemove(long idIngredient);
    }

    private final List<Ingredient> dataSet = new ArrayList<>();
    private Burger _burger;
    private IngredientChangeListener _listener;

    IngredientAdapter(Burger burger, IngredientChangeListener listener){
        _burger = burger;
        _listener = listener;
    }

    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IngredientAdapter.ViewHolder holder, int position) {
        holder.populate(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private long unitValue(long idIngredient){

        if(_burger.ingredients.contains(idIngredient)){
            return 1;
        }

        return 0;
    }

    void addData(List<Ingredient> dataSet) {
        replaceData(dataSet);
    }

    private void replaceData(List<Ingredient> dataSet) {
        setList(dataSet);
        notifyDataSetChanged();
    }

    private void setList(List<Ingredient> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final View view;
        private final TextInputEditText tieQuantity;
        private final ImageView avatarView;

        private Ingredient ingredient;

        ViewHolder(View view) {
            super(view);

            this.view = view;
            nameView = view.findViewById(R.id.name);
            tieQuantity = view.findViewById(R.id.tieQuantity);
            avatarView = view.findViewById(R.id.avatar);

            tieQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(_listener != null){
                        if(!charSequence.toString().equals("") && !charSequence.toString().equals("0")) {
                            _listener.onChange(ingredient.id, (Long.parseLong(charSequence.toString())));
                        }else{
                            _listener.onRemove(ingredient.id);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }

        void populate(Ingredient data) {
            ingredient = data;
            nameView.setText(data.name);

            if(_burger != null){
                if(_burger.ingredients.contains(data.id)){
                    tieQuantity.setText(String.valueOf(unitValue(data.id)));
                }
            }

            Glide.with(view.getContext())
                    .load(ingredient.image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.ic_not_found)
                    .into(avatarView);
        }
    }
}

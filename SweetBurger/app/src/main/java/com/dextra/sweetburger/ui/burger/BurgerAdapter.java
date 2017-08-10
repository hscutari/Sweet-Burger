package com.dextra.sweetburger.ui.burger;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dextra.sweetburger.R;
import com.dextra.sweetburger.model.Burger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class BurgerAdapter extends RecyclerView.Adapter<BurgerAdapter.ViewHolder> {

    private final List<Burger> dataSet = new ArrayList<>();

    @NonNull
    private final BurgerAdapterListener listener;

    interface BurgerAdapterListener {
        void onClickListItem(Burger burger);
        void onClickAddItem(Burger burger);
    }

    BurgerAdapter(@NonNull BurgerAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buger_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.populate(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    void addData(List<Burger> dataSet) {
        replaceData(dataSet);
    }

    void updateItem(Burger burger, Burger newBurger){
        int index = dataSet.indexOf(burger);
        this.dataSet.set(index, newBurger);
        notifyItemChanged(index);
    }

    private void replaceData(List<Burger> dataSet) {
        setList(dataSet);
        notifyDataSetChanged();
    }

    private void setList(List<Burger> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView descriptionView;
        private final TextView valueView;
        private final ImageView ivAddCart;
        private final ImageView avatarView;
        private final View view;

        private Burger burger;

        ViewHolder(View view) {
            super(view);

            this.view = view;
            nameView = view.findViewById(R.id.name);
            descriptionView = view.findViewById(R.id.description);
            ivAddCart = view.findViewById(R.id.ivAdd);
            valueView = view.findViewById(R.id.value);
            avatarView = view.findViewById(R.id.avatar);

            ivAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickAddItem(burger);
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickListItem(burger);
                }
            });
        }

        void populate(Burger data) {
            burger = data;
            nameView.setText(data.name);
            descriptionView.setText(data.getIngredientDescription());
            valueView.setText(String.format("R$%1$,.2f", data.value));

            Glide.with(view.getContext())
                    .load(burger.image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.ic_not_found)
                    .into(avatarView);
        }
    }
}

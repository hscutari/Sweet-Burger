package com.dextra.sweetburger.ui.order;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dextra.sweetburger.R;
import com.dextra.sweetburger.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henriquescutari on 8/9/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final List<Order> dataSet = new ArrayList<>();

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);

        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.ViewHolder holder, int position) {
        holder.populate(dataSet.get(position));
    }


    void addData(List<Order> orders) {
        replaceData(orders);
    }

    private void replaceData(List<Order> orders) {
        setList(orders);
        notifyDataSetChanged();
    }

    private void setList(List<Order> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView descriptionView;
        private final TextView numberView;
        private final TextView value;
        private final ImageView avatarView;
        private final View view;

        private Order order;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            nameView = view.findViewById(R.id.name);
            descriptionView = view.findViewById(R.id.description);
            numberView = view.findViewById(R.id.number);
            value = view.findViewById(R.id.value);
            avatarView = view.findViewById(R.id.avatar);
        }

        void populate(Order data) {
            order = data;
            nameView.setText(data.burger.name);

            String extras = data.getExtras();

            if(extras != "")
                descriptionView.setText(String.format("%s \nExtras: %s", data.burger.getIngredientDescription(), data.getExtras()));
            else
                descriptionView.setText(data.burger.getIngredientDescription());

            numberView.setText(String.valueOf(data.id));
            value.setText(String.format("R$%1$,.2f", data.value));

            Glide.with(view.getContext())
                    .load(order.burger.image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.ic_not_found)
                    .into(avatarView);
        }
    }
}

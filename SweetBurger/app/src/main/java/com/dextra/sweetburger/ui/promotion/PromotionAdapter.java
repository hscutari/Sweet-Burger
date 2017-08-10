package com.dextra.sweetburger.ui.promotion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dextra.sweetburger.R;
import com.dextra.sweetburger.model.Promotion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {

private final List<Promotion> dataSet = new ArrayList<>();

    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotion_list_item, parent, false);

        return new PromotionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PromotionAdapter.ViewHolder holder, int position) {
        holder.populate(dataSet.get(position));
    }


    void addData(List<Promotion> dataSet) {
        replaceData(dataSet);
    }

    private void replaceData(List<Promotion> promotions) {
        setList(promotions);
        notifyDataSetChanged();
    }

    private void setList(List<Promotion> dataSet) {
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
        private final View view;

        private Promotion promotion;


        ViewHolder(View view) {
            super(view);
            this.view = view;
            nameView = (TextView) view.findViewById(R.id.name);
            descriptionView = (TextView) view.findViewById(R.id.description);
        }

        void populate(Promotion data) {
            promotion = data;
            nameView.setText(data.name);
            descriptionView.setText(data.description);
        }
    }
}

package com.dextra.sweetburger.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by henriquescutari on 8/7/17.
 */

public class Burger implements Parcelable {

    @SerializedName("id")
    public long id;

    @SerializedName("name")
    public String name;

    @SerializedName("ingredients")
    public ArrayList<Long> ingredients;

    @SerializedName("image")
    public String image;

    public double value;

    public List<Ingredient> ingredientsDetail;

    public List<Ingredient> allIngredientsDetail;

    public Map<Long, Long> extraIngredients = new HashMap<Long, Long>();

    public Map<Long, Long> getAllIngredients(){
        for (Ingredient item : ingredientsDetail) {
            if(!extraIngredients.containsKey(item.id)){
                extraIngredients.put(item.id, 1L);
            }else{
                extraIngredients.put(item.id, extraIngredients.get(item.id));
            }
        }

        return extraIngredients;
    }

    public Extras getExtras(){
        Extras extras = new Extras();
        extras.extras = new Gson().toJson(new ArrayList<>(extraIngredients.keySet()), new TypeToken<List<Long>>(){}.getType());
        return extras;
    }

    public String getIngredientDescription(){
        String split = ", ";
        StringBuilder ingredientDetail = new StringBuilder();

        if(extraIngredients != null) {

            Map<Long, Long> all = getAllIngredients();

            for (Ingredient item : allIngredientsDetail) {
                String newStrig = "";

                if (all.containsKey(item.id)) {
                    if (all.get(item.id) > 1) {
                        newStrig = String.format("%d %s", all.get(item.id), item.name);
                    }

                    if (newStrig == "")
                        ingredientDetail.append(item.name).append(split);
                    else
                        ingredientDetail.append(newStrig).append(split);
                }
            }
        }else{
            for (Ingredient item : ingredientsDetail) {
                    ingredientDetail.append(item.name).append(split);
                }
            }

        return ingredientDetail.toString().substring (0, ingredientDetail.length() - 2);
    }

    protected Burger(Parcel in) {
        final ClassLoader cl = getClass().getClassLoader();

        this.id = in.readLong();
        this.name = in.readString();
        ingredients = new ArrayList<>();
        this.ingredients = in.readArrayList(cl);
        allIngredientsDetail = new ArrayList<>();
        this.allIngredientsDetail = in.readArrayList(cl);
        this.ingredientsDetail = in.readArrayList(cl);
        this.extraIngredients = in.readHashMap(cl);

        if(this.extraIngredients == null){
            this.extraIngredients = new HashMap<Long, Long>();
        }

        this.image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.allIngredientsDetail);
        dest.writeList(this.ingredientsDetail);
        dest.writeMap(this.extraIngredients);
        dest.writeString(this.image);
    }

    public static final Parcelable.Creator<Burger> CREATOR = new Parcelable.Creator<Burger>() {
        @Override
        public Burger createFromParcel(Parcel source) {
            return new Burger(source);
        }

        @Override
        public Burger[] newArray(int size) {
            return new Burger[size];
        }
    };
}

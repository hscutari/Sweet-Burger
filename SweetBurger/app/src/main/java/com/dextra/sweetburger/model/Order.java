package com.dextra.sweetburger.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

/**
 * Created by henriquescutari on 8/8/17.
 */

public class Order {
    @SerializedName("id")
    public long id;

    @SerializedName("id_sandwich")
    public long idBurger;

    @SerializedName("extras")
    public String[] extras;

    @SerializedName("date")
    public long date;

    public double value;

    public Burger burger;

    public String getExtras(){
        List<String> arrExtras =  Arrays.asList(extras);

        String description = "";
        for (Ingredient item: burger.allIngredientsDetail) {
            if(arrExtras.contains(String.valueOf(item.id))){
                description = String.format("%s, %s", description, item.name);
            }
        }

        if(description != "")
            return description.substring(1, description.length());
        else
            return description;
    }
}

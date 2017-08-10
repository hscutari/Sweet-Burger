package com.dextra.sweetburger.data;

import com.dextra.sweetburger.model.Ingredient;

import java.util.List;
import java.util.Map;

import static com.dextra.sweetburger.utilities.Constants.INGREDIENT_BACON;
import static com.dextra.sweetburger.utilities.Constants.INGREDIENT_CHEESE;
import static com.dextra.sweetburger.utilities.Constants.INGREDIENT_LETTUCE;
import static com.dextra.sweetburger.utilities.Constants.INGREDIENT_MEAT;

/**
 * Created by henriquescutari on 8/9/17.
 */

public class BaseCalculation {

    public double getIngredientsPrice(List<Ingredient> ingredients){

        double sum = 0.0;

        for (Ingredient item : ingredients) {
            sum = sum + item.price;
        }

        return sum;
    }

    public double getExtraIngredientsPrice(List<Ingredient> ingredients, Map<Long, Long> burgerIngredients){

        double price = 0;
        Ingredient meat = null;
        int meatProc = 0;
        Ingredient chesse = null;
        int chesseProc = 0;

        for (Ingredient item : ingredients) {

            if(item.id == INGREDIENT_MEAT) {
                meat = item;
                meatProc = applyFood(burgerIngredients, INGREDIENT_MEAT);
            }

            if(item.id == INGREDIENT_CHEESE) {
                chesse = item;
                chesseProc = applyFood(burgerIngredients, INGREDIENT_CHEESE);
            }

            if(burgerIngredients.containsKey(item.id)){
                long totalItens = burgerIngredients.get(item.id);

                if(totalItens == 0)
                    totalItens = 1;

                double totalItemPrice = totalItens * item.price;

                price = price + totalItemPrice;
            }
        }

        if(applyLight(burgerIngredients)){
            price = (price - (price / 100));
        }

        if(meatProc > 0){
            price = (price - (meat.price * meatProc));
        }

        if(chesseProc > 0){
            price = (price - (chesse.price * chesseProc));
        }

        return price;
    }

    private boolean applyLight(Map<Long, Long> allIngredients){
        boolean lettuce = allIngredients.containsKey(INGREDIENT_LETTUCE);
        boolean baccon = !allIngredients.containsKey(INGREDIENT_BACON);

        return lettuce == true && baccon == true;
    }

    private int applyFood(Map<Long, Long> allIngredients, long foodType){
        int desconut = 0;

        if(allIngredients.containsKey(foodType)){
            long quantity = allIngredients.get(foodType);
            desconut = (int)(quantity / 3);
        }

        return desconut;
    }
}

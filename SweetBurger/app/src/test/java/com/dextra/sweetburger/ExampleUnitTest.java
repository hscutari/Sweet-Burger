package com.dextra.sweetburger;

import android.content.Context;

import com.dextra.sweetburger.data.repository.BurgerRepository;
import com.dextra.sweetburger.data.repository.IngredientRepository;
import com.dextra.sweetburger.model.Ingredient;

import org.junit.Test;

import java.util.List;

import rx.Observable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}
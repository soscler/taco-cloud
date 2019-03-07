package com.chrisssanti.tacocloud.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public class IngredientTest {

    @Mock
    private Ingredient ingredient;

    @Test
    public void testIngredient(){
        ingredient = new Ingredient("FLV", "Flavour", Ingredient.Type.SAUCE);
        assert ingredient.getName().equals("Flavour");
    }


}

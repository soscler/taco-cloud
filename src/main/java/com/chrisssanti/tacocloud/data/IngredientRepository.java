package com.chrisssanti.tacocloud.data;

import com.chrisssanti.tacocloud.model.Ingredient;

import java.util.List;

public interface IngredientRepository {

    Ingredient findOne(String id);
    Iterable<Ingredient> findAll();
    Ingredient save(Ingredient ingredient);
}

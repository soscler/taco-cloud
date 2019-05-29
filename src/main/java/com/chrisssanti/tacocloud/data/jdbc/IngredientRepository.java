package com.chrisssanti.tacocloud.data.jdbc;

import com.chrisssanti.tacocloud.model.Ingredient;

public interface IngredientRepository {

    Ingredient findOne(String id);
    Iterable<Ingredient> findAll();
    Ingredient save(Ingredient ingredient);
}

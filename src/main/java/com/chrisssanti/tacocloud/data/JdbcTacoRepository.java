package com.chrisssanti.tacocloud.data;

import com.chrisssanti.tacocloud.model.Taco;

public class JdbcTacoRepository implements TacoRepository{
    @Override
    public Taco save(Taco taco) {

        return taco;
    }
}

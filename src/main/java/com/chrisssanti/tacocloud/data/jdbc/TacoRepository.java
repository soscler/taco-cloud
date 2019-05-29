package com.chrisssanti.tacocloud.data.jdbc;

import com.chrisssanti.tacocloud.model.Taco;

public interface TacoRepository {
    Taco save(Taco taco);
}

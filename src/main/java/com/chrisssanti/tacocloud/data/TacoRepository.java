package com.chrisssanti.tacocloud.data;

import com.chrisssanti.tacocloud.model.Taco;

public interface TacoRepository {
    Taco save(Taco taco);
}

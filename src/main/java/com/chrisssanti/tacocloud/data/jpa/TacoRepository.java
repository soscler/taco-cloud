package com.chrisssanti.tacocloud.data.jpa;

import com.chrisssanti.tacocloud.model.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}

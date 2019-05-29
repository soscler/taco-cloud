package com.chrisssanti.tacocloud.data.jpa;

import com.chrisssanti.tacocloud.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}

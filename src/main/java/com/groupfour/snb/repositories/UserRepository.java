package com.groupfour.snb.repositories;

import com.groupfour.snb.models.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}

package com.bachelor.externalauth.service.userService;

import com.bachelor.externalauth.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserData, String> {
    UserData findByUsernameAndPassword(String username, String password);
}

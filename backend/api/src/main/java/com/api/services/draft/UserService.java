package com.api.services.draft;

import com.shared.db.entities.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(String id);

    void synchronizeUser(String userId, String email, String firstName, String lastName);

}

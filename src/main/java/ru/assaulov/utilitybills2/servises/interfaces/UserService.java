package ru.assaulov.utilitybills2.servises.interfaces;

import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(RegistrationRequest request);

    Optional<User> findUserById(long userId);

    Boolean update(User user);

    List<User> findAllUsers();

    Boolean deleteUserById(long userId);

}

package ru.assaulov.utilitybills2.servises.interfaces;

import org.springframework.http.ResponseEntity;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;

import java.util.List;

public interface UserService {
	 ResponseEntity<?> saveUser(RegistrationRequest userToSave);
	 User findUserById(long userId);
	 List<User> findFllUsers();
	 void deleteUser(long userId);
}

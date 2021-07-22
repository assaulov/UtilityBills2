package ru.assaulov.utilitybills2.servises.interfaces;

import ru.assaulov.utilitybills2.model.User;

import java.util.List;

public interface UserService {
	 User saveUser(User userToSave);
	 User findUserById(long userId);
	 List<User> findFllUsers();
	 void deleteUser(long userId);
}

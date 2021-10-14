package ru.assaulov.utilitybills2.servises.implimentations;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.assaulov.utilitybills2.exeptions.BaseException;
import ru.assaulov.utilitybills2.exeptions.ErrorType;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.model.enums.Gender;
import ru.assaulov.utilitybills2.model.enums.Role;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;
import ru.assaulov.utilitybills2.repositories.UserRepository;
import ru.assaulov.utilitybills2.servises.interfaces.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService, UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("Search user by username " + username);

		return userRepository.findByLoginIgnoreCase(username);
	}

	@Override
	public User save(RegistrationRequest request) {
		LOGGER.info("Try save user in database");
		User userToSave = new User().toBuilder()
				.login(request.getLogin())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.password(bCryptPasswordEncoder.encode(request.getPassword()))
				.gender(Gender.valueOf(genderNotNull(request.getGender())))
				.email(request.getEmail())
				.roles(Collections.singleton(Role.ROLE_USER)).build();
		userRepository.save(userToSave);
		LOGGER.info(userToSave+ "save in database");
		LOGGER.info("Registration successful");
		return userToSave;
	}

	@Override
	public Optional<User> findUserById(long userId) {
		LOGGER.info("Search user by ID " + userId);
		return userRepository.findById(userId);
	}

	@Override
	public Boolean update(User user) {
		LOGGER.info("Try to update user " + user);
		return userRepository.findById(user.getUserId()).map(u -> {
			u.setFirstName(user.getFirstName());
			u.setLastName(user.getLastName());
			u.setEmail(user.getEmail());
			u.setGender(user.getGender());
			u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			LOGGER.info("User fields was updated");
			return true;
		}).orElse(false);
	}

	@Override
	public List<User> findAllUsers() {
		LOGGER.info("Try find all users");

		return userRepository.findAll();
	}

	@Override
	public Boolean deleteUserById(long userId) {
		LOGGER.info("Try to delete user by ID "+ userId);
		User userFromDb = findUserById(userId).orElseThrow(()-> new BaseException(String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), userId)));
		LOGGER.info("User with ID "+ userId + ": " + userFromDb.toString());
		userRepository.delete(userFromDb);
		LOGGER.info("User delete successful");
		return true;
	}

	private String genderNotNull(String gender){
		String none= "NONE";
		if(gender==null){
			return none;
		}
		return gender;

	}
}

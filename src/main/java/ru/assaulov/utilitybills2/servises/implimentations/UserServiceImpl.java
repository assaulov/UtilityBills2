package ru.assaulov.utilitybills2.servises.implimentations;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.model.enums.Gender;
import ru.assaulov.utilitybills2.model.enums.Role;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;
import ru.assaulov.utilitybills2.payload.respose.MessageResponse;
import ru.assaulov.utilitybills2.repositories.UserRepository;
import ru.assaulov.utilitybills2.servises.interfaces.UserService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByLoginIgnoreCase(username);
	}

	@Override
	public ResponseEntity<?> saveUser(RegistrationRequest request) {
		User userInDb = userRepository.findByLoginIgnoreCase(request.getLogin());
		if(userInDb!= null){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		User userToSave = new User().toBuilder()
				.login(request.getLogin())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.password(bCryptPasswordEncoder.encode(request.getPassword()))
				.gender(Gender.valueOf(request.getGender()))
				.email(request.getEmail())
				.roles(Collections.singleton(Role.ROLE_USER)).build();
		userRepository.save(userToSave);
		LOGGER.info(userToSave+ "save in database");
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@Override
	public User findUserById(long userId) {
		User user = userRepository.getById(userId);
		return user!=null ? user : null;
	}

	@Override
	public List<User> findFllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(long userId) {
		User user = userRepository.getById(userId);
		if(user!=null){
			userRepository.deleteById(userId);
		}
	}
}

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
		return userRepository.findByLoginIgnoreCase(username);
	}

	@Override
	public Optional<User> save(RegistrationRequest request) {
		User userToSave = new User().toBuilder()
				.login(request.getLogin())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.password(bCryptPasswordEncoder.encode(request.getPassword()))
				.gender(Gender.valueOf(genderNotNull(request.getGender())))
				.email(request.getEmail())
				.roles(Collections.singleton(Role.ROLE_USER)).build();
		return Optional.of(userRepository.save(userToSave));
	}

	@Override
	public Optional<User> findUserById(long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public Optional<User> update(User user) {
		return Optional.of(userRepository.save(user));
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Boolean deleteUser(long userId) {
		User userFromDb = findUserById(userId).orElseThrow(()-> new BaseException(String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), userId)));
		userRepository.delete(userFromDb);
		return userRepository.findById(userFromDb.getUserId()).isEmpty();
	}

	private String genderNotNull(String gender){
		String none= "NONE";
		if(gender==null){
			return none;
		}
		return gender;

	}
}

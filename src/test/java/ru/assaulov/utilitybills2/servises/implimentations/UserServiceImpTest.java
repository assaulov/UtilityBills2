package ru.assaulov.utilitybills2.servises.implimentations;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.model.enums.Gender;
import ru.assaulov.utilitybills2.model.enums.Role;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;
import ru.assaulov.utilitybills2.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest extends ConfigTest{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpTest.class);

	@MockBean
	private UserRepository userRepository;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@InjectMocks
	@Autowired
	private UserServiceImp userService;

	private final RegistrationRequest request = createRequest();
	private final User user = createTestUser(request);

	@Test
	public void testSaveUser() {

		User savedUser = userService.save(request);

		assertEquals(request.getLogin(), savedUser.getLogin());
		assertEquals(request.getFirstName(), savedUser.getFirstName());
		assertEquals(request.getLastName(), savedUser.getLastName());
		assertEquals(request.getGender(), savedUser.getGender().toString());
		assertEquals(request.getEmail(), savedUser.getEmail());
		assertFalse(savedUser.getPassword().isEmpty());
		assertTrue(savedUser.getRoles().contains(Role.ROLE_USER));

	}

	@Test
	public void testFindUserByLogin() {
		given(userRepository.findByLoginIgnoreCase(any(String.class))).willReturn(user);
		UserDetails userDetails = userService.loadUserByUsername(request.getLogin());
		assertEquals(request.getLastName() + " " + request.getFirstName(), userDetails.getUsername());
	}

	@Test
	public void testFindUserById() {
		user.setUserId(15L);
		given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
		Optional<User> userFromDb = userService.findUserById(15L);
		assertTrue(userFromDb.isPresent());
	}

	@Test
	public void  testUpdateUser(){
		User fieldsUpdate = new User().toBuilder()
				.email("novii@mail.ru")
				.firstName("Новое Имя")
				.lastName("Новая Фамилия")
				.gender(Gender.FEMALE)
				.password("новый_пароль")
				.build();
		user.setUserId(15L);
		fieldsUpdate.setUserId(15L);
		given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
		userService.update(fieldsUpdate);
		User updatedUser = userService.findUserById(user.getUserId()).get();
		assertEquals(user.getEmail(), updatedUser.getEmail());
		assertEquals(user.getFirstName(), updatedUser.getFirstName());
		assertEquals(user.getLastName(), updatedUser.getLastName());
		assertEquals(user.getGender(), updatedUser.getGender());
		assertNotEquals(bCryptPasswordEncoder.encode(user.getPassword()), updatedUser.getPassword());
	}

	@Test
	public void testFindAllUsers() {
		ArrayList<User> users = new ArrayList<>();
		users.add(user);
		users.add(new User().toBuilder().login("test1").build());
		users.add(new User().toBuilder().login("test2").build());
		users.add(new User().toBuilder().login("test3").build());
		given(userRepository.findAll()).willReturn(users);
		List<User> usersFromDB = userService.findAllUsers();
		assertIterableEquals(users, usersFromDB);

	}

	@Test
	public void testDeleteUser() {
		user.setUserId(15L);
		given(userService.findUserById(user.getUserId())).willReturn(Optional.of(user));
		Boolean isDeleted = userService.deleteUserById(user.getUserId());
		assertTrue(isDeleted);
		verify(userRepository, times(1)).delete(user);
	}

}
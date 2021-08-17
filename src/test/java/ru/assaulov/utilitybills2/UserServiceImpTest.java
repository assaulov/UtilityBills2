package ru.assaulov.utilitybills2;


import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;
import ru.assaulov.utilitybills2.servises.implimentations.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySources({
		@TestPropertySource("classpath:application.properties"),
		@TestPropertySource("classpath:liquibase.properties")})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceImpTest {

	static {
		System.setProperty("spring.datasource.url", System.getenv("DB_URL"));
		System.setProperty("spring.datasource.username", System.getenv("POSTGRES_USER"));
		System.setProperty("spring.datasource.password", System.getenv("POSTGRES_PASSWORD"));
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpTest.class);

	@Autowired
	private UserServiceImpl userService;
	private static long userId;
	private String login;
	private String firstName;
	private String lastName;
	private String password;
	private String gender;
	private String email;

	@BeforeEach
	public void setUp() {
		login = "test1";
		firstName = "Test";
		lastName = "Testov";
		password = "1234";
		gender = "MALE";
		email = "test@mail.ru";
	}

	@Test
	@Order(1)
	public void testSaveUser() {
		RegistrationRequest testUser = new RegistrationRequest();
		testUser.setLogin(login);
		testUser.setFirstName(firstName);
		testUser.setLastName(lastName);
		testUser.setPassword(password);
		testUser.setGender(gender);
		testUser.setEmail(email);
		LOGGER.info(testUser + " created");
		ResponseEntity<?> messageResponse = userService.saveUser(testUser);
		LOGGER.info(messageResponse.toString());
		assertTrue(messageResponse.toString().contains("User registered successfully!"));
	}

	@Test
	@Order(2)
	public void testFindUserByLogin() {
		User userFromDB = userService.findUserByLogin(login);
		LOGGER.info(userFromDB.toString() + " response from DB by user login");
		userId = userFromDB.getUserId();
		assertEquals(login, userFromDB.getLogin());
		assertEquals(firstName, userFromDB.getFirstName());
		assertEquals(lastName, userFromDB.getLastName());
		assertEquals(gender, userFromDB.getGender().toString());
		assertEquals(email, userFromDB.getEmail());
	}

	@Test
	@Order(3)
	public void testFindUserById() {
		LOGGER.info(userId + ": user ID");
		User userFromDB = userService.findUserById(userId);
		LOGGER.info(userFromDB.toString() + " response from DB by user ID");
		assertEquals(login, userFromDB.getLogin());
		assertEquals(firstName, userFromDB.getFirstName());
		assertEquals(lastName, userFromDB.getLastName());
		assertEquals(gender, userFromDB.getGender().toString());
		assertEquals(email, userFromDB.getEmail());
	}

	@Test
	@Order(4)
	public void testFindAllUsers() {
		List<User> usersFromDB = userService.findAllUsers();
		assertFalse(usersFromDB.isEmpty());
	}

	@Test
	@Order(5)
	public void testLoadUserByUsername() {
		UserDetails usersFromDB = userService.loadUserByUsername(login);
		assertTrue(usersFromDB.isEnabled());
	}

	@Test
	@Order(6)
	public void testDeleteUser() {
		User userFromDB = userService.findUserByLogin(login);
		LOGGER.info(userFromDB + " find in DB by Login");
		ResponseEntity<?> messageResponse = userService.deleteUser(userFromDB.getUserId());
		LOGGER.info(messageResponse.toString());
		assertTrue(messageResponse.toString().contains("User deleted successfully!"));
		LOGGER.info(userFromDB + " deleted from DB");
	}

}

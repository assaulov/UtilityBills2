package ru.assaulov.utilitybills2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;

@TestPropertySources({
		@TestPropertySource("classpath:application.properties"),
		@TestPropertySource("classpath:liquibase.properties")})
public class ConfigTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigTest.class);

	static {
		System.setProperty("spring.datasource.url", System.getenv("DB_URL"));
		System.setProperty("spring.datasource.username", System.getenv("POSTGRES_USER"));
		System.setProperty("spring.datasource.password", System.getenv("POSTGRES_PASSWORD"));

	}
	public static long userId;
	public static String login;
	public static String firstName;
	public static String lastName;
	public static String password;
	public static String gender;
	public static String email;


	@BeforeAll
	public static void setUp() {
		login = "test1";
		firstName = "Test";
		lastName = "Testov";
		password = "1234";
		gender = "MALE";
		email = "test@mail.ru";
		LOGGER.info("User field is set up");
	}
	
}

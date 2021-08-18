package ru.assaulov.utilitybills2;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;

@TestPropertySources({
		@TestPropertySource("classpath:application.properties"),
		@TestPropertySource("classpath:liquibase.properties")})
public class ConfigTest {

	static {
		System.setProperty("spring.datasource.url", System.getenv("DB_URL"));
		System.setProperty("spring.datasource.username", System.getenv("POSTGRES_USER"));
		System.setProperty("spring.datasource.password", System.getenv("POSTGRES_PASSWORD"));

	}
	public static long userId;
	public String login;
	public String firstName;
	public String lastName;
	public String password;
	public String gender;
	public String email;


	@BeforeEach
	public void setUp() {
		login = "test1";
		firstName = "Test";
		lastName = "Testov";
		password = "1234";
		gender = "MALE";
		email = "test@mail.ru";
	}
	
}

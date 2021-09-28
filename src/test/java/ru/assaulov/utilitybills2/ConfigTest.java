package ru.assaulov.utilitybills2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;
import ru.assaulov.utilitybills2.repositories.MetersRepository;
import ru.assaulov.utilitybills2.repositories.UserRepository;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Random;

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

	@Autowired
	UserRepository userRepository;
	@Autowired
	MetersRepository metersRepository;

	public static long userId;
	public static String login;
	public static String firstName;
	public static String lastName;
	public static String password;
	public static String gender;
	public static String email;
	public static RegistrationRequest request;

	@BeforeAll
	public static void setUp() {
		login = "test1";
		firstName = "Test";
		lastName = "Testov";
		password = "1234";
		gender = "MALE";
		email = "test@mail.ru";
		LOGGER.info("User field is set up");
		request = new RegistrationRequest(login,password, firstName, lastName, gender, email);

	}

	private static double randomValue(double rangeMin, double rangeMax){
		Random r = new Random();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		return r.doubles(rangeMin,rangeMax)
				.map(x-> {
					x = x*1000;
					x = Math.round(x);
					x= x/1000;
					return x;
				})
				.findFirst()
				.getAsDouble();
	}

	private static int randomValue(int rangeMin, int rangeMax){
		Random r = new Random();
		return r.ints(rangeMin, rangeMax)
				.findFirst()
				.getAsInt();
	}

	public Meters createMeter(boolean isNeedRandomDate, User user){
		Meters meter = new Meters().toBuilder()
				.coldWater(randomValue(0.0, 100.0))
				.hotWater(randomValue(0.0, 100.0))
				.electricity(randomValue(0.0, 5000.0))
				.gas(randomValue(0.0, 1000.0))
				.user(user)
				.build();;
		if(isNeedRandomDate) {
			meter.setMeterDataWrite(LocalDate.now().minusDays(randomValue(0,90)));
		} else {
			meter.setMeterDataWrite(LocalDate.now());
		}
		return meter;
	}
	
}

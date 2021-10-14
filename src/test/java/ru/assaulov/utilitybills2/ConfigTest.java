package ru.assaulov.utilitybills2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.model.enums.Gender;
import ru.assaulov.utilitybills2.model.enums.Role;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;
import ru.assaulov.utilitybills2.repositories.MetersRepository;
import ru.assaulov.utilitybills2.repositories.UserRepository;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Random;

//@TestPropertySources({
//		@TestPropertySource("classpath:application.properties"),
//		@TestPropertySource("classpath:liquibase.properties")})
@ExtendWith(MockitoExtension.class)
public class ConfigTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigTest.class);

//	static {
//		System.setProperty("spring.datasource.url", System.getenv("DB_URL"));
//		System.setProperty("spring.datasource.username", System.getenv("POSTGRES_USER"));
//		System.setProperty("spring.datasource.password", System.getenv("POSTGRES_PASSWORD"));
//
//	}


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

	// TODO : Сделать генератор рандомных данных
	public RegistrationRequest createRequest(){
		return new RegistrationRequest().toBuilder()
				.login("testUser")
				.firstName("Test")
				.lastName("Testov")
				.email("test_user@mail.ru")
				.gender("MALE")
				.password("qwerty1234")
				.build();
	}

	public User createTestUser(RegistrationRequest request){
		return new User().toBuilder()
				.login(request.getLogin())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.password(request.getPassword())
				.gender(Gender.valueOf(request.getGender()))
				.email(request.getEmail())
				.roles(Collections.singleton(Role.ROLE_USER)).build();
	}
	
}

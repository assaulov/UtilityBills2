package ru.assaulov.utilitybills2.servises.implimentations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.model.enums.Gender;
import ru.assaulov.utilitybills2.model.enums.Role;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Random;

//TODO: Переделать в утилитарный класс
public class TestUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestUtils.class);

	public static Meters createMeter(MetersRequest request, User user) {
		Meters meter = new Meters().toBuilder()
				.coldWater(request.getColdWater())
				.hotWater(request.getHotWater())
				.electricity(request.getElectricity())
				.gas(request.getGas())
				.meterDataWrite(request.getMeterDataWrite())
				.user(user)
				.build();
		if (request.getMeterId() != null) {
			meter.setMeterId(request.getMeterId());
		}
		LOGGER.info(meter + "was created");
		return meter;
	}

	public static MetersRequest createMetersRequest(String userLogin) {
		return new MetersRequest().toBuilder()
				.coldWater(randomValue(0.0, 100.0))
				.hotWater(randomValue(0.0, 100.0))
				.electricity(randomValue(0.0, 5000.0))
				.gas(randomValue(0.0, 1000.0))
				.meterDataWrite(randomDate())
				.userLogin(userLogin)
				.build();
	}

	// TODO : Сделать генератор рандомных данных
	public static RegistrationRequest createRequest() {
		return new RegistrationRequest().toBuilder()
				.login("testUser")
				.firstName("Test")
				.lastName("Testov")
				.email("test_user@mail.ru")
				.gender("MALE")
				.password("qwerty1234")
				.build();
	}

	public static User createTestUser(RegistrationRequest request) {
		return new User().toBuilder()
				.login(request.getLogin())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.password(request.getPassword())
				.gender(Gender.valueOf(request.getGender()))
				.email(request.getEmail())
				.roles(Collections.singleton(Role.ROLE_USER)).build();
	}

	private static double randomValue(double rangeMin, double rangeMax) {
		Random r = new Random();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		return r.doubles(rangeMin, rangeMax)
				.map(x -> {
					x = x * 1000;
					x = Math.round(x);
					x = x / 1000;
					return x;
				})
				.findFirst()
				.getAsDouble();
	}

	private static int randomValue(int rangeMin, int rangeMax) {
		Random r = new Random();
		return r.ints(rangeMin, rangeMax)
				.findFirst()
				.getAsInt();
	}

	private static LocalDate randomDate() {
		return LocalDate.now().minus(Period.ofDays((new Random().nextInt(365))));
	}


}
	


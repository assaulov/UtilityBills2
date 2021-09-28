package ru.assaulov.utilitybills2;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;
import ru.assaulov.utilitybills2.servises.implimentations.MetersServiceImp;
import ru.assaulov.utilitybills2.servises.implimentations.UserServiceImp;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MetersServiceImpTest extends ConfigTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetersServiceImpTest.class);


	private final MetersRequest meterRequest = new MetersRequest();
	private final List<Meters> generatedMeters = new ArrayList<>();

	@Autowired
	MetersServiceImp metersService;

	@Autowired
	UserServiceImp userService;

	@BeforeAll
	public void setData(){
		userService.saveUser(request);
		User user = userService.findUserByLogin(login);
		meterRequest.setUserLogin(user.getLogin());
		for (int i = 0; i < 5 ; i++) {
			generatedMeters.add(createMeter(true, user));
		}
		generatedMeters.add(createMeter(false, user));
	}

	@AfterAll
	void clearData(){
		userId = userService.findUserByLogin(login).getUserId();
		userService.deleteUser(userId);
		LOGGER.info("Data for test is cleared");
	}

	@Test
	@Order(1)
	public void testSaveMeter(){
		LOGGER.info("Test to save meters data");
		for (Meters meter: generatedMeters) {
			ResponseEntity<?> messageResponse = metersService.saveMeter(meter);
			LOGGER.info("Response from server: " + messageResponse.toString());
			assertTrue(messageResponse.toString().contains("Meters saved successfully!"));
		}
	}

	@Test
	@Order(2)
	public void testFindAllByUser_UserId(){
		LOGGER.info("Test show meters data by user ID");
		List<Meters> metersFromDB = metersService.findAllByUser_UserId(meterRequest);
		assertIterableEquals(generatedMeters, metersFromDB);
	}

	@Test
	@Order(3)
	public void testFindMetersByPeriod(){
		LOGGER.info("Test show meters data by period");
		MetersRequest metersRequest = new MetersRequest();
		metersRequest.setDateFrom(LocalDate.now().minusDays(90));
		metersRequest.setDateTo(LocalDate.now());
		metersRequest.setUserLogin(login);

		List<Meters> metersFormDb = metersService.findMetersByPeriod(metersRequest);

		assertTrue(metersFormDb.size()>=6,"Колличестов показаний не соответствует ожидаемому");

		for (Meters meter: metersFormDb) {
			assertTrue(meter.getMeterDataWrite().isBefore(LocalDate.now().plusDays(1)), "Показание не входит в данный период (позже)");
			assertTrue(meter.getMeterDataWrite().isAfter(LocalDate.now().minusDays(90)),"Показание не входит в данный период (раньше)");
		}

	}
	@Test
	@Order(4)
	public void testFindMetersByDate(){
		LOGGER.info("Test show meters data by period");
		MetersRequest metersRequest = new MetersRequest();
		LocalDate dateExpected= LocalDate.now();
		metersRequest.setMeterDataWrite(dateExpected);
		metersRequest.setUserLogin(login);
		List<Meters> metersFormDb = metersService.findMetersByDate(metersRequest);
		assertEquals(dateExpected, metersFormDb.get(0).getMeterDataWrite(), "Показания по запрашиваемой дате отсутствуют");

	}

	@Test
	@Order(5)
	public void testDeleteByMeterId(){
		LOGGER.info("Test delete meters data by meter ID");
		List<Meters> meters = metersService.findAllByUser_UserId(meterRequest);
		meterRequest.setMeterId(meters.get(0).getMeterId());
		metersService.deleteMeterById(meterRequest);
	assertThrows(NoSuchElementException.class, () -> {
		LOGGER.info("Meters data with " + meterRequest.getMeterId() + " is not present in DB");
		metersService.findById(meterRequest);
	});
	}

}

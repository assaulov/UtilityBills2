package ru.assaulov.utilitybills2;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;
import ru.assaulov.utilitybills2.payload.request.RegistrationRequest;
import ru.assaulov.utilitybills2.servises.implimentations.MetersServiceImp;
import ru.assaulov.utilitybills2.servises.implimentations.UserServiceImp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MetersServiceImpTest extends ConfigTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetersServiceImpTest.class);

	private Double coldWater = 149.9;
	private Double hotWater = 0.0;
	private Double electricity = 2096.0;
	private Double gas = 209.9;
	private MetersRequest meter;

	@Autowired
	MetersServiceImp metersService;

	@Autowired
	UserServiceImp userService;

	@BeforeAll
	public void setData(){
		RegistrationRequest request = new RegistrationRequest(login,password, firstName, lastName, gender, email);
		userService.saveUser(request);
		meter = new MetersRequest();
		meter.setMeterDataWrite(LocalDate.now());
		meter.setColdWater(coldWater);
		meter.setHotWater(hotWater);
		meter.setGas(gas);
		meter.setElectricity(electricity);
		meter.setUserLogin(login);
		LOGGER.info("Data for test is set");
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
		ResponseEntity<?> messageResponse = metersService.saveMeter(meter);
		LOGGER.info("Response from server: " + messageResponse.toString());
		assertTrue(messageResponse.toString().contains("Meters saved successfully!"));
	}

	@Test
	@Order(2)
	public void testFindAllByUser_UserId(){
		LOGGER.info("Test show meters data by user ID");
		List<Meters> meters = metersService.findAllByUser_UserId(meter);
		meter.setMeterId(meters.get(0).getMeterId());
		LOGGER.info(Arrays.toString(meters.toArray()));
		assertFalse(meters.isEmpty());
		assertEquals(1, meters.size());
	}

	@Test
	@Order(3)
	public void testIsMeterInDBHasCorrectData(){
		LOGGER.info("Test show meters data saved in DB correct");
		Meters meterFromDB = metersService.findById(meter);
		assertEquals(meter.getMeterDataWrite(), meterFromDB.getMeterDataWrite());
		assertEquals(meter.getColdWater(), meterFromDB.getColdWater());
		assertEquals(meter.getHotWater(), meterFromDB.getHotWater());
		assertEquals(meter.getElectricity(), meterFromDB.getElectricity());
		assertEquals(meter.getGas(), meterFromDB.getGas());
		assertEquals(meter.getUserLogin(), meterFromDB.getUser().getLogin());
		LOGGER.info("Test data equals data from DB");

	}
	@Test
	@Order(4)
	public void testDeleteByMeterId(){
		LOGGER.info("Test delete meters data by meter ID");
		metersService.deleteMeterById(meter);
	assertThrows(NoSuchElementException.class, () -> {
		LOGGER.info("Meters data with " + meter.getMeterId() + " is not present in DB");
		metersService.findById(meter);
	});
	}


}

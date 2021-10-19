package ru.assaulov.utilitybills2.servises.implimentations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;
import ru.assaulov.utilitybills2.repositories.MetersRepository;
import ru.assaulov.utilitybills2.repositories.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class MetersServiceImpTest extends ConfigTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetersServiceImpTest.class);
	private final MetersRequest meterRequest = createMetersRequest("test");
	private final List<Meters> generatedMeters = new ArrayList<>();
	private final User testUser = new User().toBuilder().login("test").userId(777L).build();

	@MockBean
	private UserRepository userRepository;
	@MockBean
	private MetersRepository metersRepository;

	@Autowired
	@InjectMocks
	MetersServiceImp metersService;

	@Test
	public void testSaveMeter() {
		LOGGER.info("Test save meters");
		given(userRepository.findByLoginIgnoreCase(any(String.class))).willReturn(testUser);
		Meters meter = metersService.saveMeter(meterRequest);
		assertEquals(meterRequest.getColdWater(), meter.getColdWater(), "");
		assertEquals(meterRequest.getHotWater(), meter.getHotWater());
		assertEquals(meterRequest.getElectricity(), meter.getElectricity());
		assertEquals(meterRequest.getGas(), meter.getGas());
		assertEquals(meterRequest.getColdWater(), meter.getColdWater());
		assertEquals(meterRequest.getMeterDataWrite(), meter.getMeterDataWrite());
	}

	@Test
	public void testFindAllByUser_UserId() {
		LOGGER.info("Test show meters by user ID");
		given(userRepository.findByLoginIgnoreCase(any(String.class))).willReturn(testUser);
		when(metersRepository.findAllByUser_UserId(777L)).thenReturn(generatedMeters);
		createSomeMeters(true,5);
		List<Meters> metersFromDB = metersService.findAllByUser_UserId(meterRequest);
		assertIterableEquals(generatedMeters, metersFromDB);
		generatedMeters.clear();
	}

	@Test
	public void testFindMetersByPeriod() {
		LOGGER.info("Test show meters data by period");
		given(userRepository.findByLoginIgnoreCase(any(String.class))).willReturn(testUser);
		when(metersRepository.findMetersByPeriod(meterRequest.getDateFrom(), meterRequest.getDateTo(), testUser)).thenReturn(generatedMeters);
		meterRequest.setDateFrom(LocalDate.now().minusDays(365));
		meterRequest.setDateTo(LocalDate.now());
		createSomeMeters(true,7);
		List<Meters> metersFormDb = metersService.findMetersByPeriod(meterRequest);
		metersFormDb.sort(Comparator.comparing(Meters::getMeterDataWrite).thenComparing(Meters::getMeterId));
		metersFormDb.forEach(meter -> System.out.println("id: " + meter.getMeterId() + " date: " + meter.getMeterDataWrite().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

		for (Meters meter : metersFormDb) {
			assertTrue(meter.getMeterDataWrite().isBefore(LocalDate.now().plusDays(1)), "Показание не входит в данный период (позже)");
			assertTrue(meter.getMeterDataWrite().isAfter(LocalDate.now().minusDays(365)), "Показание не входит в данный период (раньше)");
		}
		generatedMeters.clear();

	}
	@Test
	public void testFindMetersByDate() {
		LOGGER.info("Test show meters by date");
		given(userRepository.findByLoginIgnoreCase(any(String.class))).willReturn(testUser);
		LocalDate dateExpected = LocalDate.now();
		meterRequest.setMeterDataWrite(dateExpected);
		when(metersRepository.findMetersByDate(dateExpected, testUser)).thenReturn(generatedMeters);
		createSomeMeters(false, 2);
		List<Meters> metersFormDb = metersService.findMetersByDate(meterRequest);
		assertEquals(dateExpected, metersFormDb.get(0).getMeterDataWrite(), "Показания по запрашиваемой дате отсутствуют");
		assertEquals(dateExpected, metersFormDb.get(1).getMeterDataWrite(), "Показания по запрашиваемой дате отсутствуют");
		generatedMeters.clear();
	}

	@Test
	public void testDeleteByMeterId(){
		LOGGER.info("Test delete meters by meters ID");
		meterRequest.setMeterId(111L);
		Meters testMeter = createMeter(meterRequest,testUser);
		given(metersRepository.findById(111L)).willReturn(Optional.of(testMeter));
		Boolean isDeleted = metersService.deleteMeterById(meterRequest);
		assertTrue(isDeleted);
		verify(metersRepository, times(1)).delete(testMeter);
	}

	@Test
	void testUpdateMeterById() {
		LOGGER.info("Test update meters by meters ID");
		meterRequest.setMeterId(555L);
		MetersRequest fieldsToUpdate = new MetersRequest().toBuilder()
				.coldWater(8.56)
				.hotWater(0.0)
				.electricity(653.0)
				.gas(0.0)
				.meterDataWrite(LocalDate.now())
				.userLogin(testUser.getLogin())
				.meterId(555L)
				.build();
		Meters testMeter = createMeter(meterRequest,testUser);
		given(metersRepository.findById(any(Long.class))).willReturn(Optional.of(testMeter));
		Boolean isUpdated = metersService.updateMeterById(fieldsToUpdate);
		assertTrue(isUpdated, "Ошибка обновления полей показаний счетчиков");

	}

	@Test
	void testFindById() {
		LOGGER.info("Test find meters by meters ID");
		meterRequest.setMeterId(404L);
		Meters testMeter = createMeter(meterRequest,testUser);
		when(metersRepository.findById(any(Long.class))).thenReturn(Optional.of(testMeter));
		Optional<Meters> meterFromDB = metersService.findById(meterRequest);
		assertTrue(meterFromDB.isPresent(), "Запись с ID " + meterRequest.getMeterId() + " не существует");
		assertEquals(meterFromDB.get(), testMeter, "Записи не соответствуют");
	}

	private void createSomeMeters(boolean isRandomDateNeed,int count) {
		for (int i = 0; i < count; i++) {
			Meters testMeter = createMeter(createMetersRequest("test"),testUser);
			testMeter.setMeterId(i + 1L);
			if(!isRandomDateNeed){
				testMeter.setMeterDataWrite(LocalDate.now());
			}
			generatedMeters.add(testMeter);
		}
	}
}

package ru.assaulov.utilitybills2.servises.implimentations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.assaulov.utilitybills2.TestConfig;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;
import ru.assaulov.utilitybills2.repositories.MetersRepository;
import ru.assaulov.utilitybills2.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetersServiceImpTest extends TestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetersServiceImpTest.class);
    private final MetersRequest meterRequest = TestUtils.createMetersRequest("test");
    private final List<Meters> generatedMeters = new ArrayList<>();
    private final User testUser = new User().toBuilder().login("test").userId(777L).build();

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MetersRepository metersRepository;

    @Autowired
    @InjectMocks
    private MetersServiceImp metersService;

    @Test
    void testSaveMeter() {
        LOGGER.info("Test save meters");
        given(userRepository.findByLoginIgnoreCase(any(String.class))).willReturn(testUser);
        Meters meter = metersService.saveMeter(meterRequest);
        assertEquals(meterRequest.getColdWater(), meter.getColdWater(), "Данные не соответствуют");
        assertEquals(meterRequest.getHotWater(), meter.getHotWater(), "Данные не соответствуют");
        assertEquals(meterRequest.getElectricity(), meter.getElectricity(), "Данные не соответствуют");
        assertEquals(meterRequest.getGas(), meter.getGas(), "Данные не соответствуют");
        assertEquals(meterRequest.getColdWater(), meter.getColdWater(), "Данные не соответствуют");
        assertEquals(meterRequest.getMeterDataWrite(), meter.getMeterDataWrite(), "Данные не соответствуют");
    }

    @Test
    void testFindAllByUser() {
        LOGGER.info("Test show meters by user ID");
        given(userRepository.findByLoginIgnoreCase(any(String.class))).willReturn(testUser);
        when(metersRepository.findAllByUserUserId(777L)).thenReturn(generatedMeters);
        createSomeMeters(true, 5);
        List<Meters> metersFromDb = metersService.findAllByUser(meterRequest.getUserLogin());
        assertIterableEquals(generatedMeters, metersFromDb, "Данные для пользователя с ID " + testUser.getUserId() + " не найдены");
        generatedMeters.clear();
    }

    @Test
    void testFindMetersByPeriod() {
        LOGGER.info("Test show meters data by period");
        createSomeMeters(true, 7);
        meterRequest.setDateFrom(LocalDate.now().minusDays(365));
        meterRequest.setDateTo(LocalDate.now());
        given(userRepository.findByLoginIgnoreCase(any(String.class))).willReturn(testUser);
        when(metersRepository.findMetersByPeriod(meterRequest.getDateFrom(), meterRequest.getDateTo(), testUser)).thenReturn(generatedMeters);
        List<Meters> metersFormDb = metersService.findMetersByPeriod(meterRequest);
        metersFormDb.sort(Comparator.comparing(Meters::getMeterDataWrite).thenComparing(Meters::getMeterId));
        for (Meters meter : metersFormDb) {
            assertTrue(meter.getMeterDataWrite().isBefore(LocalDate.now().plusDays(1)), "Показание не входит в данный период (позже)");
            assertTrue(meter.getMeterDataWrite().isAfter(LocalDate.now().minusDays(365)), "Показание не входит в данный период (раньше)");
        }
        generatedMeters.clear();

    }

    @Test
    void testFindMetersByDate() {
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
    void testDeleteByMeterId() {
        LOGGER.info("Test delete meters by meters ID");
        meterRequest.setMeterId(111L);
        Meters testMeter = TestUtils.createMeter(meterRequest, testUser);
        given(metersRepository.findById(111L)).willReturn(Optional.of(testMeter));
        Boolean isDeleted = metersService.deleteMeterById(meterRequest);
        assertTrue(isDeleted, "Пользователь не удален");
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
        Meters testMeter = TestUtils.createMeter(meterRequest, testUser);
        given(metersRepository.findById(any(Long.class))).willReturn(Optional.of(testMeter));
        Boolean isUpdated = metersService.updateMeterById(fieldsToUpdate);
        assertTrue(isUpdated, "Ошибка обновления полей показаний счетчиков");

    }

    @Test
    void testFindById() {
        LOGGER.info("Test find meters by meters ID");
        meterRequest.setMeterId(404L);
        Meters testMeter = TestUtils.createMeter(meterRequest, testUser);
        when(metersRepository.findById(any(Long.class))).thenReturn(Optional.of(testMeter));
        Optional<Meters> meterFromDb = metersService.findById(meterRequest);
        assertTrue(meterFromDb.isPresent(), "Запись с ID " + meterRequest.getMeterId() + " не существует");
        assertEquals(meterFromDb.get(), testMeter, "Записи не соответствуют");
    }

    private void createSomeMeters(boolean isRandomDateNeed, int count) {
        for (int i = 0; i < count; i++) {
            Meters testMeter = TestUtils.createMeter(TestUtils.createMetersRequest("test"), testUser);
            testMeter.setMeterId(i + 1L);
            if (!isRandomDateNeed) {
                testMeter.setMeterDataWrite(LocalDate.now());
            }
            generatedMeters.add(testMeter);
        }
    }
}

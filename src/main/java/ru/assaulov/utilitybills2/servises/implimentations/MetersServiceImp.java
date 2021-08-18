package ru.assaulov.utilitybills2.servises.implimentations;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;
import ru.assaulov.utilitybills2.payload.respose.MessageResponse;
import ru.assaulov.utilitybills2.repositories.MetersRepository;
import ru.assaulov.utilitybills2.repositories.UserRepository;
import ru.assaulov.utilitybills2.servises.interfaces.MetersService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetersServiceImp implements MetersService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetersServiceImp.class);

	private final UserRepository userRepository;
	private final MetersRepository metersRepository;

	@Override
	public ResponseEntity<?> saveMeter(Meters meter) {
		User user = userRepository.findByLoginIgnoreCase(meter.getUser().getLogin());
		meter.setMeterDataWrite(LocalDate.now());
		meter.setUser(user);
		metersRepository.save(meter);
		LOGGER.info(meter + "successfully saved in DB");
		return ResponseEntity.ok(new MessageResponse("Meters saved successfully!"));
	}

	@Override
	public void deleteMeterById(MetersRequest request) {
		Optional<Meters> meter = metersRepository.findById(request.getMeterId());
		if (meter.isPresent()) metersRepository.delete(meter.get());
		LOGGER.info(meter.get() + "successfully deleted from DB");
	}

	@Override
	public void updateMeterById(Meters meter) {
		Meters meterFromDB = metersRepository.findById(meter.getMeterId()).get();
		LOGGER.info(meterFromDB + "before update");
		meterFromDB.setColdWater(meter.getColdWater());
		meterFromDB.setHotWater(meter.getHotWater());
		meterFromDB.setElectricity(meter.getElectricity());
		meterFromDB.setGas(meter.getGas());
		LOGGER.info(meterFromDB + "after update");
		metersRepository.saveAndFlush(meterFromDB);
		LOGGER.info(meterFromDB + " update successful");
	}

	@Override
	public Meters findById(MetersRequest request) {
		return metersRepository.findById(request.getMeterId()).get();
	}

	@Override
	public List<Meters> findAll(MetersRequest request) {
		return metersRepository.findAll();
	}

	@Override
	public List<Meters> findMetersByDate(MetersRequest request) {
		return metersRepository.findUtilitiesByDate(request.getMeterDataWrite());
	}

	@Override
	public List<Meters> findMetersByPeriod(MetersRequest request) {
		User user = userRepository.findByLoginIgnoreCase(request.getUserLogin());
		return metersRepository.findUtilitiesByPeriod(request.getDateFrom(), request.getDateTo(), user);
	}
}

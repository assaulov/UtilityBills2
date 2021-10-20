package ru.assaulov.utilitybills2.servises.implimentations;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.assaulov.utilitybills2.exeptions.BaseException;
import ru.assaulov.utilitybills2.exeptions.ErrorType;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;
import ru.assaulov.utilitybills2.repositories.MetersRepository;
import ru.assaulov.utilitybills2.repositories.UserRepository;
import ru.assaulov.utilitybills2.servises.interfaces.MetersService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetersServiceImp implements MetersService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetersServiceImp.class);

	private final UserRepository userRepository;
	private final MetersRepository metersRepository;

	@Override
	public Meters saveMeter(MetersRequest meterRequest) {
		LOGGER.info("Try save meter with request: " + meterRequest);
		User user = userRepository.findByLoginIgnoreCase(meterRequest.getUserLogin());
		Meters meterToSave = new Meters().toBuilder()
				.user(user)
				.meterDataWrite(meterRequest.getMeterDataWrite())
				.coldWater(meterRequest.getColdWater())
				.hotWater(meterRequest.getHotWater())
				.electricity(meterRequest.getElectricity())
				.gas(meterRequest.getGas())
				.build();

		if(!findMetersByDate(meterRequest).isEmpty()){
				throw new BaseException("Meter in this date already exist");
		}

		metersRepository.save(meterToSave);
		LOGGER.info(meterToSave + "successfully saved in DB");
		return meterToSave;
	}


	@Override
	public Boolean deleteMeterById(MetersRequest request) {
		 Meters meter = metersRepository.findById(request.getMeterId()).orElseThrow(()-> new BaseException(String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), request.getMeterId())));;
		 metersRepository.delete(meter);
		 LOGGER.info(meter + "successfully deleted from DB");
		 return true;
	}

	@Override
	public Boolean updateMeterById(MetersRequest request) {
		return metersRepository.findById(request.getMeterId()).map(meterFromDB -> {
			LOGGER.info(meterFromDB + "before update");
			meterFromDB.setColdWater(request.getColdWater());
			meterFromDB.setHotWater(request.getHotWater());
			meterFromDB.setElectricity(request.getElectricity());
			meterFromDB.setGas(request.getGas());
			LOGGER.info(meterFromDB + "after update");
			metersRepository.saveAndFlush(meterFromDB);
			LOGGER.info("Update successful");
			return true;
		}).orElseThrow(()-> new BaseException(String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), request.getMeterId())));
	}

	@Override
	public Optional<Meters> findById(MetersRequest request) {
		return metersRepository.findById(request.getMeterId());
	}

	@Override
	public List<Meters> findAllByUser_UserId(MetersRequest metersRequest) {
		long userId = userRepository.findByLoginIgnoreCase(metersRequest.getUserLogin()).getUserId();
		return metersRepository.findAllByUser_UserId(userId);
	}

	@Override
	public List<Meters> findMetersByDate(MetersRequest request) {
		User user = userRepository.findByLoginIgnoreCase(request.getUserLogin());
		return metersRepository.findMetersByDate(request.getMeterDataWrite(), user);
	}

	@Override
	public List<Meters> findMetersByPeriod(MetersRequest request) {
		LOGGER.info(request.toString());
		User user = userRepository.findByLoginIgnoreCase(request.getUserLogin());
		return metersRepository.findMetersByPeriod(request.getDateFrom(), request.getDateTo(), user);
	}
}

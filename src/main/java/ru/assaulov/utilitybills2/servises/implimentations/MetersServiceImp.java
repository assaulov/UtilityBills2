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
	public Meters saveMeter(MetersRequest request) {
		LOGGER.info("Try save meter with request: " + request);
		User user =findUser(request.getUserLogin());
		Meters meterToSave = new Meters().toBuilder()
				.user(user)
				.meterDataWrite(request.getMeterDataWrite())
				.coldWater(request.getColdWater())
				.hotWater(request.getHotWater())
				.electricity(request.getElectricity())
				.gas(request.getGas())
				.build();

		if(!findMetersByDate(request).isEmpty()){
				throw new BaseException("Meter in this date already exist");
		}

		metersRepository.save(meterToSave);
		LOGGER.info(meterToSave + "successfully saved in DB");
		return meterToSave;
	}


	@Override
	public Boolean deleteMeterById(MetersRequest request) {
		 Meters meter = metersRepository.findById(request.getMeterId()).orElseThrow(()-> new BaseException(String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), request.getMeterId())));
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
			meterFromDB.setMeterDataWrite(request.getMeterDataWrite());
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
	public List<Meters> findAllByUser(String userLogin) {
		return metersRepository.findAllByUser_UserId(findUser(userLogin).getUserId());
	}

	@Override
	public List<Meters> findMetersByDate(MetersRequest request) {
		List<Meters> meters = metersRepository.findMetersByDate(request.getMeterDataWrite(), findUser(request.getUserLogin()));
		if(meters.isEmpty()){
			throw new BaseException(String.format(ErrorType.ENTITY_NOT_FOUND_BY_DATE.getDescription(), request.getMeterDataWrite().toString()));
		}
		return meters;
	}

	@Override
	public List<Meters> findMetersByPeriod(MetersRequest request) {
		LOGGER.info(request.toString());
		List<Meters> meters = metersRepository.findMetersByPeriod(request.getDateFrom(), request.getDateTo(), findUser(request.getUserLogin()));
		if(meters.isEmpty()){
			throw new BaseException(String.format(ErrorType.ENTITY_NOT_FOUND_BY_PERIOD.getDescription(), request.getPeriod()));
		}
		return meters;
	}

	private User findUser(String login) {
		return Optional.ofNullable(userRepository.findByLoginIgnoreCase(login))
				.orElseThrow(()->
						new BaseException(String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), login)));
	}
}

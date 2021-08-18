package ru.assaulov.utilitybills2.servises.interfaces;


import org.springframework.http.ResponseEntity;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;

import java.util.List;

public interface MetersService {

	ResponseEntity<?> saveMeter(MetersRequest meterRequest);
	void deleteMeterById(MetersRequest request);
	void updateMeterById(Meters meter);
	Meters findById(MetersRequest request);
	List<Meters> findMetersByDate(MetersRequest request);
	List<Meters> findMetersByPeriod(MetersRequest request);
	List<Meters> findAllByUser_UserId(MetersRequest request);
}

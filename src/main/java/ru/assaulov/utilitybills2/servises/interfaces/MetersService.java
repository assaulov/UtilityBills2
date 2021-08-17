package ru.assaulov.utilitybills2.servises.interfaces;


import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;

import java.util.List;

public interface MetersService {
	void saveMeter(Meters meter);
	void deleteMeterById(MetersRequest request);
	void updateMeterById(MetersRequest request, Meters meter);
	Meters findById(MetersRequest request);
	List<Meters> findAll(MetersRequest request);
	List<Meters> findMetersByDate(MetersRequest request);
	List<Meters> findMetersByPeriod(MetersRequest request);



}

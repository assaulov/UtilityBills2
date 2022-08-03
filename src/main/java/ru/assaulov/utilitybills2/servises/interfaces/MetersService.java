package ru.assaulov.utilitybills2.servises.interfaces;

import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;

import java.util.List;
import java.util.Optional;

public interface MetersService {

    Meters saveMeter(MetersRequest meterRequest);

    Boolean deleteMeterById(MetersRequest request);

    Boolean updateMeterById(MetersRequest request);

    Optional<Meters> findById(MetersRequest request);

    List<Meters> findMetersByDate(MetersRequest request);

    List<Meters> findMetersByPeriod(MetersRequest request);

    List<Meters> findAllByUser(String userLogin);
}

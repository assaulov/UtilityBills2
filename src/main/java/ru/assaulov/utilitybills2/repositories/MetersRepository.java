package ru.assaulov.utilitybills2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.model.User;

import java.time.LocalDate;
import java.util.List;

public interface MetersRepository extends JpaRepository<Meters, Long> {

    @Query("SELECT mt FROM Meters mt WHERE mt.meterDataWrite = :meterDataWrite AND mt.user = :user")
    List<Meters> findMetersByDate(@Param("meterDataWrite") LocalDate meterDataWrite,
                                  @Param("user") User user);

    @Query("SELECT mt FROM Meters mt WHERE mt.meterDataWrite >= :dateFrom AND mt.meterDataWrite <= :dateTo AND mt.user = :user")
    List<Meters> findMetersByPeriod(@Param("dateFrom") LocalDate dateFrom,
                                    @Param("dateTo") LocalDate dateTo,
                                    @Param("user") User user);

    List<Meters> findAllByUserUserId(long userId);
}

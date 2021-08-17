package ru.assaulov.utilitybills2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.assaulov.utilitybills2.model.Meters;

public interface MetersRepository extends JpaRepository<Meters, Long> {
}

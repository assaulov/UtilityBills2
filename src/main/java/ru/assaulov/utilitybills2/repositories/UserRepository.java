package ru.assaulov.utilitybills2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.assaulov.utilitybills2.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByLoginIgnoreCase(String login);
}

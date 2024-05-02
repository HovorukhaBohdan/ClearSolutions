package org.example.clearsolutions.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.example.clearsolutions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> getAllByBirthDateBetween(LocalDate dateFrom, LocalDate dateTo);
}

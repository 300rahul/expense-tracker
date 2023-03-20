package tech.sara.expensetracker.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.sara.expensetracker.jpa.entities.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);

    Optional<User> findByPhoneNumber(String username);
}

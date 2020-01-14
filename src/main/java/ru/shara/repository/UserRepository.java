package ru.shara.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shara.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByUsername(String name);
}

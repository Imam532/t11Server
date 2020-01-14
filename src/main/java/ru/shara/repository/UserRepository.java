package ru.shara.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shara.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
//    List<User> findUsersByUsername(String name);

    @Query(value = "SELECT c FROM User c WHERE c.name LIKE '%' || :keyword || '%'"
            + " OR c.email LIKE '%' || :keyword || '%'"
            + " OR c.address LIKE '%' || :keyword || '%'"
            + " OR c.password LIKE '%' || :keyword || '%'")
    public List<User> search(@Param("keyword") String keyword);

    @Query(value = "FROM User WHERE name = :name")
    public User getUserByName(@Param("name") String name);
}

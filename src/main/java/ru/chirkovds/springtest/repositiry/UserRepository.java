package ru.chirkovds.springtest.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chirkovds.springtest.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository <User, Long> {
    List<User> findByClientId(Long id);
    User findByUsername(String login);
}
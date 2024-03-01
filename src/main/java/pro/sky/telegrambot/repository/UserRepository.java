package pro.sky.telegrambot.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByPhoneNumberContaining(String phoneNumber, Pageable pageable);
}

package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Probation;

public interface ProbationRepository extends JpaRepository<Probation, Long> {
}

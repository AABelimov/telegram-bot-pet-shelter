package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Adoption;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
}

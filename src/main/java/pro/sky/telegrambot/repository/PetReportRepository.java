package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.PetReport;

public interface PetReportRepository extends JpaRepository<PetReport, Long> {
    PetReport findByPetIdAndState(Long petId, String state);

    PetReport findByUserIdAndShelterTypeAndState(Long userId, String shelterType, String state);
}

package pro.sky.telegrambot.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.PetReport;

import java.util.List;

public interface PetReportRepository extends JpaRepository<PetReport, Long> {
    PetReport findByPetIdAndState(Long petId, String state);

    PetReport findByUserIdAndShelterTypeAndState(Long userId, String shelterType, String state);

    PetReport findFirstByVolunteerIdAndState(Long volunteerId, String state);

    List<PetReport> findAllByState(String state, PageRequest pageRequest);

    List<PetReport> findAllByVolunteerIdAndState(Long volunteerId, String state);
}

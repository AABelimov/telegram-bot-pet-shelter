package pro.sky.telegrambot.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Probation;

import java.util.List;

public interface ProbationRepository extends JpaRepository<Probation, Long> {
    List<Probation> findAllByUserIdAndShelterTypeAndState(Long userId, String shelterType, String state);

    List<Probation> findAllByUserIdAndShelterType(Long userId, String shelterType);

    Probation findByPetId(Long petId);

    Probation findFirstByVolunteerIdAndState(Long volunteerId, String state);

    List<Probation> findAllByVolunteerIdAndState(Long volunteerId, String state);

    List<Probation> findAllByState(String state, PageRequest pageRequest);
}

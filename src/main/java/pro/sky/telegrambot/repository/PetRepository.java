package pro.sky.telegrambot.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Pet;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByKindOfPetAndStateOrderByName(String kindOfPet, String state, PageRequest pageRequest);

    long countByKindOfPet(String kindOfPet);
}

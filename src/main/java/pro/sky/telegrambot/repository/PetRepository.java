package pro.sky.telegrambot.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Pet;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByStateAndKindOfPetOrderByIdDesc(String state, String kindOfPet, PageRequest pageRequest);

    List<Pet> findAllByState(String state, PageRequest pageRequest);

    List<Pet> findAllByKindOfPet(String kindOfPet, PageRequest pageRequest);
}

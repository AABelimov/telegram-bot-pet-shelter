package pro.sky.telegrambot.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.repository.PetRepository;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet getPet(Long id) {
        return petRepository.findById(id).orElseThrow(); // TODO: Добавить исключение
    }

    public List<Pet> getListOfAnimals(ShelterType shelterType, PetState state, PageRequest pageRequest) {
        String kindOfPet = null;

        switch (shelterType) {
            case DOG_SHELTER:
                kindOfPet = "DOG";
                break;
            case CAT_SHELTER:
                kindOfPet = "CAT";
        }

        return petRepository.findByKindOfPetAndStateOrderByName(kindOfPet, state.name(), pageRequest);
    }

    public long countPetsByKindOfPet(ShelterType shelterType) {
        String kindOfPet = null;

        switch (shelterType) {
            case DOG_SHELTER:
                kindOfPet = "DOG";
                break;
            case CAT_SHELTER:
                kindOfPet = "CAT";
        }

        return petRepository.countByKindOfPetAndState(kindOfPet, PetState.WAITING_TO_BE_ADOPTED.name());
    }

    public void setPetState(Long id, PetState state) {
        Pet pet = petRepository.findById(id).orElseThrow(); // TODO: добавить исключение
        pet.setState(state.name());
        petRepository.save(pet);
    }
}

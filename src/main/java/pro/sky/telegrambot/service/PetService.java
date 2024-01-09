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

        return petRepository.countByKindOfPet(kindOfPet);
    }
}

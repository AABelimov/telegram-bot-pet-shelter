package pro.sky.telegrambot.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
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

    public List<Pet> getListOfAnimals(ShelterType shelterType, PageRequest pageRequest) {
        String kindOfPet = null;

        switch (shelterType) {
            case DOG_SHELTER:
                kindOfPet = "DOG";
                break;
            case CAT_SHELTER:
                kindOfPet = "CAT";
        }

        return petRepository.findByKindOfPetOrderByName(kindOfPet, pageRequest);
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

package pro.sky.telegrambot.mapper;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.dto.PetDtoOut;
import pro.sky.telegrambot.model.Pet;

@Component
public class PetMapper {

    public PetDtoOut toDto(Pet pet) {
        PetDtoOut petDtoOut = new PetDtoOut();

        petDtoOut.setId(pet.getId());
        petDtoOut.setKindOfPet(pet.getKindOfPet());
        petDtoOut.setName(pet.getName());

        return petDtoOut;
    }
}

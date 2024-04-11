package pro.sky.telegrambot.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.dto.PetDtoIn;
import pro.sky.telegrambot.dto.PetDtoOut;
import pro.sky.telegrambot.enums.KindOfPet;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.exception.IllegalKindOfPetException;
import pro.sky.telegrambot.model.Pet;

@Component
public class PetMapper {

    private final String photoDir;

    public PetMapper(@Value("${path.to.default.photo}") String photoDir) {
        this.photoDir = photoDir;
    }

    public Pet toEntity(PetDtoIn petDtoIn) {
        try {
            KindOfPet.valueOf(petDtoIn.getKindOfPet());
            Pet pet = new Pet();
            pet.setKindOfPet(petDtoIn.getKindOfPet());
            pet.setName(petDtoIn.getName());
            pet.setAboutPet(petDtoIn.getAboutPet());
            pet.setPhotoPath(photoDir);
            pet.setState(PetState.WAITING_TO_BE_ADOPTED.name());
            return pet;
        } catch (IllegalArgumentException e) {
            throw new IllegalKindOfPetException(petDtoIn.getKindOfPet());
        }
    }

    public PetDtoOut toDto(Pet pet) {
        PetDtoOut petDtoOut = new PetDtoOut();

        petDtoOut.setId(pet.getId());
        petDtoOut.setKindOfPet(pet.getKindOfPet());
        petDtoOut.setName(pet.getName());
        petDtoOut.setAboutPet(pet.getAboutPet());
        petDtoOut.setPhotoPath("http://localhost:8080/pets/" + pet.getId() + "/photo");

        return petDtoOut;
    }
}

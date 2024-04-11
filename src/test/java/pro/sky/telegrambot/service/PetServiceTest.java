package pro.sky.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.mapper.PetMapper;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.repository.PetRepository;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static pro.sky.telegrambot.constants.PetConstants.*;

class PetServiceTest {

    private final String photoDir = "photo dir";
    private final PetRepository petRepository = mock(PetRepository.class);
    private final PetMapper petMapper = new PetMapper(photoDir);
    private PetService out;

    @BeforeEach
    public void initOut() {
        out = new PetService(petRepository, petMapper, photoDir);
    }

    @Test
    void testCreatePet() {
        Pet pet = new Pet(PET_1);
        pet.setId(null);

        when(petRepository.save(eq(pet))).thenReturn(PET_1);
//        assertEquals(PET_DTO_OUT_1, out.createPet(PET_DTO_IN_1));

//        assertThrows(IllegalKindOfPetException.class, () -> out.createPet(PET_DTO_IN_INVALID));
    }

/*    @Test
    void testGetListOfAnimals() {
        out.getListOfPets(ShelterType.DOG_SHELTER, PetState.WAITING_TO_BE_ADOPTED, PageRequest.of(0, 1));
        verify(petRepository, times(1)).findByKindOfPetAndStateOrderByName(eq("DOG"), eq("WAITING_TO_BE_ADOPTED"), eq(PageRequest.of(0, 1)));

        out.getListOfPets(ShelterType.CAT_SHELTER, PetState.WAITING_TO_BE_ADOPTED, PageRequest.of(0, 1));
        verify(petRepository, times(1)).findByKindOfPetAndStateOrderByName(eq("CAT"), eq("WAITING_TO_BE_ADOPTED"), eq(PageRequest.of(0, 1)));
    }*/
}
package pro.sky.telegrambot.mapper;

import org.junit.jupiter.api.Test;
import pro.sky.telegrambot.dto.PetDtoOut;

import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.telegrambot.constants.PetConstants.*;

class PetMapperTest {


    private final PetMapper out = new PetMapper(null);

    @Test
    void testToDto() {
        PetDtoOut actualPetDtoOut = out.toDto(PET_1);

        assertEquals(PET_DTO_OUT_1.getId(), actualPetDtoOut.getId());
        assertEquals(PET_DTO_OUT_1.getKindOfPet(), actualPetDtoOut.getKindOfPet());
        assertEquals(PET_DTO_OUT_1.getName(), actualPetDtoOut.getName());
    }
}
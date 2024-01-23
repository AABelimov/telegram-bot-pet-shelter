package pro.sky.telegrambot.mapper;

import org.junit.jupiter.api.Test;
import pro.sky.telegrambot.dto.VolunteerDtoOut;

import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

class VolunteerMapperTest {

    private final VolunteerMapper out = new VolunteerMapper();

    @Test
    void shouldReturnVolunteerDtoOut() {
        assertEquals(VOLUNTEER_DTO_OUT_1, out.toDto(VOLUNTEER_1));
    }

}
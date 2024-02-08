package pro.sky.telegrambot.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

class VolunteerMapperTest {

    private final VolunteerMapper out = new VolunteerMapper();

    @Test
    void testToDto() {
        assertEquals(VOLUNTEER_DTO_OUT_1, out.toDto(VOLUNTEER_1));
    }

}
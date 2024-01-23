package pro.sky.telegrambot.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.telegrambot.constants.UserConstants.*;

class UserMapperTest {

    private final UserMapper out = new UserMapper();

    @Test
    void shouldReturnUserDtoOut() {
        assertEquals(USER_DTO_OUT_1, out.toDto(USER_1));
    }

}
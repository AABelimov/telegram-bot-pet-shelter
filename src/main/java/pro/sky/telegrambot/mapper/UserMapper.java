package pro.sky.telegrambot.mapper;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.dto.UserDtoOut;
import pro.sky.telegrambot.model.User;

@Component
public class UserMapper {

    public UserDtoOut toDto(User user) {
        UserDtoOut userDtoOut = new UserDtoOut();

        userDtoOut.setId(user.getId());
        userDtoOut.setName(user.getName());
        userDtoOut.setPhoneNumber(user.getPhoneNumber());

        return userDtoOut;
    }
}

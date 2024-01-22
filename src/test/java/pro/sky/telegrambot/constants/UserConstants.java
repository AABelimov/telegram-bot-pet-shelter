package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.dto.UserDtoOut;
import pro.sky.telegrambot.model.User;

public class UserConstants {

    public static final Long USER_ID_1 = 1L;
    public static final String USER_NAME_1 = "USER_NAME_1";
    public static final String USER_PHONE_NUMBER_1 = "USER_PHONE_NUMBER_1";
    public static final String USER_SELECTED_SHELTER_1 = "";
    public static final String USER_STATE_1 = "";
    public static final User USER_1 = new User(USER_ID_1, USER_NAME_1, USER_PHONE_NUMBER_1, USER_SELECTED_SHELTER_1, USER_STATE_1);
    public static final UserDtoOut USER_DTO_OUT_1 = new UserDtoOut(USER_ID_1, USER_NAME_1, USER_PHONE_NUMBER_1);

}

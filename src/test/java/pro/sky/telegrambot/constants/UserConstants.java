package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.dto.UserDtoOut;
import pro.sky.telegrambot.model.User;

public class UserConstants {

    public static final Long USER_ID_1 = 1L;
    public static final Long USER_ID_2 = 2L;
    public static final Long USER_ID_3 = 3L;
    public static final Long NOT_FOUND_USER_ID = 100L;
    public static final String USER_NAME_1 = "USER_NAME_1";
    public static final String USER_NAME_2 = "USER_NAME_2";
    public static final String USER_NAME_3 = "USER_NAME_3";
    public static final String USER_PHONE_NUMBER_1 = "USER_PHONE_NUMBER_1";
    public static final String USER_PHONE_NUMBER_2 = "USER_PHONE_NUMBER_2";
    public static final String USER_PHONE_NUMBER_3 = "USER_PHONE_NUMBER_3";
    public static final String USER_SELECTED_SHELTER_1 = "";
    public static final String USER_SELECTED_SHELTER_2 = "";
    public static final String USER_SELECTED_SHELTER_3 = "";
    public static final String USER_STATE_1 = "";
    public static final String USER_STATE_2 = "";
    public static final String USER_STATE_3 = "";
    public static final User USER_1 = new User(USER_ID_1, USER_NAME_1, USER_PHONE_NUMBER_1, USER_SELECTED_SHELTER_1, USER_STATE_1);
    public static final User USER_2 = new User(USER_ID_2, USER_NAME_2, USER_PHONE_NUMBER_2, USER_SELECTED_SHELTER_2, USER_STATE_2);
    public static final User USER_3 = new User(USER_ID_3, USER_NAME_3, USER_PHONE_NUMBER_3, USER_SELECTED_SHELTER_3, USER_STATE_3);
    public static final UserDtoOut USER_DTO_OUT_1 = new UserDtoOut(USER_ID_1, USER_NAME_1, USER_PHONE_NUMBER_1);
    public static final UserDtoOut USER_DTO_OUT_2 = new UserDtoOut(USER_ID_2, USER_NAME_2, USER_PHONE_NUMBER_2);
    public static final UserDtoOut USER_DTO_OUT_3 = new UserDtoOut(USER_ID_3, USER_NAME_3, USER_PHONE_NUMBER_3);

}

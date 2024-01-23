package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.dto.VolunteerDtoOut;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;

public class VolunteerConstants {

    public static final Long VOLUNTEER_ID_1 = 1L;
    public static final Long VOLUNTEER_ID_2 = 2L;
    public static final Long VOLUNTEER_ID_3 = 3L;
    public static final Long NOT_FOUND_VOLUNTEER_ID = 100L;
    public static final String VOLUNTEER_NAME_1 = "VOLUNTEER_NAME_1";
    public static final String VOLUNTEER_NAME_2 = "VOLUNTEER_NAME_2";
    public static final String VOLUNTEER_NAME_3 = "VOLUNTEER_NAME_3";
    public static final User VOLUNTEER_USER_1 = null;
    public static final User VOLUNTEER_USER_2 = null;
    public static final User VOLUNTEER_USER_3 = null;
    public static final String VOLUNTEER_STATE_1 = "";
    public static final String VOLUNTEER_STATE_2 = "";
    public static final String VOLUNTEER_STATE_3 = "";
    public static final Volunteer VOLUNTEER_1 = new Volunteer(VOLUNTEER_ID_1, VOLUNTEER_NAME_1, VOLUNTEER_USER_1, VOLUNTEER_STATE_1);
    public static final Volunteer VOLUNTEER_2 = new Volunteer(VOLUNTEER_ID_2, VOLUNTEER_NAME_2, VOLUNTEER_USER_2, VOLUNTEER_STATE_2);
    public static final Volunteer VOLUNTEER_3 = new Volunteer(VOLUNTEER_ID_3, VOLUNTEER_NAME_3, VOLUNTEER_USER_3, VOLUNTEER_STATE_3);
    public static final VolunteerDtoOut VOLUNTEER_DTO_OUT_1 = new VolunteerDtoOut(VOLUNTEER_ID_1, VOLUNTEER_NAME_1);
    public static final VolunteerDtoOut VOLUNTEER_DTO_OUT_2 = new VolunteerDtoOut(VOLUNTEER_ID_2, VOLUNTEER_NAME_2);
    public static final VolunteerDtoOut VOLUNTEER_DTO_OUT_3 = new VolunteerDtoOut(VOLUNTEER_ID_3, VOLUNTEER_NAME_3);
}

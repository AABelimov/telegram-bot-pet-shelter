package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;

import java.time.LocalDateTime;

import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

public class ProbationConstants {

    public static final Long PROBATION_ID_1 = 1L;
    public static final Long PROBATION_ID_2 = 2L;
    public static final User PROBATION_USER_1 = USER_1;
    public static final User PROBATION_USER_2 = USER_2;
    public static final Pet PROBATION_PET_1 = PET_1;
    public static final Pet PROBATION_PET_2 = PET_2;
    public static final String PROBATION_SHELTER_TYPE_1 = "";
    public static final String PROBATION_SHELTER_TYPE_2 = "";
    public static final LocalDateTime PROBATION_END_DATE_1 = LocalDateTime.MIN.plusDays(30);
    public static final LocalDateTime PROBATION_END_DATE_2 = LocalDateTime.MIN.plusDays(30);
    public static final LocalDateTime PROBATION_LAST_REPORT_DATE_1 = LocalDateTime.MIN;
    public static final LocalDateTime PROBATION_LAST_REPORT_DATE_2 = LocalDateTime.MIN;
    public static final Volunteer PROBATION_VOLUNTEER_1 = VOLUNTEER_1;
    public static final Volunteer PROBATION_VOLUNTEER_2 = VOLUNTEER_2;
    public static final String PROBATION_STATE_1 = "";
    public static final String PROBATION_STATE_2 = "";
    public static final Probation PROBATION_1 = new Probation(PROBATION_ID_1, PROBATION_USER_1, PROBATION_PET_1,
            PROBATION_SHELTER_TYPE_1, PROBATION_END_DATE_1, PROBATION_LAST_REPORT_DATE_1, PROBATION_VOLUNTEER_1, PROBATION_STATE_1);
    public static final Probation PROBATION_2 = new Probation(PROBATION_ID_2, PROBATION_USER_2, PROBATION_PET_2,
            PROBATION_SHELTER_TYPE_2, PROBATION_END_DATE_2, PROBATION_LAST_REPORT_DATE_2, PROBATION_VOLUNTEER_2, PROBATION_STATE_2);
    public static final ProbationDtoIn PROBATION_DTO_IN_1 = new ProbationDtoIn(USER_ID_1, PET_ID_1, VOLUNTEER_ID_1);
    public static final ProbationDtoIn PROBATION_DTO_IN_2 = new ProbationDtoIn(USER_ID_2, PET_ID_2, VOLUNTEER_ID_2);
    public static final ProbationDtoIn NOT_FOUNT_PET_ID_PROBATION_DTO_IN = new ProbationDtoIn(USER_ID_1, NOT_FOUND_PET_ID, VOLUNTEER_ID_1);
    public static final ProbationDtoIn NOT_FOUNT_USER_ID_PROBATION_DTO_IN = new ProbationDtoIn(NOT_FOUND_USER_ID, PET_ID_1, VOLUNTEER_ID_1);
    public static final ProbationDtoIn NOT_FOUNT_VOLUNTEER_ID_PROBATION_DTO_IN = new ProbationDtoIn(USER_ID_1, PET_ID_1, NOT_FOUND_VOLUNTEER_ID);
}

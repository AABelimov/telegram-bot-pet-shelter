package pro.sky.telegrambot.constants;

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
    public static final User PROBATION_USER_1 = USER_1;
    public static final Pet PROBATION_PET_1 = PET_1;
    public static final String PROBATION_SHELTER_TYPE_1 = "";
    public static final LocalDateTime PROBATION_END_DATE_1 = LocalDateTime.MAX;
    public static final LocalDateTime LAST_REPORT_DATE_1 = LocalDateTime.MIN;
    public static final Volunteer PROBATION_VOLUNTEER_1 = VOLUNTEER_1;
    public static final String PROBATION_STATE_1 = "";
    public static final Probation PROBATION_1 = new Probation(PROBATION_ID_1, PROBATION_USER_1, PROBATION_PET_1
            , PROBATION_SHELTER_TYPE_1, PROBATION_END_DATE_1, LAST_REPORT_DATE_1, PROBATION_VOLUNTEER_1, PROBATION_STATE_1);
}

package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;

import java.time.LocalDateTime;

import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

public class PetReportConstants {
    public static final Long PET_REPORT_ID_1 = 1L;
    public static final Pet PET_REPORT_PET_1 = PET_1;
    public static final String PET_REPORT_SHELTER_TYPE_1 = "";
    public static final User PET_REPORT_USER_1 = USER_1;
    public static final LocalDateTime PET_REPORT_TIME_SENDING_REPORT_1 = LocalDateTime.MAX;
    public static final String PET_REPORT_PHOTO_PATH_1 = "";
    public static final String PET_REPORT_DIET_1 = "";
    public static final String PET_REPORT_WELL_BEING_1 = "";
    public static final String PET_REPORT_CHANGE_IN_BEHAVIOR_1 = "";
    public static final String PET_REPORT_STATE_1 = "";
    public static final Volunteer PET_REPORT_VOLUNTEER_1 = VOLUNTEER_1;
    public static final PetReport PET_REPORT_1 = new PetReport(PET_REPORT_ID_1, PET_REPORT_PET_1, PET_REPORT_SHELTER_TYPE_1,
            PET_REPORT_USER_1, PET_REPORT_TIME_SENDING_REPORT_1, PET_REPORT_PHOTO_PATH_1, PET_REPORT_DIET_1, PET_REPORT_WELL_BEING_1,
            PET_REPORT_CHANGE_IN_BEHAVIOR_1, PET_REPORT_STATE_1, PET_REPORT_VOLUNTEER_1);
}

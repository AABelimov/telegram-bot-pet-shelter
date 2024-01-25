package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.dto.PetReportDtoOut;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

public class PetReportConstants {
    public static final Long PET_REPORT_ID_1 = 1L;
    public static final Long PET_REPORT_ID_2 = 2L;
    public static final Long PET_REPORT_ID_3 = 3L;
    public static final Pet PET_REPORT_PET_1 = PET_1;
    public static final Pet PET_REPORT_PET_2 = PET_2;
    public static final Pet PET_REPORT_PET_3 = PET_3;
    public static final String PET_REPORT_SHELTER_TYPE_1 = "";
    public static final String PET_REPORT_SHELTER_TYPE_2 = "";
    public static final String PET_REPORT_SHELTER_TYPE_3 = "";
    public static final User PET_REPORT_USER_1 = USER_1;
    public static final User PET_REPORT_USER_2 = USER_2;
    public static final User PET_REPORT_USER_3 = USER_3;
    public static final LocalDateTime PET_REPORT_TIME_SENDING_REPORT_1 = LocalDateTime.MAX;
    public static final LocalDateTime PET_REPORT_TIME_SENDING_REPORT_2 = LocalDateTime.MAX;
    public static final LocalDateTime PET_REPORT_TIME_SENDING_REPORT_3 = LocalDateTime.MAX;
    public static final String PET_REPORT_PHOTO_PATH_1 = "";
    public static final String PET_REPORT_PHOTO_PATH_2 = "";
    public static final String PET_REPORT_PHOTO_PATH_3 = "";
    public static final String PET_REPORT_DIET_1 = "";
    public static final String PET_REPORT_DIET_2 = "";
    public static final String PET_REPORT_DIET_3 = "";
    public static final String PET_REPORT_WELL_BEING_1 = "";
    public static final String PET_REPORT_WELL_BEING_2 = "";
    public static final String PET_REPORT_WELL_BEING_3 = "";
    public static final String PET_REPORT_CHANGE_IN_BEHAVIOR_1 = "";
    public static final String PET_REPORT_CHANGE_IN_BEHAVIOR_2 = "";
    public static final String PET_REPORT_CHANGE_IN_BEHAVIOR_3 = "";
    public static final String PET_REPORT_STATE_1 = PetReportState.FILLING.name();
    public static final String PET_REPORT_STATE_2 = "";
    public static final String PET_REPORT_STATE_3 = "";
    public static final Volunteer PET_REPORT_VOLUNTEER_1 = VOLUNTEER_1;
    public static final Volunteer PET_REPORT_VOLUNTEER_2 = VOLUNTEER_2;
    public static final Volunteer PET_REPORT_VOLUNTEER_3 = VOLUNTEER_3;
    public static final PetReport PET_REPORT_1 = new PetReport(PET_REPORT_ID_1, PET_REPORT_PET_1, PET_REPORT_SHELTER_TYPE_1,
            PET_REPORT_USER_1, PET_REPORT_TIME_SENDING_REPORT_1, PET_REPORT_PHOTO_PATH_1, PET_REPORT_DIET_1, PET_REPORT_WELL_BEING_1,
            PET_REPORT_CHANGE_IN_BEHAVIOR_1, PET_REPORT_STATE_1, PET_REPORT_VOLUNTEER_1);
    public static final PetReport PET_REPORT_2 = new PetReport(PET_REPORT_ID_2, PET_REPORT_PET_2, PET_REPORT_SHELTER_TYPE_2,
            PET_REPORT_USER_2, PET_REPORT_TIME_SENDING_REPORT_2, PET_REPORT_PHOTO_PATH_2, PET_REPORT_DIET_2, PET_REPORT_WELL_BEING_2,
            PET_REPORT_CHANGE_IN_BEHAVIOR_2, PET_REPORT_STATE_2, PET_REPORT_VOLUNTEER_2);
    public static final PetReport PET_REPORT_3 = new PetReport(PET_REPORT_ID_3, PET_REPORT_PET_3, PET_REPORT_SHELTER_TYPE_3,
            PET_REPORT_USER_3, PET_REPORT_TIME_SENDING_REPORT_3, PET_REPORT_PHOTO_PATH_3, PET_REPORT_DIET_3, PET_REPORT_WELL_BEING_3,
            PET_REPORT_CHANGE_IN_BEHAVIOR_3, PET_REPORT_STATE_3, PET_REPORT_VOLUNTEER_3);
    public static final PetReportDtoOut PET_REPORT_DTO_OUT_1 = new PetReportDtoOut(PET_REPORT_ID_1, USER_DTO_OUT_1,
            PET_DTO_OUT_1, VOLUNTEER_DTO_OUT_1, PET_REPORT_TIME_SENDING_REPORT_1, PET_REPORT_DIET_1,
            PET_REPORT_WELL_BEING_1, PET_REPORT_CHANGE_IN_BEHAVIOR_1);
    public static final PetReportDtoOut PET_REPORT_DTO_OUT_2 = new PetReportDtoOut(PET_REPORT_ID_2, USER_DTO_OUT_2,
            PET_DTO_OUT_2, VOLUNTEER_DTO_OUT_2, PET_REPORT_TIME_SENDING_REPORT_2, PET_REPORT_DIET_2,
            PET_REPORT_WELL_BEING_2, PET_REPORT_CHANGE_IN_BEHAVIOR_2);
    public static final PetReportDtoOut PET_REPORT_DTO_OUT_3 = new PetReportDtoOut(PET_REPORT_ID_3, USER_DTO_OUT_3,
            PET_DTO_OUT_3, VOLUNTEER_DTO_OUT_3, PET_REPORT_TIME_SENDING_REPORT_3, PET_REPORT_DIET_3,
            PET_REPORT_WELL_BEING_3, PET_REPORT_CHANGE_IN_BEHAVIOR_3);
    public static final List<PetReport> UNVERIFIED_REPORTS_LIST = new ArrayList<>(List.of(
            PET_REPORT_1,
            PET_REPORT_2,
            PET_REPORT_3
    ));
    public static final List<PetReportDtoOut> UNVERIFIED_REPORTS_DTO_OUT_LIST = new ArrayList<>(List.of(
            PET_REPORT_DTO_OUT_1,
            PET_REPORT_DTO_OUT_2,
            PET_REPORT_DTO_OUT_3
    ));
}

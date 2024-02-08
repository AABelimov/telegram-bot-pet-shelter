package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.model.OverdueReport;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;

import java.util.ArrayList;
import java.util.List;

import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

public class OverdueReportConstants {

    public static final Long OVERDUE_REPORT_ID_1 = 1L;
    public static final Long OVERDUE_REPORT_ID_2 = 2L;
    public static final Long OVERDUE_REPORT_ID_3 = 3L;
    public static final int OVERDUE_REPORT_DAYS_1 = 1;
    public static final int OVERDUE_REPORT_DAYS_2 = 2;
    public static final int OVERDUE_REPORT_DAYS_3 = 3;
    public static final User OVERDUE_REPORT_USER_1 = USER_1;
    public static final User OVERDUE_REPORT_USER_2 = USER_2;
    public static final User OVERDUE_REPORT_USER_3 = USER_3;
    public static final Pet OVERDUE_REPORT_PET_1 = PET_1;
    public static final Pet OVERDUE_REPORT_PET_2 = PET_2;
    public static final Pet OVERDUE_REPORT_PET_3 = PET_3;
    public static final Volunteer OVERDUE_REPORT_VOLUNTEER_1 = VOLUNTEER_1;
    public static final Volunteer OVERDUE_REPORT_VOLUNTEER_2 = VOLUNTEER_2;
    public static final Volunteer OVERDUE_REPORT_VOLUNTEER_3 = VOLUNTEER_3;
    public static final OverdueReport OVERDUE_REPORT_1 = new OverdueReport(OVERDUE_REPORT_ID_1, OVERDUE_REPORT_DAYS_1, OVERDUE_REPORT_USER_1,
            OVERDUE_REPORT_PET_1, OVERDUE_REPORT_VOLUNTEER_1);
    public static final OverdueReport OVERDUE_REPORT_2 = new OverdueReport(OVERDUE_REPORT_ID_2, OVERDUE_REPORT_DAYS_2, OVERDUE_REPORT_USER_2,
            OVERDUE_REPORT_PET_2, OVERDUE_REPORT_VOLUNTEER_2);
    public static final OverdueReport OVERDUE_REPORT_3 = new OverdueReport(OVERDUE_REPORT_ID_3, OVERDUE_REPORT_DAYS_3, OVERDUE_REPORT_USER_3,
            OVERDUE_REPORT_PET_3, OVERDUE_REPORT_VOLUNTEER_3);
    public static final List<OverdueReport> OVERDUE_REPORT_LIST = new ArrayList<>(List.of(
            OVERDUE_REPORT_1,
            OVERDUE_REPORT_2,
            OVERDUE_REPORT_3
    ));

}

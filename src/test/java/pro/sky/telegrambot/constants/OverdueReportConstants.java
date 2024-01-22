package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.model.OverdueReport;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;

import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

public class OverdueReportConstants {

    public static final Long OVERDUE_REPORT_ID_1 = 1L;
    public static final int OVERDUE_REPORT_DAYS_1 = 10;
    public static final User OVERDUE_REPORT_USER_1 = USER_1;
    public static final Pet OVERDUE_REPORT_PET_1 = PET_1;
    public static final Volunteer OVERDUE_REPORT_VOLUNTEER_1 = VOLUNTEER_1;
    public static final OverdueReport OVERDUE_REPORT_1 = new OverdueReport(OVERDUE_REPORT_ID_1, OVERDUE_REPORT_DAYS_1, OVERDUE_REPORT_USER_1,
            OVERDUE_REPORT_PET_1, OVERDUE_REPORT_VOLUNTEER_1);

}

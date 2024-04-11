package pro.sky.telegrambot.constants;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserCommand;
import pro.sky.telegrambot.enums.VolunteerCommand;

import static pro.sky.telegrambot.constants.PetConstants.*;

public class InlineKeyboardMarkupConstants {
    public static final InlineKeyboardButton CAT_BUTTON = new InlineKeyboardButton("Приют с кошками")
            .callbackData(ShelterType.CAT_SHELTER.name());

    public static final InlineKeyboardButton DOG_BUTTON = new InlineKeyboardButton("Приют с собаками")
            .callbackData(ShelterType.DOG_SHELTER.name());

    public static final InlineKeyboardButton INFO_ABOUT_SHELTER_BUTTON = new InlineKeyboardButton("Информация о приюте")
            .callbackData(UserCommand.INFO_ABOUT_SHELTER.name());

    public static final InlineKeyboardButton HOW_ADOPT_PET_BUTTON = new InlineKeyboardButton("Как взять животное из приюта")
            .callbackData(UserCommand.HOW_ADOPT_PET.name());

    public static final InlineKeyboardButton SEND_REPORT_BUTTON = new InlineKeyboardButton("Отправить отчет")
            .callbackData(UserCommand.SEND_REPORT.name());

    public static final InlineKeyboardButton CALL_VOLUNTEER_BUTTON = new InlineKeyboardButton("Позвать волонтера")
            .callbackData(UserCommand.CALL_VOLUNTEER.name());

    public static final InlineKeyboardButton BACK_BUTTON = new InlineKeyboardButton("Назад")
            .callbackData(UserCommand.BACK.name());
    public static final InlineKeyboardButton BACK_BUTTON_1 = new InlineKeyboardButton("Назад")
            .callbackData("-1");

    public static final InlineKeyboardButton ABOUT_SHELTER_BUTTON = new InlineKeyboardButton("Расскажи о приюте")
            .callbackData(UserCommand.ABOUT_PET_SHELTER.name());

    public static final InlineKeyboardButton SCHEDULE_AND_ADDRESS_BUTTON = new InlineKeyboardButton("Расписание и адрес")
            .callbackData(UserCommand.SCHEDULE_AND_ADDRESS.name());

    public static final InlineKeyboardButton LOCATION_MAP_BUTTON = new InlineKeyboardButton("Схема проезда")
            .callbackData(UserCommand.LOCATION_MAP.name());

    public static final InlineKeyboardButton REGISTRATION_PASS_BUTTON = new InlineKeyboardButton("Контакты для оформления пропуска")
            .callbackData(UserCommand.REGISTRATION_PASS.name());

    public static final InlineKeyboardButton SAFETY_PRECAUTIONS = new InlineKeyboardButton("Рекомендации по технике безопасности")
            .callbackData(UserCommand.SAFETY_PRECAUTIONS.name());

    public static final InlineKeyboardButton USER_CONTACTS_BUTTON = new InlineKeyboardButton("Оставить контакты для связи")
            .callbackData(UserCommand.SHARE_CONTACTS.name());

    public static final InlineKeyboardButton LIST_OF_ANIMALS_BUTTON = new InlineKeyboardButton("Список животных")
            .callbackData(UserCommand.LIST_OF_PETS.name());

    public static final InlineKeyboardButton RULES_FOR_MEETING_BUTTON = new InlineKeyboardButton("Правила знакомства с животным")
            .callbackData(UserCommand.RULES_FOR_MEETING.name());

    public static final InlineKeyboardButton DOCUMENTS_BUTTON = new InlineKeyboardButton("Документы для усыновления")
            .callbackData(UserCommand.DOCUMENTS.name());

    public static final InlineKeyboardButton TRANSPORTATION_RULES_BUTTON = new InlineKeyboardButton("Рекомендации по транспортировке")
            .callbackData(UserCommand.TRANSPORTATION_RULES.name());

    public static final InlineKeyboardButton HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON = new InlineKeyboardButton("Обустройство дома для молодого животного")
            .callbackData(UserCommand.HOME_IMPROVEMENT_FOR_SMALL_PET.name());

    public static final InlineKeyboardButton HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON = new InlineKeyboardButton("Обустройство дома для взрослого животного")
            .callbackData(UserCommand.HOME_IMPROVEMENT_FOR_BIG_PET.name());

    public static final InlineKeyboardButton HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON = new InlineKeyboardButton("Обустройство дома для животного инвалида")
            .callbackData(UserCommand.HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES.name());

    public static final InlineKeyboardButton ADVICE_FROM_DOG_HANDLERS_BUTTON = new InlineKeyboardButton("Советы от кинолога")
            .callbackData(UserCommand.ADVICE_FROM_DOG_HANDLERS.name());

    public static final InlineKeyboardButton DOG_HANDLERS_BUTTON = new InlineKeyboardButton("Проверенные кинологи")
            .callbackData(UserCommand.DOG_HANDLERS.name());

    public static final InlineKeyboardButton REJECTION_REASON_BUTTON = new InlineKeyboardButton("Причины отказа в усыновлении")
            .callbackData(UserCommand.REJECTION_REASON.name());

    public static final InlineKeyboardButton NEXT_BUTTON = new InlineKeyboardButton("следующий").callbackData(Integer.toString(1));
    public static final InlineKeyboardButton NEXT_BUTTON_1 = new InlineKeyboardButton("следующий").callbackData(Integer.toString(2));
    public static final InlineKeyboardButton PREV_BUTTON = new InlineKeyboardButton("предыдущий").callbackData(Integer.toString(0));
    public static final InlineKeyboardButton PREV_BUTTON_1 = new InlineKeyboardButton("предыдущий").callbackData(Integer.toString(1));
    public static final InlineKeyboardButton MAIN_MENU_BUTTON = new InlineKeyboardButton("Главное меню").callbackData("-1");
    public static final InlineKeyboardButton SELECT_ANIMAL_TO_REPORT_BUTTON = new InlineKeyboardButton("Выбрать животное для отчета")
            .callbackData(UserCommand.SELECT_ANIMAL_TO_REPORT.name());
    public static final InlineKeyboardButton PET_1_BUTTON = new InlineKeyboardButton(PET_NAME_1).callbackData(PET_ID_1.toString());
    public static final InlineKeyboardButton PET_2_BUTTON = new InlineKeyboardButton(PET_NAME_2).callbackData(PET_ID_2.toString());
    public static final InlineKeyboardButton PET_3_BUTTON = new InlineKeyboardButton(PET_NAME_3).callbackData(PET_ID_3.toString());
    public static final InlineKeyboardButton CHECK_REPORTS_BUTTON = new InlineKeyboardButton("Проверить отчеты")
            .callbackData(VolunteerCommand.CHECK_REPORTS.name());

    public static final InlineKeyboardButton OVERDUE_REPORT_BUTTON = new InlineKeyboardButton("Посмотреть должников по отчетам")
            .callbackData(VolunteerCommand.OVERDUE_REPORTS.name());

    public static final InlineKeyboardButton DECIDE_ON_PROBATION_BUTTON = new InlineKeyboardButton("Принять решение по усыновлению")
            .callbackData(VolunteerCommand.DECIDE_ON_PROBATION.name());

    public static final InlineKeyboardButton AT_WORK_BUTTON = new InlineKeyboardButton("На работе")
            .callbackData(VolunteerCommand.AT_WORK.name());

    public static final InlineKeyboardButton NOT_AT_WORK_BUTTON = new InlineKeyboardButton("Не на работе")
            .callbackData(VolunteerCommand.NOT_AT_WORK.name());

    public static final InlineKeyboardButton ACCEPT_REPORT_BUTTON = new InlineKeyboardButton("Принять отчет")
            .callbackData(VolunteerCommand.ACCEPT_REPORT.name());

    public static final InlineKeyboardButton DENY_REPORT_BUTTON = new InlineKeyboardButton("Забраковать отчет")
            .callbackData(VolunteerCommand.DENY_REPORT.name());

    public static final InlineKeyboardButton FINISH_VIEWING_REPORTS_BUTTON = new InlineKeyboardButton("Закончить просмотр отчетов")
            .callbackData(VolunteerCommand.FINISH_VIEWING_REPORTS.name());

    public static final InlineKeyboardButton ALLOW_ADOPTION_BUTTON = new InlineKeyboardButton("Разрешить усыновление")
            .callbackData(VolunteerCommand.ALLOW_ADOPTION.name());

    public static final InlineKeyboardButton REFUSE_ADOPTION_BUTTON = new InlineKeyboardButton("Отказать в усыновлении")
            .callbackData(VolunteerCommand.REFUSE_ADOPTION.name());

    public static final InlineKeyboardButton EXTEND_FOR_FOURTEEN_DAYS_BUTTON = new InlineKeyboardButton("Продлить на 14 дней")
            .callbackData(VolunteerCommand.EXTEND_FOR_FOURTEEN_DAYS.name());

    public static final InlineKeyboardButton EXTEND_FOR_THIRTY_DAYS_BUTTON = new InlineKeyboardButton("Продлить на 30 дней")
            .callbackData(VolunteerCommand.EXTEND_FOR_THIRTY_DAYS.name());

    public static final InlineKeyboardButton FINISH_VIEWING_BUTTON = new InlineKeyboardButton("Закончить просмотр")
            .callbackData(VolunteerCommand.FINISH_VIEWING_DECIDE_ON_PROBATION.name());

    public static final InlineKeyboardMarkup CHOOSE_SHELTER_USER_MENU_KEYBOARD = new InlineKeyboardMarkup(CAT_BUTTON, DOG_BUTTON);
    public static final InlineKeyboardMarkup USER_MAIN_MENU_KEYBOARD =
            new InlineKeyboardMarkup(INFO_ABOUT_SHELTER_BUTTON)
                    .addRow(HOW_ADOPT_PET_BUTTON)
                    .addRow(SEND_REPORT_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_INFO_ABOUT_SHELTER =
            new InlineKeyboardMarkup(ABOUT_SHELTER_BUTTON)
                    .addRow(SCHEDULE_AND_ADDRESS_BUTTON)
                    .addRow(LOCATION_MAP_BUTTON)
                    .addRow(REGISTRATION_PASS_BUTTON)
                    .addRow(SAFETY_PRECAUTIONS)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_ABOUT_PET_SHELTER =
            new InlineKeyboardMarkup(SCHEDULE_AND_ADDRESS_BUTTON)
                    .addRow(LOCATION_MAP_BUTTON)
                    .addRow(REGISTRATION_PASS_BUTTON)
                    .addRow(SAFETY_PRECAUTIONS)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_SCHEDULE_AND_ADDRESS =
            new InlineKeyboardMarkup(ABOUT_SHELTER_BUTTON)
                    .addRow(LOCATION_MAP_BUTTON)
                    .addRow(REGISTRATION_PASS_BUTTON)
                    .addRow(SAFETY_PRECAUTIONS)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_LOCATION_MAP =
            new InlineKeyboardMarkup(ABOUT_SHELTER_BUTTON)
                    .addRow(SCHEDULE_AND_ADDRESS_BUTTON)
                    .addRow(REGISTRATION_PASS_BUTTON)
                    .addRow(SAFETY_PRECAUTIONS)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_REGISTRATION_PASS =
            new InlineKeyboardMarkup(ABOUT_SHELTER_BUTTON)
                    .addRow(SCHEDULE_AND_ADDRESS_BUTTON)
                    .addRow(LOCATION_MAP_BUTTON)
                    .addRow(SAFETY_PRECAUTIONS)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_SAFETY_PRECAUTIONS =
            new InlineKeyboardMarkup(ABOUT_SHELTER_BUTTON)
                    .addRow(SCHEDULE_AND_ADDRESS_BUTTON)
                    .addRow(LOCATION_MAP_BUTTON)
                    .addRow(REGISTRATION_PASS_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_CALL_VOLUNTEER =
            new InlineKeyboardMarkup(ABOUT_SHELTER_BUTTON)
                    .addRow(SCHEDULE_AND_ADDRESS_BUTTON)
                    .addRow(LOCATION_MAP_BUTTON)
                    .addRow(REGISTRATION_PASS_BUTTON)
                    .addRow(SAFETY_PRECAUTIONS)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOW_ADOPT_PET_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_LIST_OF_ANIMALS_DOG_SHELTER =
            new InlineKeyboardMarkup(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_RULES_FOR_MEETING_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_DOCUMENTS_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_TRANSPORTATION_RULES_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_SMALL_PET_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_BIG_PET_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_ADVICE_FROM_DOG_HANDLERS_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_DOG_HANDLERS_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_REJECTION_REASON_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_CALL_VOLUNTEER_DOG_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(ADVICE_FROM_DOG_HANDLERS_BUTTON)
                    .addRow(DOG_HANDLERS_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOW_ADOPT_PET_CAT_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_LIST_OF_ANIMALS_CAT_SHELTER =
            new InlineKeyboardMarkup(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_RULES_FOR_MEETING_CAT_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_DOCUMENTS_CAT_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_TRANSPORTATION_RULES_CAT_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_SMALL_PET_CAT_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_BIG_PET_CAT_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_CAT_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_REJECTION_REASON_CAT_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(CALL_VOLUNTEER_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD_CALL_VOLUNTEER_CAT_SHELTER =
            new InlineKeyboardMarkup(LIST_OF_ANIMALS_BUTTON)
                    .addRow(RULES_FOR_MEETING_BUTTON)
                    .addRow(DOCUMENTS_BUTTON)
                    .addRow(TRANSPORTATION_RULES_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_SMALL_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_BIG_PET_BUTTON)
                    .addRow(HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_BUTTON)
                    .addRow(REJECTION_REASON_BUTTON)
                    .addRow(USER_CONTACTS_BUTTON)
                    .addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup LIST_OF_ANIMALS_USER_MENU_KEYBOARD_PREV =
            new InlineKeyboardMarkup(PREV_BUTTON_1).addRow(MAIN_MENU_BUTTON);
    public static final InlineKeyboardMarkup LIST_OF_ANIMALS_USER_MENU_KEYBOARD_NEXT =
            new InlineKeyboardMarkup(NEXT_BUTTON).addRow(MAIN_MENU_BUTTON);
    public static final InlineKeyboardMarkup LIST_OF_ANIMALS_USER_MENU_KEYBOARD_PREV_NEXT =
            new InlineKeyboardMarkup(PREV_BUTTON, NEXT_BUTTON_1).addRow(MAIN_MENU_BUTTON);
    public static final InlineKeyboardMarkup SEND_REPORT_USER_MENU_KEYBOARD =
            new InlineKeyboardMarkup(SELECT_ANIMAL_TO_REPORT_BUTTON).addRow(CALL_VOLUNTEER_BUTTON).addRow(BACK_BUTTON);
    public static final InlineKeyboardMarkup SELECT_ANIMAL_TO_REPORT_USER_MENU_KEYBOARD =
            new InlineKeyboardMarkup(PET_1_BUTTON).addRow(PET_2_BUTTON).addRow(PET_3_BUTTON).addRow(BACK_BUTTON_1);
    public static final InlineKeyboardMarkup VOLUNTEER_MAIN_MENU =
            new InlineKeyboardMarkup(CHECK_REPORTS_BUTTON).addRow(OVERDUE_REPORT_BUTTON).addRow(DECIDE_ON_PROBATION_BUTTON)
                    .addRow(AT_WORK_BUTTON).addRow(NOT_AT_WORK_BUTTON);
    public static final InlineKeyboardMarkup SHOW_REPORT_VOLUNTEER_MENU_KEYBOARD =
            new InlineKeyboardMarkup(ACCEPT_REPORT_BUTTON).addRow(DENY_REPORT_BUTTON).addRow(FINISH_VIEWING_REPORTS_BUTTON);
    public static final InlineKeyboardMarkup SHOW_PROBATION_VOLUNTEER_MENU_KEYBOARD =
            new InlineKeyboardMarkup(ALLOW_ADOPTION_BUTTON).addRow(REFUSE_ADOPTION_BUTTON).addRow(EXTEND_FOR_FOURTEEN_DAYS_BUTTON)
                    .addRow(EXTEND_FOR_THIRTY_DAYS_BUTTON).addRow(FINISH_VIEWING_BUTTON);
    public static final InlineKeyboardMarkup VOLUNTEER_OVERDUE_REPORTS_MENU = new InlineKeyboardMarkup(BACK_BUTTON);
}

package pro.sky.telegrambot.constants;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserCommand;

public class InlineKeyboardMarkupConstants {
    public static final InlineKeyboardButton catButton = new InlineKeyboardButton("Приют с кошками")
            .callbackData(ShelterType.CAT_SHELTER.name());

    public static final InlineKeyboardButton dogButton = new InlineKeyboardButton("Приют с собаками")
            .callbackData(ShelterType.DOG_SHELTER.name());

    public static final InlineKeyboardButton infoAboutShelterButton = new InlineKeyboardButton("Информация о приюте")
            .callbackData(UserCommand.INFO_ABOUT_SHELTER.name());

    public static final InlineKeyboardButton howAdoptPetButton = new InlineKeyboardButton("Как взять животное из приюта")
            .callbackData(UserCommand.HOW_ADOPT_PET.name());

    public static final InlineKeyboardButton sendReportButton = new InlineKeyboardButton("Отправить отчет")
            .callbackData(UserCommand.SEND_REPORT.name());

    public static final InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера")
            .callbackData(UserCommand.CALL_VOLUNTEER.name());

    public static final InlineKeyboardButton backButton = new InlineKeyboardButton("Назад")
            .callbackData(UserCommand.BACK.name());

    public static final InlineKeyboardButton aboutShelterButton = new InlineKeyboardButton("Расскажи о приюте")
            .callbackData(UserCommand.ABOUT_PET_SHELTER.name());

    public static final InlineKeyboardButton scheduleAndAddressButton = new InlineKeyboardButton("Расписание и адрес")
            .callbackData(UserCommand.SCHEDULE_AND_ADDRESS.name());

    public static final InlineKeyboardButton locationMapButton = new InlineKeyboardButton("Схема проезда")
            .callbackData(UserCommand.LOCATION_MAP.name());

    public static final InlineKeyboardButton registrationPassButton = new InlineKeyboardButton("Контакты для оформления пропуска на машину")
            .callbackData(UserCommand.REGISTRATION_PASS.name());

    public static final InlineKeyboardButton safetyPrecautions = new InlineKeyboardButton("Рекомендации по технике беопасности")
            .callbackData(UserCommand.SAFETY_PRECAUTIONS.name());

    public static final InlineKeyboardButton userContactsButton = new InlineKeyboardButton("Оставить контакты для связи");

    InlineKeyboardButton listOfAnimalsButton = new InlineKeyboardButton("Список животных")
            .callbackData(UserCommand.LIST_OF_ANIMALS.name());

    InlineKeyboardButton rulesForMeetingButton = new InlineKeyboardButton("Правила знакомства с животным")
            .callbackData(UserCommand.RULES_FOR_MEETING.name());

    InlineKeyboardButton documentsButton = new InlineKeyboardButton("Документы для усыновления")
            .callbackData(UserCommand.DOCUMENTS.name());

    InlineKeyboardButton transportationRulesButton = new InlineKeyboardButton("Рекомендации по транспортировке")
            .callbackData(UserCommand.TRANSPORTATION_RULES.name());

    InlineKeyboardButton homeImprovementForSmallPetButton = new InlineKeyboardButton("Рекомендации по обустойству дома для щенка")
            .callbackData(UserCommand.HOME_IMPROVEMENT_FOR_SMALL_PET.name());

    InlineKeyboardButton homeImprovementForBigPetButton = new InlineKeyboardButton("Рекомендации по обустойству дома для взрослого животного")
            .callbackData(UserCommand.HOME_IMPROVEMENT_FOR_BIG_PET.name());

    InlineKeyboardButton homeImprovementForPetWithDisabilitiesButton = new InlineKeyboardButton("Рекомендации по обустойству дома для животного с ограниченными возможностями")
            .callbackData(UserCommand.HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES.name());

    InlineKeyboardButton adviceFromDogHandlersButton = new InlineKeyboardButton("Советы от кинолога")
            .callbackData(UserCommand.ADVICE_FROM_DOG_HANDLERS.name());

    InlineKeyboardButton dogHandlersButton = new InlineKeyboardButton("Проверенные кинологи")
            .callbackData(UserCommand.DOG_HANDLERS.name());

    InlineKeyboardButton rejectionReasonButton = new InlineKeyboardButton("Причины отказа в усыновлении")
            .callbackData(UserCommand.REJECTION_REASON.name());

    public static final InlineKeyboardMarkup CHOOSE_SHELTER_USER_MENU_KEYBOARD = new InlineKeyboardMarkup(catButton, dogButton);
    public static final InlineKeyboardMarkup USER_MAIN_MENU_KEYBOARD =
            new InlineKeyboardMarkup(infoAboutShelterButton)
                    .addRow(howAdoptPetButton)
                    .addRow(sendReportButton)
                    .addRow(callVolunteerButton)
                    .addRow(backButton);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_INFO_ABOUT_SHELTER =
            new InlineKeyboardMarkup(aboutShelterButton)
                    .addRow(scheduleAndAddressButton)
                    .addRow(locationMapButton)
                    .addRow(registrationPassButton)
                    .addRow(safetyPrecautions)
                    .addRow(userContactsButton)
                    .addRow(callVolunteerButton)
                    .addRow(backButton);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_ABOUT_PET_SHELTER =
            new InlineKeyboardMarkup(scheduleAndAddressButton)
                    .addRow(locationMapButton)
                    .addRow(registrationPassButton)
                    .addRow(safetyPrecautions)
                    .addRow(userContactsButton)
                    .addRow(callVolunteerButton)
                    .addRow(backButton);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_SCHEDULE_AND_ADDRESS =
            new InlineKeyboardMarkup(aboutShelterButton)
                    .addRow(locationMapButton)
                    .addRow(registrationPassButton)
                    .addRow(safetyPrecautions)
                    .addRow(userContactsButton)
                    .addRow(callVolunteerButton)
                    .addRow(backButton);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_LOCATION_MAP =
            new InlineKeyboardMarkup(aboutShelterButton)
                    .addRow(scheduleAndAddressButton)
                    .addRow(registrationPassButton)
                    .addRow(safetyPrecautions)
                    .addRow(userContactsButton)
                    .addRow(callVolunteerButton)
                    .addRow(backButton);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_REGISTRATION_PASS =
            new InlineKeyboardMarkup(aboutShelterButton)
                    .addRow(scheduleAndAddressButton)
                    .addRow(locationMapButton)
                    .addRow(safetyPrecautions)
                    .addRow(userContactsButton)
                    .addRow(callVolunteerButton)
                    .addRow(backButton);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_SAFETY_PRECAUTIONS =
            new InlineKeyboardMarkup(aboutShelterButton)
                    .addRow(scheduleAndAddressButton)
                    .addRow(locationMapButton)
                    .addRow(registrationPassButton)
                    .addRow(userContactsButton)
                    .addRow(callVolunteerButton)
                    .addRow(backButton);
    public static final InlineKeyboardMarkup INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_CALL_VOLUNTEER =
            new InlineKeyboardMarkup(aboutShelterButton)
                    .addRow(scheduleAndAddressButton)
                    .addRow(locationMapButton)
                    .addRow(registrationPassButton)
                    .addRow(safetyPrecautions)
                    .addRow(userContactsButton)
                    .addRow(backButton);
    public static final InlineKeyboardMarkup HOW_ADOPT_PET_USER_MENU_KEYBOARD = null;
    public static final InlineKeyboardMarkup LIST_OF_ANIMALS_USER_MENU_KEYBOARD = null;
    public static final InlineKeyboardMarkup SEND_REPORT_USER_MENU_KEYBOARD = null;
    public static final InlineKeyboardMarkup SELECT_ANIMAL_TO_REPORT_USER_MENU_KEYBOARD = null;
    public static final InlineKeyboardMarkup VOLUNTEER_MAIN_MENU = null;
    public static final InlineKeyboardMarkup SHOW_REPORT_VOLUNTEER_MENU_KEYBOARD = null;
    public static final InlineKeyboardMarkup SHOW_PROBATION_VOLUNTEER_MENU_KEYBOARD = null;
    public static final InlineKeyboardMarkup VOLUNTEER_OVERDUE_REPORTS_MENU = null;
}

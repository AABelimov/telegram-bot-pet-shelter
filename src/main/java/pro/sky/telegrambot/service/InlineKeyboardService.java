package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserCommand;
import pro.sky.telegrambot.enums.VolunteerCommand;
import pro.sky.telegrambot.model.Pet;

import java.util.ArrayList;
import java.util.List;

@Service
public class InlineKeyboardService {

    public InlineKeyboardMarkup getChooseShelterUserMenuKeyboard() {
        InlineKeyboardButton catButton = new InlineKeyboardButton("Приют с кошками")
                .callbackData(ShelterType.CAT_SHELTER.name());

        InlineKeyboardButton dogButton = new InlineKeyboardButton("Приют с собаками")
                .callbackData(ShelterType.DOG_SHELTER.name());

        return new InlineKeyboardMarkup(catButton, dogButton);
    }

    public InlineKeyboardMarkup getUserMainMenuKeyboard() {
        InlineKeyboardButton infoAboutShelterButton = new InlineKeyboardButton("Информация о приюте")
                .callbackData(UserCommand.INFO_ABOUT_SHELTER.name());

        InlineKeyboardButton howAdoptPetButton = new InlineKeyboardButton("Как взять животное из приюта")
                .callbackData(UserCommand.HOW_ADOPT_PET.name());

        InlineKeyboardButton sendReportButton = new InlineKeyboardButton("Отправить отчет")
                .callbackData(UserCommand.SEND_REPORT.name());

        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера")
                .callbackData(UserCommand.CALL_VOLUNTEER.name());

        InlineKeyboardButton backButton = new InlineKeyboardButton("Назад")
                .callbackData(UserCommand.BACK.name());

        return new InlineKeyboardMarkup(infoAboutShelterButton)
                .addRow(howAdoptPetButton)
                .addRow(sendReportButton)
                .addRow(callVolunteerButton)
                .addRow(backButton);
    }

    public InlineKeyboardMarkup getInfoAboutShelterUserMenuKeyboard(UserCommand userCommand) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton aboutShelterButton = new InlineKeyboardButton("Расскажи о приюте")
                .callbackData(UserCommand.ABOUT_PET_SHELTER.name());

        InlineKeyboardButton scheduleAndAddressButton = new InlineKeyboardButton("Расписание и адрес")
                .callbackData(UserCommand.SCHEDULE_AND_ADDRESS.name());

        InlineKeyboardButton locationMapButton = new InlineKeyboardButton("Схема проезда")
                .callbackData(UserCommand.LOCATION_MAP.name());

        InlineKeyboardButton registrationPassButton = new InlineKeyboardButton("Контакты для оформления пропуска")
                .callbackData(UserCommand.REGISTRATION_PASS.name());

        InlineKeyboardButton safetyPrecautions = new InlineKeyboardButton("Рекомендации по технике безопасности")
                .callbackData(UserCommand.SAFETY_PRECAUTIONS.name());

        InlineKeyboardButton userContactsButton = new InlineKeyboardButton("Оставить контакты для связи")
                .callbackData(UserCommand.SHARE_CONTACTS.name());

        InlineKeyboardButton backButton = new InlineKeyboardButton("Назад")
                .callbackData(UserCommand.BACK.name());

        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера")
                .callbackData(UserCommand.CALL_VOLUNTEER.name());

        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>(List.of(
                aboutShelterButton,
                scheduleAndAddressButton,
                locationMapButton,
                registrationPassButton,
                safetyPrecautions,
                userContactsButton,
                callVolunteerButton,
                backButton
        ));

        switch (userCommand) {
            case ABOUT_PET_SHELTER:
                inlineKeyboardButtons.remove(aboutShelterButton);
                break;
            case SCHEDULE_AND_ADDRESS:
                inlineKeyboardButtons.remove(scheduleAndAddressButton);
                break;
            case LOCATION_MAP:
                inlineKeyboardButtons.remove(locationMapButton);
                break;
            case REGISTRATION_PASS:
                inlineKeyboardButtons.remove(registrationPassButton);
                break;
            case SAFETY_PRECAUTIONS:
                inlineKeyboardButtons.remove(safetyPrecautions);
                break;
            case CALL_VOLUNTEER:
                inlineKeyboardButtons.remove(callVolunteerButton);
                break;
        }

        inlineKeyboardButtons.forEach(inlineKeyboardMarkup::addRow);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getHowAdoptPetUserMenuKeyboard(UserCommand userCommand, ShelterType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton listOfAnimalsButton = new InlineKeyboardButton("Список животных")
                .callbackData(UserCommand.LIST_OF_PETS.name());

        InlineKeyboardButton rulesForMeetingButton = new InlineKeyboardButton("Правила знакомства с животным")
                .callbackData(UserCommand.RULES_FOR_MEETING.name());

        InlineKeyboardButton documentsButton = new InlineKeyboardButton("Документы для усыновления")
                .callbackData(UserCommand.DOCUMENTS.name());

        InlineKeyboardButton transportationRulesButton = new InlineKeyboardButton("Рекомендации по транспортировке")
                .callbackData(UserCommand.TRANSPORTATION_RULES.name());

        InlineKeyboardButton homeImprovementForSmallPetButton = new InlineKeyboardButton("Обустройство дома для молодого животного")
                .callbackData(UserCommand.HOME_IMPROVEMENT_FOR_SMALL_PET.name());

        InlineKeyboardButton homeImprovementForBigPetButton = new InlineKeyboardButton("Обустройство дома для взрослого животного")
                .callbackData(UserCommand.HOME_IMPROVEMENT_FOR_BIG_PET.name());

        InlineKeyboardButton homeImprovementForPetWithDisabilitiesButton = new InlineKeyboardButton("Обустройство дома для животного инвалида")
                .callbackData(UserCommand.HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES.name());

        InlineKeyboardButton adviceFromDogHandlersButton = new InlineKeyboardButton("Советы от кинолога")
                .callbackData(UserCommand.ADVICE_FROM_DOG_HANDLERS.name());

        InlineKeyboardButton dogHandlersButton = new InlineKeyboardButton("Проверенные кинологи")
                .callbackData(UserCommand.DOG_HANDLERS.name());

        InlineKeyboardButton rejectionReasonButton = new InlineKeyboardButton("Причины отказа в усыновлении")
                .callbackData(UserCommand.REJECTION_REASON.name());

        InlineKeyboardButton userContactsButton = new InlineKeyboardButton("Оставить контакты для связи")
                .callbackData(UserCommand.SHARE_CONTACTS.name());

        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера")
                .callbackData(UserCommand.CALL_VOLUNTEER.name());

        InlineKeyboardButton backButton = new InlineKeyboardButton("Назад")
                .callbackData(UserCommand.BACK.name());

        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>(List.of(
                listOfAnimalsButton,
                rulesForMeetingButton,
                documentsButton,
                transportationRulesButton,
                homeImprovementForSmallPetButton,
                homeImprovementForBigPetButton,
                homeImprovementForPetWithDisabilitiesButton,
                adviceFromDogHandlersButton,
                dogHandlersButton,
                rejectionReasonButton,
                userContactsButton,
                callVolunteerButton,
                backButton
        ));

        if (shelterType.equals(ShelterType.CAT_SHELTER)) {
            inlineKeyboardButtons.remove(adviceFromDogHandlersButton);
            inlineKeyboardButtons.remove(dogHandlersButton);
        }

        switch (userCommand) {
            case LIST_OF_PETS:
                inlineKeyboardButtons.remove(listOfAnimalsButton);
                break;
            case RULES_FOR_MEETING:
                inlineKeyboardButtons.remove(rulesForMeetingButton);
                break;
            case DOCUMENTS:
                inlineKeyboardButtons.remove(documentsButton);
                break;
            case TRANSPORTATION_RULES:
                inlineKeyboardButtons.remove(transportationRulesButton);
                break;
            case HOME_IMPROVEMENT_FOR_SMALL_PET:
                inlineKeyboardButtons.remove(homeImprovementForSmallPetButton);
                break;
            case HOME_IMPROVEMENT_FOR_BIG_PET:
                inlineKeyboardButtons.remove(homeImprovementForBigPetButton);
                break;
            case HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES:
                inlineKeyboardButtons.remove(homeImprovementForPetWithDisabilitiesButton);
                break;
            case ADVICE_FROM_DOG_HANDLERS:
                inlineKeyboardButtons.remove(adviceFromDogHandlersButton);
                break;
            case DOG_HANDLERS:
                inlineKeyboardButtons.remove(dogHandlersButton);
                break;
            case REJECTION_REASON:
                inlineKeyboardButtons.remove(rejectionReasonButton);
                break;
            case CALL_VOLUNTEER:
                inlineKeyboardButtons.remove(callVolunteerButton);
                break;
        }

        inlineKeyboardButtons.forEach(inlineKeyboardMarkup::addRow);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getListOfAnimalsUserMenuKeyboard(int currentPage, int maxPage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton nextButton = new InlineKeyboardButton("следующий").callbackData(Integer.toString(currentPage + 1));
        InlineKeyboardButton prevButton = new InlineKeyboardButton("предыдущий").callbackData(Integer.toString(currentPage - 1));
        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("Главное меню").callbackData("-1");

        if (currentPage > 0 && currentPage < maxPage) {
            inlineKeyboardMarkup.addRow(prevButton, nextButton);
        } else if (currentPage == 0 && currentPage < maxPage){
            inlineKeyboardMarkup.addRow(nextButton);
        } else if (currentPage == maxPage && currentPage != 0){
            inlineKeyboardMarkup.addRow(prevButton);
        }
        inlineKeyboardMarkup.addRow(mainMenuButton);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getSendReportUserMenuKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton selectAnimalToReportButton = new InlineKeyboardButton("Выбрать животное для отчета")
                .callbackData(UserCommand.SELECT_ANIMAL_TO_REPORT.name());

        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера")
                .callbackData(UserCommand.CALL_VOLUNTEER.name());

        InlineKeyboardButton backButton = new InlineKeyboardButton("Назад")
                .callbackData(UserCommand.BACK.name());


        inlineKeyboardMarkup.addRow(selectAnimalToReportButton);
        inlineKeyboardMarkup.addRow(callVolunteerButton);
        inlineKeyboardMarkup.addRow(backButton);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getSelectAnimalToReportUserMenuKeyboard(List<Pet> pets) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton backButton = new InlineKeyboardButton("Назад")
                .callbackData("-1");

        pets.forEach(pet -> inlineKeyboardMarkup.addRow(new InlineKeyboardButton(pet.getName()).callbackData(pet.getId().toString())));
        inlineKeyboardMarkup.addRow(backButton);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getVolunteerMainMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton checkReportsButton = new InlineKeyboardButton("Проверить отчеты")
                .callbackData(VolunteerCommand.CHECK_REPORTS.name());

        InlineKeyboardButton overdueReportButton = new InlineKeyboardButton("Посмотреть должников по отчетам")
                .callbackData(VolunteerCommand.OVERDUE_REPORTS.name());

        InlineKeyboardButton decideOnProbationButton = new InlineKeyboardButton("Принять решение по усыновлению")
                .callbackData(VolunteerCommand.DECIDE_ON_PROBATION.name());

        InlineKeyboardButton atWorkButton = new InlineKeyboardButton("На работе")
                .callbackData(VolunteerCommand.AT_WORK.name());

        InlineKeyboardButton notAtWorkButton = new InlineKeyboardButton("Не на работе")
                .callbackData(VolunteerCommand.NOT_AT_WORK.name());

        inlineKeyboardMarkup.addRow(checkReportsButton);
        inlineKeyboardMarkup.addRow(overdueReportButton);
        inlineKeyboardMarkup.addRow(decideOnProbationButton);
        inlineKeyboardMarkup.addRow(atWorkButton);
        inlineKeyboardMarkup.addRow(notAtWorkButton);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getShowReportVolunteerMenuKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton acceptReportButton = new InlineKeyboardButton("Принять отчет")
                .callbackData(VolunteerCommand.ACCEPT_REPORT.name());

        InlineKeyboardButton denyReportButton = new InlineKeyboardButton("Забраковать отчет")
                .callbackData(VolunteerCommand.DENY_REPORT.name());

        InlineKeyboardButton finishViewingReportsButton = new InlineKeyboardButton("Закончить просмотр отчетов")
                .callbackData(VolunteerCommand.FINISH_VIEWING_REPORTS.name());

        inlineKeyboardMarkup.addRow(acceptReportButton);
        inlineKeyboardMarkup.addRow(denyReportButton);
        inlineKeyboardMarkup.addRow(finishViewingReportsButton);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getShowProbationVolunteerMenuKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton allowAdoptionButton = new InlineKeyboardButton("Разрешить усыновление")
                .callbackData(VolunteerCommand.ALLOW_ADOPTION.name());

        InlineKeyboardButton refuseAdoptionButton = new InlineKeyboardButton("Отказать в усыновлении")
                .callbackData(VolunteerCommand.REFUSE_ADOPTION.name());

        InlineKeyboardButton extendForFourteenDaysButton = new InlineKeyboardButton("Продлить на 14 дней")
                .callbackData(VolunteerCommand.EXTEND_FOR_FOURTEEN_DAYS.name());

        InlineKeyboardButton extendForThirtyDaysButton = new InlineKeyboardButton("Продлить на 30 дней")
                .callbackData(VolunteerCommand.EXTEND_FOR_THIRTY_DAYS.name());

        InlineKeyboardButton finishViewingButton = new InlineKeyboardButton("Закончить просмотр")
                .callbackData(VolunteerCommand.FINISH_VIEWING_DECIDE_ON_PROBATION.name());

        inlineKeyboardMarkup.addRow(allowAdoptionButton);
        inlineKeyboardMarkup.addRow(refuseAdoptionButton);
        inlineKeyboardMarkup.addRow(extendForFourteenDaysButton);
        inlineKeyboardMarkup.addRow(extendForThirtyDaysButton);
        inlineKeyboardMarkup.addRow(finishViewingButton);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getVolunteerOverdueReportsMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton backButton = new InlineKeyboardButton("Назад")
                .callbackData(VolunteerCommand.BACK.name());

        return inlineKeyboardMarkup.addRow(backButton);
    }
}

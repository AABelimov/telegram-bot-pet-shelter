package pro.sky.telegrambot.service;

import org.junit.jupiter.api.Test;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserCommand;

import static org.junit.jupiter.api.Assertions.*;
import static pro.sky.telegrambot.constants.InlineKeyboardMarkupConstants.*;
import static pro.sky.telegrambot.constants.PetConstants.*;

class InlineKeyboardServiceTest {

    private final InlineKeyboardService out = new InlineKeyboardService();

    @Test
    void testGetChooseShelterUserMenuKeyboard() {
        assertEquals(CHOOSE_SHELTER_USER_MENU_KEYBOARD, out.getChooseShelterUserMenuKeyboard());
    }

    @Test
    void testGetUserMainMenuKeyboard() {
        assertEquals(USER_MAIN_MENU_KEYBOARD, out.getUserMainMenuKeyboard());
    }

    @Test
    void testGetInfoAboutShelterUserMenuKeyboard() {
        assertEquals(INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_INFO_ABOUT_SHELTER, out.getInfoAboutShelterUserMenuKeyboard(UserCommand.INFO_ABOUT_SHELTER));
        assertEquals(INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_ABOUT_PET_SHELTER, out.getInfoAboutShelterUserMenuKeyboard(UserCommand.ABOUT_PET_SHELTER));
        assertEquals(INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_SCHEDULE_AND_ADDRESS, out.getInfoAboutShelterUserMenuKeyboard(UserCommand.SCHEDULE_AND_ADDRESS));
        assertEquals(INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_LOCATION_MAP, out.getInfoAboutShelterUserMenuKeyboard(UserCommand.LOCATION_MAP));
        assertEquals(INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_REGISTRATION_PASS, out.getInfoAboutShelterUserMenuKeyboard(UserCommand.REGISTRATION_PASS));
        assertEquals(INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_SAFETY_PRECAUTIONS, out.getInfoAboutShelterUserMenuKeyboard(UserCommand.SAFETY_PRECAUTIONS));
        assertEquals(INFO_ABOUT_SHELTER_USER_MENU_KEYBOARD_CALL_VOLUNTEER, out.getInfoAboutShelterUserMenuKeyboard(UserCommand.CALL_VOLUNTEER));
    }

    @Test
    void testGetHowAdoptPetUserMenuKeyboard() {
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOW_ADOPT_PET_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.HOW_ADOPT_PET, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_LIST_OF_ANIMALS_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.LIST_OF_ANIMALS, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_RULES_FOR_MEETING_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.RULES_FOR_MEETING, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_DOCUMENTS_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.DOCUMENTS, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_TRANSPORTATION_RULES_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.TRANSPORTATION_RULES, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_SMALL_PET_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.HOME_IMPROVEMENT_FOR_SMALL_PET, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_BIG_PET_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.HOME_IMPROVEMENT_FOR_BIG_PET, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_ADVICE_FROM_DOG_HANDLERS_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.ADVICE_FROM_DOG_HANDLERS, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_DOG_HANDLERS_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.DOG_HANDLERS, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_REJECTION_REASON_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.REJECTION_REASON, ShelterType.DOG_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_CALL_VOLUNTEER_DOG_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.CALL_VOLUNTEER, ShelterType.DOG_SHELTER));

        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOW_ADOPT_PET_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.HOW_ADOPT_PET, ShelterType.CAT_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_LIST_OF_ANIMALS_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.LIST_OF_ANIMALS, ShelterType.CAT_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_RULES_FOR_MEETING_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.RULES_FOR_MEETING, ShelterType.CAT_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_DOCUMENTS_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.DOCUMENTS, ShelterType.CAT_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_TRANSPORTATION_RULES_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.TRANSPORTATION_RULES, ShelterType.CAT_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_SMALL_PET_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.HOME_IMPROVEMENT_FOR_SMALL_PET, ShelterType.CAT_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_BIG_PET_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.HOME_IMPROVEMENT_FOR_BIG_PET, ShelterType.CAT_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES, ShelterType.CAT_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_REJECTION_REASON_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.REJECTION_REASON, ShelterType.CAT_SHELTER));
        assertEquals(HOW_ADOPT_PET_USER_MENU_KEYBOARD_CALL_VOLUNTEER_CAT_SHELTER, out.getHowAdoptPetUserMenuKeyboard(UserCommand.CALL_VOLUNTEER, ShelterType.CAT_SHELTER));
    }

    @Test
    void testGetListOfAnimalsUserMenuKeyboard() {
        assertEquals(LIST_OF_ANIMALS_USER_MENU_KEYBOARD_NEXT, out.getListOfAnimalsUserMenuKeyboard(0, 2));
        assertEquals(LIST_OF_ANIMALS_USER_MENU_KEYBOARD_PREV_NEXT, out.getListOfAnimalsUserMenuKeyboard(1, 2));
        assertEquals(LIST_OF_ANIMALS_USER_MENU_KEYBOARD_PREV, out.getListOfAnimalsUserMenuKeyboard(2, 2));
    }

    @Test
    void testGetSendReportUserMenuKeyboard() {
        assertEquals(SEND_REPORT_USER_MENU_KEYBOARD, out.getSendReportUserMenuKeyboard());
    }

    @Test
    void testGetSelectAnimalToReportUserMenuKeyboard() {
        assertEquals(SELECT_ANIMAL_TO_REPORT_USER_MENU_KEYBOARD, out.getSelectAnimalToReportUserMenuKeyboard(PET_LIST));
    }

    @Test
    void testGetVolunteerMainMenu() {
        assertEquals(VOLUNTEER_MAIN_MENU, out.getVolunteerMainMenu());
    }

    @Test
    void testGetShowReportVolunteerMenuKeyboard() {
        assertEquals(SHOW_REPORT_VOLUNTEER_MENU_KEYBOARD, out.getShowReportVolunteerMenuKeyboard());
    }

    @Test
    void testGetShowProbationVolunteerMenuKeyboard() {
        assertEquals(SHOW_PROBATION_VOLUNTEER_MENU_KEYBOARD, out.getShowProbationVolunteerMenuKeyboard());
    }

    @Test
    void testGetVolunteerOverdueReportsMenu() {
        assertEquals(VOLUNTEER_OVERDUE_REPORTS_MENU, out.getVolunteerOverdueReportsMenu());
    }
}
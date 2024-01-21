package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.*;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.service.*;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class handles commands coming from the user
 */
@Component
public class UserDataCallbackQueryHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataCallbackQueryHandler.class);
    private final String locationMapDogPetShelter;
    private final String locationMapCatPetShelter;
    private final InlineKeyboardService inlineKeyboardService;
    private final TelegramBotService telegramBotService;
    private final UserService userService;
    private final VolunteerService volunteerService;
    private final MessageService messageService;
    private final PetService petService;
    private final PetReportService petReportService;
    private final ProbationService probationService;
    private final UserTextMessageHandler userTextMessageHandler;

    public UserDataCallbackQueryHandler(@Value("${path.to.photo.with.location.map.for.dog.pet.shelter}") String locationMapDogPetShelter,
                                        @Value("${path.to.photo.with.location.map.for.cat.pet.shelter}") String locationMapCatPetShelter,
                                        InlineKeyboardService inlineKeyboardService,
                                        TelegramBotService telegramBotService,
                                        UserService userService,
                                        VolunteerService volunteerService,
                                        MessageService messageService,
                                        PetService petService,
                                        PetReportService petReportService,
                                        ProbationService probationService,
                                        UserTextMessageHandler userTextMessageHandler) {
        this.locationMapDogPetShelter = locationMapDogPetShelter;
        this.locationMapCatPetShelter = locationMapCatPetShelter;
        this.inlineKeyboardService = inlineKeyboardService;
        this.telegramBotService = telegramBotService;
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.messageService = messageService;
        this.petService = petService;
        this.petReportService = petReportService;
        this.probationService = probationService;
        this.userTextMessageHandler = userTextMessageHandler;
    }

    /**
     * This method handles commands from the choose shelter menu
     *
     * @param userId    ID of the user who sent the message
     * @param messageId ID of the message sent by the user
     * @param data      data from a callback query that belonged to a specific button
     */
    public void handleChooseShelter(Long userId, Integer messageId, String data) {
        ShelterType shelterType = ShelterType.valueOf(data);
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getUserMainMenuKeyboard();
        String textMessage = messageService.getMessage("USER_MAIN_MENU", shelterType);

        telegramBotService.editInlineKeyboard(userId, messageId, textMessage, inlineKeyboardMarkup);
        userService.setSelectedShelter(userId, shelterType);
        userService.setUserState(userId, UserState.MAIN_MENU);
    }

    /**
     * This method handles commands from the main menu
     *
     * @param userId
     * @param messageId
     * @param data
     */
    public void handleMainMenu(Long userId, Integer messageId, String data) {
        UserCommand userCommand = UserCommand.valueOf(data);
        ShelterType shelterType = ShelterType.valueOf(userService.getSelectedShelter(userId));
        InlineKeyboardMarkup inlineKeyboardMarkup;

        switch (userCommand) {
            case INFO_ABOUT_SHELTER:
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterUserMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "INFO_ABOUT_SHELTER");
                userService.setUserState(userId, UserState.INFO_ABOUT_SHELTER);
                break;
            case HOW_ADOPT_PET:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "HOW_ADOPT_PET");
                userService.setUserState(userId, UserState.HOW_ADOPT_PET);
                break;
            case SEND_REPORT:
                inlineKeyboardMarkup = inlineKeyboardService.getSendReportUserMenuKeyboard();
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "SEND_REPORT");
                userService.setUserState(userId, UserState.SEND_REPORT);
                break;
            case CALL_VOLUNTEER:
                startConversation(userId, messageId);
                break;
            case BACK:
                handleBackCommand(userId, messageId);
                break;
        }
    }

    /**
     * This method handles commands from about shelter menu
     *
     * @param userId
     * @param messageId
     * @param data
     */
    public void handleInfoAboutShelter(Long userId, Integer messageId, String data) {
        UserCommand userCommand = UserCommand.valueOf(data);
        ShelterType shelterType = ShelterType.valueOf(userService.getSelectedShelter(userId));
        InlineKeyboardMarkup inlineKeyboardMarkup;

        switch (userCommand) {
            case ABOUT_PET_SHELTER:
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterUserMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "ABOUT_PET_SHELTER");
                break;
            case SCHEDULE_AND_ADDRESS:
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterUserMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "SCHEDULE_AND_ADDRESS");
                break;
            case LOCATION_MAP:
                locationMap(userId, messageId, shelterType);
                break;
            case REGISTRATION_PASS:
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterUserMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "REGISTRATION_PASS");
                break;
            case SAFETY_PRECAUTIONS:
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterUserMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "SAFETY_PRECAUTIONS");
                break;
            case SHARE_CONTACTS:
                shareContacts(userId, messageId);
                break;
            case CALL_VOLUNTEER:
                startConversation(userId, messageId);
                break;
            case BACK:
                handleBackCommand(userId, messageId);
                break;
            case MAIN_MENU:
                String selectedShelter = userService.getSelectedShelter(userId);
                handleChooseShelter(userId, messageId, selectedShelter);
                break;
        }
    }

    public void handleHowAdoptPet(Long userId, Integer messageId, String data) {
        UserCommand userCommand = UserCommand.valueOf(data);
        ShelterType shelterType = ShelterType.valueOf(userService.getSelectedShelter(userId));
        InlineKeyboardMarkup inlineKeyboardMarkup;

        switch (userCommand) {
            case LIST_OF_ANIMALS:
                listOfAnimals(userId, messageId, shelterType, 0);
                break;
            case RULES_FOR_MEETING:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "RULES_FOR_MEETING");
                break;
            case DOCUMENTS:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "DOCUMENTS");
                break;
            case TRANSPORTATION_RULES:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "TRANSPORTATION_RULES");
                break;
            case HOME_IMPROVEMENT_FOR_SMALL_PET:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "HOME_IMPROVEMENT_FOR_SMALL_PET");
                break;
            case HOME_IMPROVEMENT_FOR_BIG_PET:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "HOME_IMPROVEMENT_FOR_BIG_PET");
                break;
            case HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES");
                break;
            case ADVICE_FROM_DOG_HANDLERS:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "ADVICE_FROM_DOG_HANDLERS");
                break;
            case DOG_HANDLERS:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "DOG_HANDLERS");
                break;
            case REJECTION_REASON:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "REJECTION_REASON");
                break;
            case SHARE_CONTACTS:
                shareContacts(userId, messageId);
                break;
            case CALL_VOLUNTEER:
                startConversation(userId, messageId);
                break;
            case BACK:
                handleBackCommand(userId, messageId);
                break;
            case MAIN_MENU:
                String selectedShelter = userService.getSelectedShelter(userId);
                handleChooseShelter(userId, messageId, selectedShelter);
                break;
        }
    }

    public void handleSendReport(Long userId, Integer messageId, String data) {
        UserCommand userCommand = UserCommand.valueOf(data);
        ShelterType shelterType = ShelterType.valueOf(userService.getSelectedShelter(userId));

        switch (userCommand) {
            case SELECT_ANIMAL_TO_REPORT:
                userService.setUserState(userId, UserState.SELECT_ANIMAL_TO_REPORT);
                selectAnimalToReport(userId, messageId, shelterType);
                break;
            case CALL_VOLUNTEER:
                startConversation(userId, messageId);
                break;
            case BACK:
                handleBackCommand(userId, messageId);
                break;
            case MAIN_MENU:
                String selectedShelter = userService.getSelectedShelter(userId);
                handleChooseShelter(userId, messageId, selectedShelter);
                break;
        }
    }

    public void handleSelectAnimalToReport(Long userId, Integer messageId, String data) {
        Long petId = Long.parseLong(data);
        User user = userService.getUser(userId);
        Pet pet;
        ShelterType shelterType;
        PetReport petReport;
        Probation probation;
        Volunteer volunteer;
        InlineKeyboardMarkup inlineKeyboardMarkup;

        if (petId == -1) {
            handleMainMenu(userId, messageId, UserCommand.SEND_REPORT.name());
        } else {
            pet = petService.getPet(petId);
            petReport = petReportService.getReportByPetIdAndState(petId, PetReportState.FILLING);
            shelterType = pet.getKindOfPet().equals("CAT") ? ShelterType.CAT_SHELTER : ShelterType.DOG_SHELTER;
            probation = probationService.getProbationByUserIdAndShelterTypeAndState(userId, shelterType, ProbationState.FILLING_REPORT);

            if (probation != null && probation.getPet().getId().equals(petId)) {
                petReportService.fillReport(userId, petReport);
            }

            if (probation != null && !probation.getPet().getId().equals(petId)) {
                inlineKeyboardMarkup = inlineKeyboardService.getSendReportUserMenuKeyboard();
                telegramBotService.editInlineKeyboard(userId, messageId, "сначала заполните до конца отчет для " + probation.getPet().getName(), inlineKeyboardMarkup);
                userService.setUserState(userId, UserState.SEND_REPORT);
            }

            if (petReport == null && probation == null) {
                probation = probationService.getProbationByPetId(petId);
                volunteer = probation.getVolunteer();
                petReport = petReportService.createPetReport(pet, user, volunteer, shelterType);
                probationService.setProbationState(probation.getId(), ProbationState.FILLING_REPORT);
                petReportService.fillReport(userId, petReport);
            }
        }
    }

    public void handleViewingAnimals(Long userId, Integer messageId, String data) {
        ShelterType shelterType = ShelterType.valueOf(userService.getSelectedShelter(userId));
        int page = Integer.parseInt(data);
        String text = messageService.getMessage("USER_MAIN_MENU", shelterType);

        if (page == -1) {
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getUserMainMenuKeyboard();
            telegramBotService.deleteMessage(userId, messageId);
            telegramBotService.sendInlineKeyboard(userId, text, inlineKeyboardMarkup);
            userService.setUserState(userId, UserState.MAIN_MENU);
        } else {
            listOfAnimals(userId, messageId, shelterType, page);
        }
    }

    /**
     * This method handles the back command
     *
     * @param userId
     * @param messageId
     */
    private void handleBackCommand(Long userId, Integer messageId) {
        UserState userState = userService.getUserState(userId);

        switch (userState) {
            case MAIN_MENU:
                userTextMessageHandler.handleStart(userId, "/start", messageId);
                break;
            case INFO_ABOUT_SHELTER:
            case HOW_ADOPT_PET:
            case SEND_REPORT:
                String selectedShelter = userService.getSelectedShelter(userId);
                handleChooseShelter(userId, messageId, selectedShelter);
                break;
        }
    }

    public void sendInlineKeyboard(Long userId, Integer messageId, InlineKeyboardMarkup inlineKeyboardMarkup, ShelterType shelterType, String messageTitle) {
        String text = messageService.getMessage(messageTitle, shelterType);
        telegramBotService.editInlineKeyboard(userId, messageId, text, inlineKeyboardMarkup);
    }

    /**
     * This method starts a chat with the volunteer
     *
     * @param userId
     */
    private void startConversation(Long userId, Integer messageId) {
        Volunteer volunteer = volunteerService.getFreeVolunteer();
        User user = userService.getUser(userId);
        UserState userState = UserState.valueOf(user.getState());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        ShelterType shelterType;

        if (volunteer == null) {
            switch (userState) {
                case MAIN_MENU:
                    inlineKeyboardMarkup = inlineKeyboardService.getUserMainMenuKeyboard();
                    break;
                case INFO_ABOUT_SHELTER:
                    inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterUserMenuKeyboard(UserCommand.CALL_VOLUNTEER);
                    break;
                case HOW_ADOPT_PET:
                    shelterType = ShelterType.valueOf(user.getSelectedShelter());
                    inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(UserCommand.CALL_VOLUNTEER, shelterType);
                    break;
                case SEND_REPORT:
                    inlineKeyboardMarkup = inlineKeyboardService.getSendReportUserMenuKeyboard();
                    break;
            }

            telegramBotService.editInlineKeyboard(userId, messageId, "Нет  свободных волонтеров, готовых вам сейчас помоч", inlineKeyboardMarkup);
        } else {
            volunteerService.startConversation(volunteer, user);
            userService.startConversation(userId);

            telegramBotService.sendMessage(userId, messageService.getMessage("START_CONVERSATION_USER"));
            telegramBotService.sendMessage(volunteer.getId(), messageService.getMessage("START_CONVERSATION_VOLUNTEER"));
        }
    }

    /**
     * This method sends a photo with location map
     *
     * @param userId
     * @param messageId
     * @param shelterType
     */
    private void locationMap(Long userId, Integer messageId, ShelterType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterUserMenuKeyboard(UserCommand.LOCATION_MAP);
        String photoPath = shelterType.equals(ShelterType.DOG_SHELTER) ? locationMapDogPetShelter : locationMapCatPetShelter;
        String textMessage = messageService.getMessage("INFO_ABOUT_SHELTER", shelterType);
        String aboutPhoto = messageService.getMessage("LOCATION_SCHEMA", shelterType);
        Path path = Path.of(photoPath);
        File photo = path.toFile();

        if (photo.exists()) {
            telegramBotService.deleteMessage(userId, messageId);
            telegramBotService.sendMessage(userId, aboutPhoto);
            telegramBotService.sendPhoto(userId, photo);
            telegramBotService.sendInlineKeyboard(userId, textMessage, inlineKeyboardMarkup);
        } else {
            telegramBotService.editInlineKeyboard(userId, messageId, "Фото со схемой проезда нет", inlineKeyboardMarkup);
            LOGGER.error("No location map for {}", shelterType.name());
        }
    }

    private void shareContacts(Long userId, Integer messageId) {
        String text = "Напишите свой номер телефона в формате: +7-9**-***-**-**";

        userService.setUserState(userId, UserState.SHARE_CONTACTS);
        telegramBotService.editMessage(userId, messageId, text);
    }

    private void listOfAnimals(Long userId, Integer messageId, ShelterType shelterType, int page) {
        int countPets = (int) petService.countPetsByKindOfPet(shelterType);

        if (countPets > 0) {
            PageRequest pageRequest = PageRequest.of(page, 1);
            Pet pet = petService.getListOfAnimals(shelterType, PetState.WAITING_TO_BE_ADOPTED, pageRequest).get(0);
            String name = pet.getName();
            Path photoPath = Path.of(pet.getPhotoPath());
            File photo = photoPath.toFile();
            String aboutPet = pet.getAboutPet();
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getListOfAnimalsUserMenuKeyboard(page, countPets - 1);
            String text = String.format("Имя: %s\nО себе: %s", name, aboutPet);
            UserState userState = userService.getUserState(userId);

            if (userState.equals(UserState.HOW_ADOPT_PET)) {
                userService.setUserState(userId, UserState.VIEWING_ANIMALS);
                telegramBotService.deleteMessage(userId, messageId);
                telegramBotService.sendInlineKeyboard(userId, photo, text, inlineKeyboardMarkup);
            } else {
                telegramBotService.editInlineKeyboard(userId, messageId, photo, text, inlineKeyboardMarkup);
            }
        } else {
            userService.setUserState(userId, UserState.HOW_ADOPT_PET);
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetUserMenuKeyboard(UserCommand.LIST_OF_ANIMALS, shelterType);
            sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "LIST_OF_ANIMALS_NULL");
        }
    }

    private void selectAnimalToReport(Long userId, Integer messageId, ShelterType shelterType) {
        Probation probation = probationService.getProbationByUserIdAndShelterTypeAndState(userId, shelterType, ProbationState.FILLING_REPORT);
        List<Probation> probationList = probationService.getProbationList(userId, shelterType, ProbationState.WAITING_FOR_A_NEW_REPORT);
        List<Pet> pets;
        InlineKeyboardMarkup inlineKeyboardMarkup;

        if (probation != null) {
            probationList.add(probation);
        }

        pets = probationList.stream()
                .map(Probation::getPet)
                .collect(Collectors.toList());
        inlineKeyboardMarkup = inlineKeyboardService.getSelectAnimalToReportUserMenuKeyboard(pets);
        String text;
        probationList = probationService.getProbationList(userId, shelterType);

        if (probationList.size() == 0) {
            text = "В этом приюте вы не брали животных";
        } else if (pets.size() == 0) {
            text = "В этом приюте за сегодня отчет сдавать не нужно";
        } else {
            text = "Выберете животное, для которого хотите составить отчет";
        }

        telegramBotService.editInlineKeyboard(userId, messageId, text, inlineKeyboardMarkup);
    }
}

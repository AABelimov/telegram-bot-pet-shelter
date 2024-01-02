package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserCommand;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.*;

import java.io.File;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles commands coming from the user
 */
@Component
public class UserCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommandHandler.class);
    private static final Pattern PATTERN_PHONE = Pattern.compile("(\\+7-9[0-9]{2}-[0-9]{3}-[0-9]{2}-[0-9]{2})");
    private final String locationMapDogPetShelter;
    private final String locationMapCatPetShelter;
    private final InlineKeyboardService inlineKeyboardService;
    private final TelegramBotService telegramBotService;
    private final UserService userService;
    private final VolunteerService volunteerService;
    private final MessageService messageService;

    public UserCommandHandler(@Value("${path.to.photo.with.location.map.for.dog.pet.shelter}") String locationMapDogPetShelter,
                              @Value("${path.to.photo.with.location.map.for.cat.pet.shelter}") String locationMapCatPetShelter,
                              InlineKeyboardService inlineKeyboardService,
                              TelegramBotService telegramBotService,
                              UserService userService,
                              VolunteerService volunteerService,
                              MessageService messageService) {
        this.locationMapDogPetShelter = locationMapDogPetShelter;
        this.locationMapCatPetShelter = locationMapCatPetShelter;
        this.inlineKeyboardService = inlineKeyboardService;
        this.telegramBotService = telegramBotService;
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.messageService = messageService;
    }

    /**
     * This method handles the start command
     *
     * @param userId    ID of the user who sent the message
     * @param text      text from message
     * @param messageId ID of the message sent by the user
     */
    public void handleStart(Long userId, String text, Integer messageId) {
        if ("/start".equals(text)) {
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getChooseShelterKeyboard();
            String textMessage = "Выберите приют:";

            if (messageId == null) {
                telegramBotService.sendInlineKeyboard(userId, textMessage, inlineKeyboardMarkup);
            } else {
                telegramBotService.editInlineKeyboard(userId, messageId, textMessage, inlineKeyboardMarkup);
            }
            userService.setUserState(userId, UserState.CHOOSE_SHELTER);
        } else {
            telegramBotService.sendMessage(userId, "Введите команду /start");
        }
    }

    public void handleStart(Long userId, String text) {
        handleStart(userId, text, null);
    }

    /**
     * This method handles the user's phone number
     *
     * @param userId
     * @param text
     */
    public void handleUserPhoneNumber(Long userId, String text) {
        Matcher matcher = PATTERN_PHONE.matcher(text);

        if (matcher.matches()) {
            String phoneNumber = matcher.group(1);
            String message = "Ваш телефон сохранен.\n\n";
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(UserCommand.INFO_ABOUT_SHELTER);
            ShelterType shelterType = ShelterType.valueOf(userService.getSelectedShelter(userId));
            LOGGER.info(phoneNumber);

            switch (shelterType) {
                case DOG_SHELTER:
                    message = message + "Какую информацию о собачьем приюте вы хотите получить?";
                    break;
                case CAT_SHELTER:
                    message = message + "Какую информацию о кошачьем приюте вы хотите получить?";
                    break;
            }

            userService.setPhoneNumber(userId, phoneNumber);
            userService.setUserState(userId, UserState.INFO_ABOUT_SHELTER);
            telegramBotService.sendInlineKeyboard(userId, message, inlineKeyboardMarkup);
        } else {
            telegramBotService.sendMessage(userId, "Напишите свой номер телефона в формате: +7-9**-***-**-**");
        }
    }

    /**
     * This method sends a message to the volunteer while user chatting with him or ends the conversation
     *
     * @param userId
     * @param text
     */
    public void sendMessageToVolunteer(Long userId, String text) {
        Volunteer volunteer = volunteerService.getVolunteerByUserId(userId);

        if ("/stop".equals(text)) {
            userService.stopConversation(userId);
            volunteerService.stopConversation(volunteer.getId());
            telegramBotService.sendMessage(volunteer.getId(), "Пользователь завершил разговор");
            handleStart(userId, "/start");
        } else {
            telegramBotService.sendMessage(volunteer.getId(), text);
        }
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
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getMainMenuKeyboard();
        String textMessage = null;

        switch (shelterType) {
            case CAT_SHELTER:
                textMessage = "С каким вопросом вы пришли в кошачий приют?";
                break;
            case DOG_SHELTER:
                textMessage = "С каким вопросом вы пришли в собачий приют?";
                break;
            default:
                LOGGER.debug("handleChooseShelter некорректный shelterType - " + shelterType);
        }

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
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "INFO_ABOUT_SHELTER");
                userService.setUserState(userId, UserState.INFO_ABOUT_SHELTER);
                break;
            case HOW_ADOPT_PET:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "HOW_ADOPT_PET");
                userService.setUserState(userId, UserState.HOW_ADOPT_PET);
                break;
            case CALL_VOLUNTEER:
                startConversation(userId);
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
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "ABOUT_PET_SHELTER");
                break;
            case SCHEDULE_AND_ADDRESS:
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "SCHEDULE_AND_ADDRESS");
                break;
            case LOCATION_MAP:
                locationMap(userId, messageId, shelterType);
                break;
            case REGISTRATION_PASS:
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "REGISTRATION_PASS");
                break;
            case SAFETY_PRECAUTIONS:
                inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(userCommand);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "SAFETY_PRECAUTIONS");
                break;
            case SHARE_CONTACTS:
                shareContacts(userId, messageId);
                break;
            case CALL_VOLUNTEER:
                startConversation(userId);
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
                listOfAnimals(userId, messageId, shelterType);
                break;
            case RULES_FOR_MEETING:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "RULES_FOR_MEETING");
                break;
            case DOCUMENTS:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "DOCUMENTS");
                break;
            case TRANSPORTATION_RULES:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "TRANSPORTATION_RULES");
                break;
            case HOME_IMPROVEMENT_FOR_SMALL_PET:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "HOME_IMPROVEMENT_FOR_SMALL_PET");
                break;
            case HOME_IMPROVEMENT_FOR_BIG_PET:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "HOME_IMPROVEMENT_FOR_BIG_PET");
                break;
            case HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "HOME_IMPROVEMENT_FOR_PET_WITH_DISABILITIES");
                break;
            case ADVICE_FROM_DOG_HANDLERS:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "ADVICE_FROM_DOG_HANDLERS");
                break;
            case DOG_HANDLERS:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "DOG_HANDLERS");
                break;
            case REJECTION_REASON:
                inlineKeyboardMarkup = inlineKeyboardService.getHowAdoptPetMenuKeyboard(userCommand, shelterType);
                sendInlineKeyboard(userId, messageId, inlineKeyboardMarkup, shelterType, "REJECTION_REASON");
                break;
            case SHARE_CONTACTS:
                shareContacts(userId, messageId);
                break;
            case CALL_VOLUNTEER:
                startConversation(userId);
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
                handleStart(userId, "/start", messageId);
                break;
            case INFO_ABOUT_SHELTER:
            case HOW_ADOPT_PET:
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
    private void startConversation(Long userId) {
        Volunteer volunteer = volunteerService.getFreeVolunteer();
        User user = userService.getUser(userId);

        volunteerService.startConversation(volunteer, user);
        userService.startConversation(userId);

        telegramBotService.sendMessage(userId, messageService.getMessage("START_CONVERSATION_USER"));
        telegramBotService.sendMessage(volunteer.getId(), messageService.getMessage("START_CONVERSATION_VOLUNTEER"));
    }

    /**
     * This method sends a photo with location map
     *
     * @param userId
     * @param messageId
     * @param shelterType
     */
    private void locationMap(Long userId, Integer messageId, ShelterType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(UserCommand.LOCATION_MAP);
        String photoPath = null;
        String textMessage = null;
        String aboutPhoto = null;
        Path path;
        File photo;

        switch (shelterType) {
            case DOG_SHELTER:
                photoPath = locationMapDogPetShelter;
                textMessage = "Какую информацию о собачьем приюте вы хотите получить?";
                aboutPhoto = "Схема проезда до собачьего приюта:";
                break;
            case CAT_SHELTER:
                photoPath = locationMapCatPetShelter;
                textMessage = "Какую информацию о кошачьем приюте вы хотите получить?";
                aboutPhoto = "Схема проезда до кошачьего приюта:";
                break;
        }

        path = Path.of(photoPath);
        photo = path.toFile();

        telegramBotService.sendMessage(userId, aboutPhoto);
        telegramBotService.sendPhoto(userId, photo);
        telegramBotService.deleteMessage(userId, messageId);
        telegramBotService.sendInlineKeyboard(userId, textMessage, inlineKeyboardMarkup);
    }

    private void shareContacts(Long userId, Integer messageId) {
        String text = "Напишите свой номер телефона в формате: +7-9**-***-**-**";

        userService.setUserState(userId, UserState.SHARE_CONTACTS);
        telegramBotService.editMessage(userId, messageId, text);
    }

    private void listOfAnimals(Long userId, Integer messageId, ShelterType shelterType) {

    }
}

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
import pro.sky.telegrambot.service.InlineKeyboardService;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

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

    public UserCommandHandler(@Value("${path.to.photo.with.location.map.for.dog.pet.shelter}") String locationMapDogPetShelter,
                              @Value("${path.to.photo.with.location.map.for.cat.pet.shelter}") String locationMapCatPetShelter,
                              InlineKeyboardService inlineKeyboardService,
                              TelegramBotService telegramBotService,
                              UserService userService,
                              VolunteerService volunteerService) {
        this.locationMapDogPetShelter = locationMapDogPetShelter;
        this.locationMapCatPetShelter = locationMapCatPetShelter;
        this.inlineKeyboardService = inlineKeyboardService;
        this.telegramBotService = telegramBotService;
        this.userService = userService;
        this.volunteerService = volunteerService;
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

            if (messageId == null) {
                telegramBotService.sendInlineKeyboard(userId, "Выберите приют:", inlineKeyboardMarkup);
            } else {
                telegramBotService.editInlineKeyboard(userId, messageId, "Выберите приют:", inlineKeyboardMarkup);
            }
            userService.setUserState(userId, UserState.CHOOSE_SHELTER);
        } else {
            telegramBotService.sendMessage(userId, "Введите команду /start");
        }
    }

    public void handleStart(Long userId, String text) {
        handleStart(userId, text, null);
    }

    public void handleShareContacts(Long userId, String text) {
        Matcher matcher = PATTERN_PHONE.matcher(text);

        if (matcher.matches()) {
            String phoneNumber = matcher.group(1);
            String message = "Ваш телефон сохранен.\n\n";
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(UserCommand.INFO_ABOUT_SHELTER);
            ShelterType shelterType = ShelterType.valueOf(userService.getSelectedShelter(userId));
            LOGGER.info(phoneNumber);

            switch (shelterType) {
                case DOG:
                    message = message + "Какую информацию о собачьем приюте вы хотите получить?";
                    break;
                case CAT:
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
            case CAT:
                textMessage = "С каким вопросом вы пришли в кошачий приют?";
                break;
            case DOG:
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

        switch (userCommand) {
            case INFO_ABOUT_SHELTER:
                infoAboutShelterMenu(userId, messageId, userCommand, shelterType);
                break;
            case CALL_VOLUNTEER:
                startConversation(userId);
                break;
            case BACK:
                handleBackCommand(userId, messageId);
                break;
        }
    }

    public void handleInfoAboutShelter(Long userId, Integer messageId, String data) {
        UserCommand userCommand = UserCommand.valueOf(data);
        ShelterType shelterType = ShelterType.valueOf(userService.getSelectedShelter(userId));

        switch (userCommand) {
            case ABOUT_SHELTER:
                aboutShelter(userId, messageId, shelterType);
                break;
            case SCHEDULE_AND_ADDRESS:
                scheduleAndAddress(userId, messageId, shelterType);
                break;
            case LOCATION_MAP:
                locationMap(userId, messageId, shelterType);
                break;
            case REGISTRATION_PASS:
                registrationPass(userId, messageId, shelterType);
                break;
            case SAFETY_PRECAUTIONS:
                safetyPrecautions(userId, messageId, shelterType);
                break;
            case SHARE_CONTACTS:
                shareContacts(userId, messageId);
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
                String selectedShelter = userService.getSelectedShelter(userId);
                handleChooseShelter(userId, messageId, selectedShelter);
                break;
        }
    }

    public void infoAboutShelterMenu(Long userId, Integer messageId, UserCommand userCommand, ShelterType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(userCommand);
        String text = null;

        switch (shelterType) {
            case DOG:
                text = "Какую информацию о собачьем приюте вы хотите получить?";
                break;
            case CAT:
                text = "Какую информацию о кошачьем приюте вы хотите получить?";
                break;
        }

        telegramBotService.editInlineKeyboard(userId, messageId, text, inlineKeyboardMarkup);
        userService.setUserState(userId, UserState.INFO_ABOUT_SHELTER);
    }

    private void startConversation(Long userId) {
        Volunteer volunteer = volunteerService.getFreeVolunteer();
        User user = userService.getUser(userId);

        volunteerService.startConversation(volunteer, user);
        userService.startConversation(userId);

        telegramBotService.sendMessage(userId, "Волонтер сейчас с вами свяжется");
        telegramBotService.sendMessage(volunteer.getId(), "Пользователь запросил связь, у вас с ним начался разговор");
    }

    private void aboutShelter(Long userId, Integer messageId, ShelterType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(UserCommand.ABOUT_SHELTER);
        String textMessage = null;

        switch (shelterType) {
            case DOG:
                textMessage = "Рассказ о собачьем приюте";
                break;
            case CAT:
                textMessage = "Рассказ о кошачьем приюте";
                break;
        }

        telegramBotService.editInlineKeyboard(userId, messageId, textMessage, inlineKeyboardMarkup);
    }

    private void scheduleAndAddress(Long userId, Integer messageId, ShelterType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(UserCommand.SCHEDULE_AND_ADDRESS);
        String textMessage = null;

        switch (shelterType) {
            case DOG:
                textMessage = "Расписание и адрес собачьего приюта";
                break;
            case CAT:
                textMessage = "Расписание и адрес кошачьего приюта";
                break;
        }

        telegramBotService.editInlineKeyboard(userId, messageId, textMessage, inlineKeyboardMarkup);
    }

    private void locationMap(Long userId, Integer messageId, ShelterType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(UserCommand.LOCATION_MAP);
        String photoPath = null;
        String textMessage = null;
        String aboutPhoto = null;
        Path path;
        File photo;

        switch (shelterType) {
            case DOG:
                photoPath = locationMapDogPetShelter;
                textMessage = "Какую информацию о собачьем приюте вы хотите получить?";
                aboutPhoto = "Схема проезда до собачьего приюта:";
                break;
            case CAT:
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

    private void registrationPass(Long userId, Integer messageId, ShelterType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(UserCommand.REGISTRATION_PASS);
        String textMessage = null;

        switch (shelterType) {
            case DOG:
                textMessage = "Контакты для оформления пропуска в собачий приют";
                break;
            case CAT:
                textMessage = "Контакты для оформления пропуска в кошачий приют";
                break;
        }

        telegramBotService.editInlineKeyboard(userId, messageId, textMessage, inlineKeyboardMarkup);
    }

    private void safetyPrecautions(Long userId, Integer messageId, ShelterType shelterType) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterMenuKeyboard(UserCommand.SAFETY_PRECAUTIONS);
        String textMessage = null;

        switch (shelterType) {
            case DOG:
                textMessage = "Рекомендации по технике безопасности в собачьем приюте";
                break;
            case CAT:
                textMessage = "Рекомендации по технике безопасности в кошачьем приюте";
                break;
        }

        telegramBotService.editInlineKeyboard(userId, messageId, textMessage, inlineKeyboardMarkup);
    }

    private void shareContacts(Long userId, Integer messageId) {
        String text = "Напишите свой номер телефона в формате: +7-9**-***-**-**";

        userService.setUserState(userId, UserState.SHARE_CONTACTS);
        telegramBotService.editMessage(userId, messageId, text);
    }
}

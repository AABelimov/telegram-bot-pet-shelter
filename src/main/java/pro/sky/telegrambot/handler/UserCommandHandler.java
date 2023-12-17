package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserCommand;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.service.InlineKeyboardService;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;

/**
 * This class handles commands coming from the user
 */
@Component
public class UserCommandHandler {

    private final InlineKeyboardService inlineKeyboardService;
    private final TelegramBotService telegramBotService;
    private final UserService userService;

    public UserCommandHandler(InlineKeyboardService inlineKeyboardService,
                              TelegramBotService telegramBotService,
                              UserService userService) {
        this.inlineKeyboardService = inlineKeyboardService;
        this.telegramBotService = telegramBotService;
        this.userService = userService;
    }

    /**
     * This method handles the start command
     * @param userId ID of the user who sent the message
     * @param text text from message
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

    /**
     * This method handles commands from the choose shelter menu
     * @param userId ID of the user who sent the message
     * @param messageId ID of the message sent by the user
     * @param data data from a callback query that belonged to a specific button
     */
    public void handleChooseShelter(Long userId, Integer messageId, String data) {
        ShelterType shelterType = ShelterType.valueOf(data);
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getMainMenuKeyboard();
        String textMessage = "С каким вопросом вы пришли?";

        telegramBotService.editInlineKeyboard(userId, messageId, textMessage, inlineKeyboardMarkup);
        userService.setSelectedShelter(userId, shelterType);
        userService.setUserState(userId, UserState.MAIN_MENU);
    }

    /**
     * This method handles commands from the main menu
     * @param userId
     * @param messageId
     * @param data
     */
    public void handleMainMenu(Long userId, Integer messageId, String data) {
        UserCommand userCommand = UserCommand.valueOf(data);
        switch (userCommand) {
            case INFO_ABOUT_SHELTER:
                infoAboutShelterMenu(userId, messageId);
                break;
            case CALL_VOLUNTEER:
                telegramBotService.startConversation(userId);
                break;
            case BACK:
                handleBackCommand(userId, messageId, data);
                break;
        }
    }

    /**
     * This method handles the back command
     * @param userId
     * @param messageId
     * @param data
     */
    private void handleBackCommand(Long userId, Integer messageId, String data) {
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

    public void handleInfoAboutShelter(Long userId, Integer messageId, String data) {
        UserCommand userCommand = UserCommand.valueOf(data);

        switch (userCommand) {
            case USER_CONTACTS:
                break;
            case BACK:
                handleBackCommand(userId, messageId, data);
                break;
        }
    }

    public void infoAboutShelterMenu(Long userId, Integer messageId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterKeyboard();

        telegramBotService.editInlineKeyboard(userId, messageId, "ashdgfasjdgfajsdf", inlineKeyboardMarkup);
        userService.setUserState(userId, UserState.INFO_ABOUT_SHELTER);
    }
}

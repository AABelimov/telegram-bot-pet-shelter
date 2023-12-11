package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserCommand;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.service.InlineKeyboardService;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;

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

    public void startHandle(Long userId, String text) {
        startHandle(userId, text, null);
    }

    public void startHandle(Long userId, String text, Integer messageId) {
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

    public void chooseShelterHandle(Long userId, Integer messageId, String data) {

        ShelterType shelterType = ShelterType.valueOf(data);
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getMainMenuKeyboard();
        String textMessage = "С каким вопросом вы пришли?";

        telegramBotService.editInlineKeyboard(userId, messageId, textMessage, inlineKeyboardMarkup);
        userService.setSelectedShelter(userId, shelterType);
        userService.setUserState(userId, UserState.MAIN_MENU);
    }

    public void mainMenuHandle(Long userId, Integer messageId, String data) {
        UserCommand userCommand = UserCommand.valueOf(data);
        switch (userCommand) {
            case BACK:
                backCommandHandle(userId, messageId, data);
                break;
        }
    }

    private void backCommandHandle(Long userId, Integer messageId, String data) {
        UserState userState = userService.getUserState(userId);

        switch (userState) {
            case MAIN_MENU:
                startHandle(userId, "/start", messageId);
                break;
        }
    }
}

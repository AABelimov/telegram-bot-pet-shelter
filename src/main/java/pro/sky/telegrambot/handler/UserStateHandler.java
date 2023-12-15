package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;

/**
 * This class handles user states
 */
@Component
public class UserStateHandler {

    private final UserService userService;
    private final UserCommandHandler userCommandHandler;
    private final TelegramBotService telegramBotService;

    public UserStateHandler(UserService userService,
                            UserCommandHandler userCommandHandler,
                            TelegramBotService telegramBotService) {
        this.userService = userService;
        this.userCommandHandler = userCommandHandler;
        this.telegramBotService = telegramBotService;
    }

    /**
     * This method calls command handlers
     * @param userId
     * @param callbackQuery
     * @param message
     */
    public void handleState(Long userId, CallbackQuery callbackQuery, Message message) {
        UserState userState = userService.getUserState(userId);

        if (callbackQuery != null) {
            Integer messageId = callbackQuery.message().messageId();
            String data = callbackQuery.data();

            switch (userState) {
                case CHOOSE_SHELTER:
                    userCommandHandler.handleChooseShelter(userId, messageId, data);
                    break;
                case MAIN_MENU:
                    userCommandHandler.handleMainMenu(userId, messageId, data);
                    break;
            }

        } else {
            String text = message.text();

            switch (userState) {
                case START:
                    userCommandHandler.handleStart(userId, text);
                    break;
                case CONVERSATION:
                    telegramBotService.sendMessageToVolunteer(userId, text);
                    break;
            }
        }
    }
}

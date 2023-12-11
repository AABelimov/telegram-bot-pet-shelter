package pro.sky.telegrambot.handler;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.service.UserService;

@Component
public class UserStateHandler {

    private final UserService userService;
    private final UserCommandHandler userCommandHandler;

    public UserStateHandler(UserService userService,
                            UserCommandHandler userCommandHandler) {
        this.userService = userService;
        this.userCommandHandler = userCommandHandler;
    }

    public void stateHandleText(Long userId, String text) {
        UserState userState = userService.getUserState(userId);

        switch (userState) {
            case START:
                userCommandHandler.startHandle(userId, text);
                break;
        }
    }

    public void stateHandleCallBackQuery(Long userId, Integer messageId, String data) {
        UserState userState = userService.getUserState(userId);

        switch (userState) {
            case CHOOSE_SHELTER:
                userCommandHandler.chooseShelterHandle(userId, messageId, data);
                break;
            case MAIN_MENU:
                userCommandHandler.mainMenuHandle(userId, messageId, data);
                break;
        }
    }
}

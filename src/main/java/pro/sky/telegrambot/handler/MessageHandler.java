package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.enums.VolunteerState;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.MessageService;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

/**
 * This class handles the message object if it is not null
 */
@Component
public class MessageHandler {

    private final VolunteerService volunteerService;
    private final UserService userService;
    private final VolunteerStateHandler volunteerStateHandler;
    private final TelegramBotService telegramBotService;
    private final UserStateHandler userStateHandler;
    private final MessageService messageService;

    public MessageHandler(VolunteerService volunteerService,
                          UserService userService,
                          VolunteerStateHandler volunteerStateHandler,
                          TelegramBotService telegramBotService,
                          UserStateHandler userStateHandler,
                          MessageService messageService) {
        this.volunteerService = volunteerService;
        this.userService = userService;
        this.volunteerStateHandler = volunteerStateHandler;
        this.telegramBotService = telegramBotService;
        this.userStateHandler = userStateHandler;
        this.messageService = messageService;
    }

    /**
     * This method determines whether the message came from a user or a volunteer and cals the appropriate handler
     *
     * @param message this object represents a message
     */
    public void handleMessage(Message message) {
        Long id = message.chat().id();
        Volunteer volunteer = volunteerService.getVolunteer(id);

        if ("/start".equals(message.text())) {
            if (volunteer != null) {
                volunteerStart(id);
            } else {
                userStart(id, message);
            }
        }

        if (volunteer != null) {
            volunteerStateHandler.handleState(id, null, message);
        } else {
            userStateHandler.handleState(id, null, message);
        }
    }

    private void volunteerStart(Long id) {
        volunteerService.setVolunteerState(id, VolunteerState.START);
    }

    private void userStart(Long id, Message message) {
        User user = userService.getUser(id);

        if (user == null) {
            String userName = message.chat().firstName();
            userService.createUser(id, userName);
            telegramBotService.sendMessage(id, String.format("%s, %s", userName, messageService.getMessage("GREETING_NEW_USER")));
        }
        userService.setUserState(id, UserState.START);
    }
}

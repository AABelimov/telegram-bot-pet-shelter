package pro.sky.telegrambot.handler;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

@Component
public class VolunteerCommandHandler {

    private final VolunteerService volunteerService;
    private final UserService userService;
    private final TelegramBotService telegramBotService;

    public VolunteerCommandHandler(VolunteerService volunteerService,
                                   UserService userService,
                                   TelegramBotService telegramBotService) {
        this.volunteerService = volunteerService;
        this.userService = userService;
        this.telegramBotService = telegramBotService;
    }

    public void sendMessageToUser(Long volunteerId, String text) {
        Long userId = volunteerService.getUserIdByVolunteerId(volunteerId);

        if ("/stop".equals(text)) {
            userService.stopConversation(userId);
            volunteerService.stopConversation(volunteerId);
        } else {
            telegramBotService.sendMessage(userId, text);
        }
    }
}

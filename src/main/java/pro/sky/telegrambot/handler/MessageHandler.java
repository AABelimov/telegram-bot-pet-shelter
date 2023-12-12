package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

@Component
public class MessageHandler {

    private final VolunteerService volunteerService;
    private final VolunteerStateHandler volunteerStateHandler;
    private final TelegramBotService telegramBotService;
    private final UserStateHandler userStateHandler;

    public MessageHandler(VolunteerService volunteerService,
                          VolunteerStateHandler volunteerStateHandler,
                          TelegramBotService telegramBotService,
                          UserStateHandler userStateHandler) {
        this.volunteerService = volunteerService;
        this.volunteerStateHandler = volunteerStateHandler;
        this.telegramBotService = telegramBotService;
        this.userStateHandler = userStateHandler;
    }

    public void handleMessage(Message message) {
        Long id = message.chat().id();
        Volunteer volunteer = volunteerService.getVolunteer(id);

        if ("/start".equals(message.text())) {
            telegramBotService.start(id, volunteer);
        } else if (volunteer != null) {
            volunteerStateHandler.handleState(id, message);
        } else {
            userStateHandler.handleState(id, null, message);
        }
    }
}

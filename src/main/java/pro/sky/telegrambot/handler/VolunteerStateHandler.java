package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.VolunteerState;
import pro.sky.telegrambot.service.VolunteerService;

/**
 * This class handles volunteer states
 */
@Component
public class VolunteerStateHandler {

    private final VolunteerService volunteerService;
    private final VolunteerCommandHandler volunteerCommandHandler;

    public VolunteerStateHandler(VolunteerService volunteerService,
                                 VolunteerCommandHandler volunteerCommandHandler) {
        this.volunteerService = volunteerService;
        this.volunteerCommandHandler = volunteerCommandHandler;
    }

    public void handleState(Long volunteerId, CallbackQuery callbackQuery, Message message) {
        VolunteerState volunteerState = volunteerService.getVolunteerState(volunteerId);

        if (callbackQuery != null) {
            Integer messageId = callbackQuery.message().messageId();
            String data = callbackQuery.data();

            switch (volunteerState) {

            }

        } else {
            String text = message.text();

            switch (volunteerState) {
                case CONVERSATION:
                    volunteerCommandHandler.sendMessageToUser(volunteerId, text);
                    break;
            }
        }
    }
}

package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.VolunteerState;
import pro.sky.telegrambot.service.InlineKeyboardService;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

/**
 * This class handles commands coming from the volunteer
 */
@Component
public class VolunteerTextMessageHandler {

    private final VolunteerService volunteerService;
    private final UserService userService;
    private final TelegramBotService telegramBotService;
    private final InlineKeyboardService inlineKeyboardService;

    public VolunteerTextMessageHandler(VolunteerService volunteerService,
                                       UserService userService,
                                       TelegramBotService telegramBotService,
                                       InlineKeyboardService inlineKeyboardService) {
        this.volunteerService = volunteerService;
        this.userService = userService;
        this.telegramBotService = telegramBotService;
        this.inlineKeyboardService = inlineKeyboardService;
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

    public void handleStart(Long volunteerId, String text) {
        if ("/start".equals(text)) {
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getVolunteerMainMenu();
            String textMessage = "text";
            telegramBotService.sendInlineKeyboard(volunteerId, textMessage, inlineKeyboardMarkup);
            volunteerService.setVolunteerState(volunteerId, VolunteerState.MAIN_MENU);
        }
    }
}

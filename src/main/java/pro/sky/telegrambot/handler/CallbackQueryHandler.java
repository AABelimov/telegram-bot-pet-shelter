package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.VolunteerService;

@Component
public class CallbackQueryHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackQueryHandler.class);
    private final TelegramBot telegramBot;
    private final VolunteerService volunteerService;
    private final VolunteerStateHandler volunteerStateHandler;
    private final UserStateHandler userStateHandler;

    public CallbackQueryHandler(TelegramBot telegramBot,
                                VolunteerService volunteerService,
                                VolunteerStateHandler volunteerStateHandler,
                                UserStateHandler userStateHandler) {
        this.telegramBot = telegramBot;
        this.volunteerService = volunteerService;
        this.volunteerStateHandler = volunteerStateHandler;
        this.userStateHandler = userStateHandler;
    }

    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id()));
        LOGGER.info(callbackQuery.data());
        Long id = callbackQuery.message().chat().id();
        Volunteer volunteer = volunteerService.getVolunteer(id);

        if (volunteer != null) {
            volunteerStateHandler.handleState(id, callbackQuery.message());
        } else {
            userStateHandler.handleState(id, callbackQuery, null);
        }
    }
}

package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.VolunteerService;

/**
 * If callbackQuery is not null then it is handling in this class
 */
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

    /**
     * This method determines whether the callback query came from a user or a volunteer and cals the appropriate handler
     * @param callbackQuery this object represents an incoming callback query from a callback button in an inline keyboard
     */
    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id()));
        LOGGER.info(callbackQuery.data());
        Long id = callbackQuery.message().chat().id();
        Volunteer volunteer = volunteerService.getVolunteer(id);

        if (volunteer != null) {
            volunteerStateHandler.handleState(id, callbackQuery, null);
        } else {
            userStateHandler.handleState(id, callbackQuery, null);
        }
    }
}

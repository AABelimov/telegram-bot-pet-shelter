package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.handler.UserStateHandler;
import pro.sky.telegrambot.handler.VolunteerStateHandler;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final VolunteerService volunteerService;
    private final TelegramBot telegramBot;
    private final UserStateHandler userStateHandler;
    private final VolunteerStateHandler volunteerStateHandler;
    private final UserService userService;
    private final TelegramBotService telegramBotService;

    public TelegramBotUpdatesListener(VolunteerService volunteerService,
                                      TelegramBot telegramBot,
                                      UserStateHandler userStateHandler,
                                      VolunteerStateHandler volunteerStateHandler,
                                      UserService userService,
                                      TelegramBotService telegramBotService) {
        this.volunteerService = volunteerService;
        this.telegramBot = telegramBot;
        this.userStateHandler = userStateHandler;
        this.volunteerStateHandler = volunteerStateHandler;
        this.userService = userService;
        this.telegramBotService = telegramBotService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            LOGGER.info("Processing update: {}", update);
            Message message = update.message();
            CallbackQuery callbackQuery = update.callbackQuery();

            if (message != null && (message.text() != null || message.photo() != null)) {
                Long id = message.chat().id();
                Volunteer volunteer = volunteerService.getVolunteer(id);

                if (volunteer != null) {
                    volunteerStateHandler.stateHandle(id, update);
                } else {
                    UserState userState = userService.getUserState(id);

                    if ("/start".equals(message.text())) {
                        if (userState.equals(UserState.NEW_USER)) {
                            String userName = message.chat().firstName();
                            userService.setUserName(id, userName);
                            telegramBotService.sendMessage(id, String.format("%s, Доброго времени суток. " +
                                    "Это учебный бот из курса по java разработке школы skyPro.\n\n", userName));
                        }
                        userService.setUserState(id, UserState.START);
                    }
                    userStateHandler.stateHandleText(id, message.text());
                }
            }

            if (callbackQuery != null) {
                telegramBot.execute(new AnswerCallbackQuery(callbackQuery.id()));
                LOGGER.info(callbackQuery.data());
                Long id = callbackQuery.message().chat().id();
                Volunteer volunteer = volunteerService.getVolunteer(id);

                if (volunteer != null) {
                    volunteerStateHandler.stateHandle(id, update);
                } else {
                    Integer messageId = callbackQuery.message().messageId();
                    String data = callbackQuery.data();
                    userStateHandler.stateHandleCallBackQuery(id, messageId, data);
                }
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}

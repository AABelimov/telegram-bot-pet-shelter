package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.handler.CallbackQueryHandler;
import pro.sky.telegrambot.handler.MessageHandler;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final CallbackQueryHandler callbackQueryHandler;
    private final MessageHandler messageHandler;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      CallbackQueryHandler callbackQueryHandler,
                                      MessageHandler messageHandler) {
        this.telegramBot = telegramBot;
        this.callbackQueryHandler = callbackQueryHandler;
        this.messageHandler = messageHandler;
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
                messageHandler.handleMessage(message);
            }

            if (callbackQuery != null) {
                callbackQueryHandler.handleCallbackQuery(callbackQuery);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}

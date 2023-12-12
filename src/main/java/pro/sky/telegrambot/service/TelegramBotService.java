package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;

@Service
public class TelegramBotService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotService.class);
    private final TelegramBot telegramBot;
    private final UserService userService;

    public TelegramBotService(TelegramBot telegramBot, UserService userService) {
        this.telegramBot = telegramBot;
        this.userService = userService;
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        SendResponse sendResponse = telegramBot.execute(sendMessage);

        if (!sendResponse.isOk()) {
            LOGGER.error("Send message was failed due to: " + sendResponse.description());
        }
    }

    public void sendInlineKeyboard(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage(chatId, text).replyMarkup(inlineKeyboardMarkup);
        SendResponse sendResponse = telegramBot.execute(sendMessage);

        if (!sendResponse.isOk()) {
            LOGGER.error("Send inline keyboard was failed due to: " + sendResponse.description());
        }
    }

    public void editInlineKeyboard(Long chatId, Integer messageId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessageText = new EditMessageText(chatId, messageId, text);
        BaseResponse baseResponse = telegramBot.execute(editMessageText.replyMarkup(inlineKeyboardMarkup));

        if (!baseResponse.isOk()) {
            LOGGER.error("Send inline keyboard was failed due to: " + baseResponse.description());
        }
    }

    public void start(Long id, Volunteer volunteer, Message message) {

        if (volunteer == null) {
            User user = userService.getUser(id);

            if (user == null) {
                String userName = message.chat().firstName();
                userService.createUser(id, userName);
                sendMessage(id, String.format("%s, Доброго времени суток. " +
                        "Это учебный бот из курса по java разработке школы skyPro.\n\n", userName));
            }
            userService.setUserState(id, UserState.START);
        }
    }
}

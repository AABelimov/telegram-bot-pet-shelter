package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMedia;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TelegramBotService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotService.class);
    private final TelegramBot telegramBot;

    public TelegramBotService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        SendResponse sendResponse = telegramBot.execute(sendMessage);

        if (!sendResponse.isOk()) {
            LOGGER.error("Send message was failed due to: " + sendResponse.description());
        }
    }

    public void deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
        BaseResponse baseResponse = telegramBot.execute(deleteMessage);

        if (!baseResponse.isOk()) {
            LOGGER.error("Delete message was failed due to: " + baseResponse.description());
        }
    }

    public void sendPhoto(Long chatId, File photo) {
        SendPhoto sendPhoto = new SendPhoto(chatId, photo).disableNotification(true);
        SendResponse sendResponse = telegramBot.execute(sendPhoto);

        if (!sendResponse.isOk()) {
            LOGGER.error("Send photo was failed due to: " + sendResponse.description());
        }
    }

    public void editPhoto(Long chatId, Integer messageId, File photo) {
        InputMedia<?> inputMedia = new InputMediaPhoto(photo);
        EditMessageMedia editMessageMedia = new EditMessageMedia(chatId, messageId, inputMedia);
        BaseResponse baseResponse = telegramBot.execute(editMessageMedia);

        if (!baseResponse.isOk()) {
            LOGGER.error("Send photo was failed due to: " + baseResponse.description());
        }
    }

    public void sendInlineKeyboard(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage(chatId, text).replyMarkup(inlineKeyboardMarkup).disableNotification(true);
        SendResponse sendResponse = telegramBot.execute(sendMessage);

        if (!sendResponse.isOk()) {
            LOGGER.error("Send inline keyboard was failed due to: " + sendResponse.description());
        }
    }

    public void sendInlineKeyboard(Long chatId, File photo, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendPhoto sendPhoto = new SendPhoto(chatId, photo)
                .caption(text)
                .replyMarkup(inlineKeyboardMarkup)
                .disableNotification(true);
        SendResponse sendResponse = telegramBot.execute(sendPhoto);

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

    public void editInlineKeyboard(Long chatId, Integer messageId, File photo, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        InputMedia<?> inputMedia = new InputMediaPhoto(photo).caption(text);
        EditMessageMedia editMessageMedia = new EditMessageMedia(chatId, messageId, inputMedia).replyMarkup(inlineKeyboardMarkup);
        BaseResponse baseResponse = telegramBot.execute(editMessageMedia);

        if (!baseResponse.isOk()) {
            LOGGER.error("Send inline keyboard was failed due to: " + baseResponse.description());
        }
    }

    public void editMessage(Long chatId, Integer messageId, String text) {
        EditMessageText editMessageText = new EditMessageText(chatId, messageId, text);
        BaseResponse baseResponse = telegramBot.execute(editMessageText);

        if (!baseResponse.isOk()) {
            LOGGER.error("Send message was failed due to: " + baseResponse.description());
        }
    }
}

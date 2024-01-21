package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.service.UserService;

/**
 * This class handles user states
 */
@Component
public class UserStateHandler {

    private final UserService userService;
    private final UserDataCallbackQueryHandler userDataCallbackQueryHandler;
    private final UserTextMessageHandler userTextMessageHandler;
    private final UserPhotoMessageHandler userPhotoMessageHandler;

    public UserStateHandler(UserService userService,
                            UserDataCallbackQueryHandler userDataCallbackQueryHandler,
                            UserTextMessageHandler userTextMessageHandler,
                            UserPhotoMessageHandler userPhotoMessageHandler) {
        this.userService = userService;
        this.userDataCallbackQueryHandler = userDataCallbackQueryHandler;
        this.userTextMessageHandler = userTextMessageHandler;
        this.userPhotoMessageHandler = userPhotoMessageHandler;
    }

    /**
     * This method calls command handlers
     * @param userId
     * @param callbackQuery
     * @param message
     */
    public void handleState(Long userId, CallbackQuery callbackQuery, Message message) {
        UserState userState = userService.getUserState(userId);

        if (callbackQuery != null) {
            Integer messageId = callbackQuery.message().messageId();
            String data = callbackQuery.data();

            switch (userState) {
                case CHOOSE_SHELTER:
                    userDataCallbackQueryHandler.handleChooseShelter(userId, messageId, data);
                    break;
                case MAIN_MENU:
                    userDataCallbackQueryHandler.handleMainMenu(userId, messageId, data);
                    break;
                case INFO_ABOUT_SHELTER:
                    userDataCallbackQueryHandler.handleInfoAboutShelter(userId, messageId, data);
                    break;
                case HOW_ADOPT_PET:
                    userDataCallbackQueryHandler.handleHowAdoptPet(userId, messageId, data);
                    break;
                case VIEWING_ANIMALS:
                    userDataCallbackQueryHandler.handleViewingAnimals(userId, messageId, data);
                    break;
                case SEND_REPORT:
                    userDataCallbackQueryHandler.handleSendReport(userId, messageId, data);
                    break;
                case SELECT_ANIMAL_TO_REPORT:
                    userDataCallbackQueryHandler.handleSelectAnimalToReport(userId, messageId, data);
                    break;
            }

        } else {
            String text = message.text();
            PhotoSize[] photoSizes = message.photo();

            if (text != null) {
                switch (userState) {
                    case START:
                        userTextMessageHandler.handleStart(userId, text);
                        break;
                    case CONVERSATION:
                        userTextMessageHandler.sendMessageToVolunteer(userId, text);
                        break;
                    case SHARE_CONTACTS:
                        userTextMessageHandler.handleUserPhoneNumber(userId, text);
                        break;
                    case FILL_OUT_THE_REPORT_DIET:
                        userTextMessageHandler.handleDiet(userId, text);
                        break;
                    case FILL_OUT_THE_REPORT_WELL_BEING:
                        userTextMessageHandler.handleWellBeing(userId, text);
                        break;
                    case FILL_OUT_THE_REPORT_CHANGE_IN_BEHAVIOR:
                        userTextMessageHandler.handleChangeInBehavior(userId, text);
                        break;
                }
            } else if (photoSizes != null) {
                switch (userState) {
                    case FILL_OUT_THE_REPORT_PHOTO:
                        userPhotoMessageHandler.handlePhoto(userId, photoSizes[photoSizes.length - 1]);
                        break;
                }
            }
        }
    }
}

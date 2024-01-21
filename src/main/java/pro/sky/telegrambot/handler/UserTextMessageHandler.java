package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.*;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.service.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserTextMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTextMessageHandler.class);
    private static final Pattern PATTERN_PHONE = Pattern.compile("(\\+7-9[0-9]{2}-[0-9]{3}-[0-9]{2}-[0-9]{2})");
    private final InlineKeyboardService inlineKeyboardService;
    private final TelegramBotService telegramBotService;
    private final UserService userService;
    private final VolunteerService volunteerService;
    private final PetReportService petReportService;
    private final ProbationService probationService;
    private final MessageService messageService;

    public UserTextMessageHandler(InlineKeyboardService inlineKeyboardService,
                                  TelegramBotService telegramBotService,
                                  UserService userService,
                                  VolunteerService volunteerService,
                                  PetReportService petReportService,
                                  ProbationService probationService,
                                  MessageService messageService) {
        this.inlineKeyboardService = inlineKeyboardService;
        this.telegramBotService = telegramBotService;
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.petReportService = petReportService;
        this.probationService = probationService;
        this.messageService = messageService;
    }

    /**
     * This method handles the start command
     *
     * @param userId    ID of the user who sent the message
     * @param text      text from message
     * @param messageId ID of the message sent by the user
     */
    public void handleStart(Long userId, String text, Integer messageId) {
        if ("/start".equals(text)) {
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getChooseShelterUserMenuKeyboard();
            String textMessage = "Выберите приют:";

            if (messageId == null) {
                telegramBotService.sendInlineKeyboard(userId, textMessage, inlineKeyboardMarkup);
            } else {
                telegramBotService.editInlineKeyboard(userId, messageId, textMessage, inlineKeyboardMarkup);
            }
            userService.setUserState(userId, UserState.CHOOSE_SHELTER);
        } else {
            telegramBotService.sendMessage(userId, "Введите команду /start");
        }
    }

    public void handleStart(Long userId, String text) {
        handleStart(userId, text, null);
    }

    /**
     * This method sends a message to the volunteer while user chatting with him or ends the conversation
     *
     * @param userId
     * @param text
     */
    public void sendMessageToVolunteer(Long userId, String text) {
        Volunteer volunteer = volunteerService.getVolunteerByUserId(userId);

        if ("/stop".equals(text)) {
            userService.stopConversation(userId);
            volunteerService.stopConversation(volunteer.getId());
            telegramBotService.sendMessage(volunteer.getId(), "Пользователь завершил разговор");
            handleStart(userId, "/start");
        } else {
            telegramBotService.sendMessage(volunteer.getId(), text);
        }
    }

    /**
     * This method handles the user's phone number
     *
     * @param userId
     * @param text
     */
    public void handleUserPhoneNumber(Long userId, String text) {
        Matcher matcher = PATTERN_PHONE.matcher(text);

        if (matcher.matches()) {
            String phoneNumber = matcher.group(1);
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getInfoAboutShelterUserMenuKeyboard(UserCommand.INFO_ABOUT_SHELTER);
            ShelterType shelterType = ShelterType.valueOf(userService.getSelectedShelter(userId));
            String message = messageService.getMessage("PHONE_NUMBER_SAVED") + "\n\n" + messageService.getMessage("INFO_ABOUT_SHELTER", shelterType);
            LOGGER.debug(phoneNumber);

            userService.setPhoneNumber(userId, phoneNumber);
            userService.setUserState(userId, UserState.INFO_ABOUT_SHELTER);
            telegramBotService.sendInlineKeyboard(userId, message, inlineKeyboardMarkup);
        } else {
            telegramBotService.sendMessage(userId, "Напишите свой номер телефона в формате: +7-9**-***-**-**");
        }
    }

    public void handleDiet(Long userId, String text) {
        PetReport petReport = petReportService.getReportByUserIdAndState(userId);

        petReportService.setDiet(petReport.getId(), text);
        userService.setUserState(userId, UserState.FILL_OUT_THE_REPORT_WELL_BEING);
        telegramBotService.sendMessage(userId, "Опишите общее самочувствие животного и привыкание к новому месту");
    }

    public void handleWellBeing(Long userId, String text) {
        PetReport petReport = petReportService.getReportByUserIdAndState(userId);

        petReportService.setWellBeing(petReport.getId(), text);
        userService.setUserState(userId, UserState.FILL_OUT_THE_REPORT_CHANGE_IN_BEHAVIOR);
        telegramBotService.sendMessage(userId, "Опишите изменения в поведении");
    }

    public void handleChangeInBehavior(Long userId, String text) {
        PetReport petReport = petReportService.getReportByUserIdAndState(userId);
        Pet pet = petReport.getPet();
        ShelterType shelterType = pet.getKindOfPet().equals("CAT") ? ShelterType.CAT_SHELTER : ShelterType.DOG_SHELTER;
        Probation probation = probationService.getProbationByUserIdAndShelterTypeAndState(userId, shelterType, ProbationState.FILLING_REPORT);
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getUserMainMenuKeyboard();
        String textMessage = messageService.getMessage("USER_MAIN_MENU", shelterType);

        petReportService.setChangeInBehavior(petReport.getId(), text);
        petReportService.setReportState(petReport.getId(), PetReportState.WAITING_FOR_VERIFICATION);
        probationService.setProbationState(probation.getId(), ProbationState.REPORT_IN_VERIFICATION);
        telegramBotService.sendInlineKeyboard(userId, textMessage, inlineKeyboardMarkup);
        userService.setUserState(userId, UserState.MAIN_MENU);
    }
}

package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.enums.*;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.service.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Transactional
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
        User user = userService.getUser(userId);
        ShelterType shelterType = ShelterType.valueOf(user.getSelectedShelter());
        PetReport petReport = petReportService.getReport(userId, shelterType, PetReportState.FILLING);

        petReportService.setDiet(petReport.getId(), text);
        userService.setUserState(userId, UserState.FILL_OUT_THE_REPORT_WELL_BEING);
        telegramBotService.sendMessage(userId, "Опишите общее самочувствие животного и привыкание к новому месту");
    }

    public void handleWellBeing(Long userId, String text) {
        User user = userService.getUser(userId);
        ShelterType shelterType = ShelterType.valueOf(user.getSelectedShelter());
        PetReport petReport = petReportService.getReport(userId, shelterType, PetReportState.FILLING);

        petReportService.setWellBeing(petReport.getId(), text);
        userService.setUserState(userId, UserState.FILL_OUT_THE_REPORT_CHANGE_IN_BEHAVIOR);
        telegramBotService.sendMessage(userId, "Опишите изменения в поведении");
    }

    public void handleChangeInBehavior(Long userId, String text) {
        User user = userService.getUser(userId);
        ShelterType shelterType = ShelterType.valueOf(user.getSelectedShelter());
        PetReport petReport = petReportService.getReport(userId, shelterType, PetReportState.FILLING);
        Probation probation = probationService.getProbationByUserIdAndShelterTypeAndState(userId, shelterType.name(), ProbationState.FILLING_REPORT);
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getUserMainMenuKeyboard();
        String textMessage = messageService.getMessage("USER_MAIN_MENU", shelterType);

        petReportService.setChangeInBehavior(petReport.getId(), text);
        petReportService.setReportState(petReport.getId(), PetReportState.WAITING_FOR_VERIFICATION);
        probationService.setProbationState(probation.getId(), ProbationState.REPORT_IN_VERIFICATION);
        telegramBotService.sendInlineKeyboard(userId, textMessage, inlineKeyboardMarkup);
        userService.setUserState(userId, UserState.MAIN_MENU);
    }
}

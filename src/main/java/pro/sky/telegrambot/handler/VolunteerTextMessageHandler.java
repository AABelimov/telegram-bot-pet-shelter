package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.enums.VolunteerState;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.service.*;

/**
 * This class handles commands coming from the volunteer
 */
@Component
public class VolunteerTextMessageHandler {

    private final VolunteerService volunteerService;
    private final UserService userService;
    private final TelegramBotService telegramBotService;
    private final InlineKeyboardService inlineKeyboardService;
    private final PetReportService petReportService;
    private final ProbationService probationService;
    private final OverdueReportService overdueReportService;

    public VolunteerTextMessageHandler(VolunteerService volunteerService,
                                       UserService userService,
                                       TelegramBotService telegramBotService,
                                       InlineKeyboardService inlineKeyboardService,
                                       PetReportService petReportService,
                                       ProbationService probationService,
                                       OverdueReportService overdueReportService) {
        this.volunteerService = volunteerService;
        this.userService = userService;
        this.telegramBotService = telegramBotService;
        this.inlineKeyboardService = inlineKeyboardService;
        this.petReportService = petReportService;
        this.probationService = probationService;
        this.overdueReportService = overdueReportService;
    }

    @Transactional
    public void sendMessageToUser(Long volunteerId, String text) {
        Long userId = volunteerService.getUserIdByVolunteerId(volunteerId);

        if ("/stop".equals(text)) {
            userService.stopConversation(userId);
            volunteerService.stopConversation(volunteerId);
            telegramBotService.sendMessage(userId, "Собеседник завершил разговор");
            handleStart(volunteerId, "/start", null);
        } else {
            telegramBotService.sendMessage(userId, text);
        }
    }

    @Transactional
    public void handleStart(Long volunteerId, String text, Integer messageId) {
        if ("/start".equals(text)) {
            int countReports = petReportService.getReportsByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_VERIFICATION).size();
            int countOverdueReports = overdueReportService.getOverdueReportsByVolunteerId(volunteerId).size();
            int countProbation =  probationService.getProbationListByVolunteerIdAndState(volunteerId, ProbationState.ON_THE_DECISION).size();
            Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getVolunteerMainMenu();
            String textMessage = String.format("Привет, %s!\n\nНепроверенных отчетов: %d." +
                            "\n\nДолжников по отчетам: %d.\n\nЗаканчивающихся сегодня испытательных сроков: %d.",
                    volunteer.getName(), countReports, countOverdueReports, countProbation);
            volunteerService.setVolunteerState(volunteerId, VolunteerState.MAIN_MENU);

            if (messageId == null) {
                telegramBotService.sendInlineKeyboard(volunteerId, textMessage, inlineKeyboardMarkup);
            } else {
                telegramBotService.editInlineKeyboard(volunteerId, messageId, textMessage, inlineKeyboardMarkup);
            }
        }
    }

    @Transactional
    public void handleCommentaryOnTheReport(Long volunteerId, String text) {
        PetReport petReport = petReportService.getReportByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_COMMENT);
        User user = petReport.getUser();
        Pet pet = petReport.getPet();
        String textMessage = String.format("%s, ваш отчет по животному с кличкой %s не был принят по причине:\n%s",
                user.getName(), pet.getName(), text);

        telegramBotService.sendMessage(user.getId(), textMessage);
        petReportService.setReportState(petReport.getId(), PetReportState.DENIED);
        handleStart(volunteerId, "/start", null);
    }
}

package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.enums.VolunteerCommand;
import pro.sky.telegrambot.enums.VolunteerState;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.service.*;

import java.io.File;
import java.nio.file.Path;

@Component
public class VolunteerDataCallbackQueryHandler {

    private final VolunteerService volunteerService;
    private final TelegramBotService telegramBotService;
    private final PetReportService petReportService;
    private final InlineKeyboardService inlineKeyboardService;
    private final VolunteerTextMessageHandler volunteerTextMessageHandler;
    private final ProbationService probationService;

    public VolunteerDataCallbackQueryHandler(VolunteerService volunteerService,
                                             TelegramBotService telegramBotService,
                                             PetReportService petReportService,
                                             InlineKeyboardService inlineKeyboardService,
                                             VolunteerTextMessageHandler volunteerTextMessageHandler,
                                             ProbationService probationService) {
        this.volunteerService = volunteerService;
        this.telegramBotService = telegramBotService;
        this.petReportService = petReportService;
        this.inlineKeyboardService = inlineKeyboardService;
        this.volunteerTextMessageHandler = volunteerTextMessageHandler;
        this.probationService = probationService;
    }

    public void handleMainMenu(Long volunteerId, Integer messageId, String data) {
        VolunteerCommand volunteerCommand = VolunteerCommand.valueOf(data);

        switch (volunteerCommand) {
            case CHECK_REPORTS:
                volunteerService.setVolunteerState(volunteerId, VolunteerState.CHECK_REPORTS);
                telegramBotService.deleteMessage(volunteerId, messageId);
                showReport(volunteerId, null);
                break;
        }
    }

    public void handleCheckReports(Long volunteerId, Integer messageId, String data) {
        VolunteerCommand volunteerCommand = VolunteerCommand.valueOf(data);

        switch (volunteerCommand) {
            case FINISH_VIEWING_REPORTS:
                handleFinishViewingReports(volunteerId, messageId);
                break;
            case ACCEPT_REPORT:
                handleAcceptReport(volunteerId, messageId);
                break;
        }
    }

    private void showReport(Long volunteerId, Integer messageId) {
        PetReport petReport = petReportService.getReportByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_VERIFICATION);
        User user = petReport.getUser();
        Pet pet = petReport.getPet();
        Path photoPath = Path.of(petReport.getPhotoPath());
        File photo = photoPath.toFile();
        String diet = petReport.getDiet();
        String wellBeing = petReport.getWellBeing();
        String changeInBehavior = petReport.getChangeInBehavior();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getShowReportVolunteerMenuKeyboard();
        String text = String.format("Усыновитель: %s\nПитомец: %s\nРацион: %s\nСамочувствие: %s\nИзменения в привычках: %s",
        user.getName(), pet.getName(), diet, wellBeing, changeInBehavior);

        petReportService.setReportState(petReport.getId(), PetReportState.WAITING_FOR_A_DECISION);

        if (messageId == null) {
            telegramBotService.sendInlineKeyboard(volunteerId, photo, text, inlineKeyboardMarkup);
        } else {
            telegramBotService.editInlineKeyboard(volunteerId, messageId, photo, text, inlineKeyboardMarkup);
        }
    }

    private void handleFinishViewingReports(Long volunteerId, Integer messageId) {
        PetReport petReport = petReportService.getReportByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_A_DECISION);
        petReportService.setReportState(petReport.getId(), PetReportState.WAITING_FOR_VERIFICATION);
        telegramBotService.deleteMessage(volunteerId, messageId);
        volunteerTextMessageHandler.handleStart(volunteerId, "/start");
    }

    private void handleAcceptReport(Long volunteerId, Integer messageId) {
        PetReport petReport = petReportService.getReportByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_A_DECISION);
        Probation probation = probationService.getProbationByPetId(petReport.getPet().getId());

        petReportService.setReportState(petReport.getId(), PetReportState.ACCEPT);
        petReportService.setTimeSendingReport(petReport.getId());
        probationService.setProbationState(probation.getId(), ProbationState.REPORT_ACCEPTED);
        probationService.setLastReportDate(probation.getId());
        showReport(volunteerId, messageId);
    }
}

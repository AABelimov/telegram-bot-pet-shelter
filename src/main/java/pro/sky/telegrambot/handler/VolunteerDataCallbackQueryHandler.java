package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.*;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.service.*;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * This class handles the data coming from the volunteer in the callback query
 */
@Component
public class VolunteerDataCallbackQueryHandler {

    private final VolunteerService volunteerService;
    private final TelegramBotService telegramBotService;
    private final PetReportService petReportService;
    private final InlineKeyboardService inlineKeyboardService;
    private final VolunteerTextMessageHandler volunteerTextMessageHandler;
    private final ProbationService probationService;
    private final AdoptionService adoptionService;
    private final PetService petService;
    private final OverdueReportService overdueReportService;

    public VolunteerDataCallbackQueryHandler(VolunteerService volunteerService,
                                             TelegramBotService telegramBotService,
                                             PetReportService petReportService,
                                             InlineKeyboardService inlineKeyboardService,
                                             VolunteerTextMessageHandler volunteerTextMessageHandler,
                                             ProbationService probationService,
                                             AdoptionService adoptionService,
                                             PetService petService,
                                             OverdueReportService overdueReportService) {
        this.volunteerService = volunteerService;
        this.telegramBotService = telegramBotService;
        this.petReportService = petReportService;
        this.inlineKeyboardService = inlineKeyboardService;
        this.volunteerTextMessageHandler = volunteerTextMessageHandler;
        this.probationService = probationService;
        this.adoptionService = adoptionService;
        this.petService = petService;
        this.overdueReportService = overdueReportService;
    }

    public void handleMainMenu(Long volunteerId, Integer messageId, String data) {
        VolunteerCommand volunteerCommand = VolunteerCommand.valueOf(data);
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getVolunteerMainMenu();

        switch (volunteerCommand) {
            case CHECK_REPORTS:
                checkReports(volunteerId, messageId);
                break;
            case OVERDUE_REPORTS:
                overdueReports(volunteerId, messageId);
                break;
            case DECIDE_ON_PROBATION:
                decideOnProbation(volunteerId, messageId);
                break;
            case AT_WORK:
                volunteerService.setVolunteerState(volunteerId, VolunteerState.AT_WORK);
                telegramBotService.editInlineKeyboard(volunteerId, messageId, "Режим - на работе", inlineKeyboardMarkup);
                break;
            case NOT_AT_WORK:
                volunteerService.setVolunteerState(volunteerId, VolunteerState.NOT_AT_WORK);
                telegramBotService.editInlineKeyboard(volunteerId, messageId, "Режим - не на работе", inlineKeyboardMarkup);
                break;
        }
    }

    public void handleCheckReports(Long volunteerId, Integer messageId, String data) {
        VolunteerCommand volunteerCommand = VolunteerCommand.valueOf(data);

        switch (volunteerCommand) {
            case ACCEPT_REPORT:
                acceptReport(volunteerId, messageId);
                break;
            case DENY_REPORT:
                denyReport(volunteerId, messageId);
                break;
            case FINISH_VIEWING_REPORTS:
                finishViewingReports(volunteerId, messageId);
                break;
        }
    }

    public void handleOverdueReports(Long volunteerId, Integer messageId, String data) {
        VolunteerCommand volunteerCommand = VolunteerCommand.valueOf(data);

        switch (volunteerCommand) {
            case BACK:
                volunteerTextMessageHandler.handleStart(volunteerId, "/start", messageId);
                break;
        }
    }

    public void handleDecideOnProbation(Long volunteerId, Integer messageId, String data) {
        VolunteerCommand volunteerCommand = VolunteerCommand.valueOf(data);

        switch (volunteerCommand) {
            case ALLOW_ADOPTION:
                allowAdoption(volunteerId, messageId);
                break;
            case REFUSE_ADOPTION:
                refuseAdoption(volunteerId, messageId);
                break;
            case EXTEND_FOR_FOURTEEN_DAYS:
                extendProbation(volunteerId, messageId, 14);
                break;
            case EXTEND_FOR_THIRTY_DAYS:
                extendProbation(volunteerId, messageId, 30);
                break;
            case FINISH_VIEWING_DECIDE_ON_PROBATION:
                finishViewingDecideOnProbation(volunteerId, messageId);
                break;
        }
    }

    private void overdueReports(Long volunteerId, Integer messageId) {
        List<OverdueReport> overdueReports = overdueReportService.getOverdueReportsByVolunteerId(volunteerId);
        StringBuilder text = new StringBuilder();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getVolunteerOverdueReportsMenu();
        int count = 1;

        for (OverdueReport o : overdueReports) {
            text.append(String.format("%d.\nИмя: %s\nНомер телефона: %s\nДней не сдает отчет: %d\n\n",
                    count++, o.getUser().getName(), o.getUser().getPhoneNumber(), o.getDays()));
        }
        if (overdueReports.size() == 0) {
            text.append("Должников нет");
        }

        telegramBotService.editInlineKeyboard(volunteerId, messageId, text.toString(), inlineKeyboardMarkup);
        volunteerService.setVolunteerState(volunteerId, VolunteerState.OVERDUE_REPORTS);
    }

    private void checkReports(Long volunteerId, Integer messageId) {
        PetReport petReport = petReportService.getReportByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_VERIFICATION);
        InlineKeyboardMarkup inlineKeyboardMarkup;
        String text;

        if (petReport == null) {
            text = "Отчетов для проверки нет";
            inlineKeyboardMarkup = inlineKeyboardService.getVolunteerMainMenu();
            telegramBotService.editInlineKeyboard(volunteerId, messageId, text, inlineKeyboardMarkup);
        } else {
            volunteerService.setVolunteerState(volunteerId, VolunteerState.CHECK_REPORTS);
            telegramBotService.deleteMessage(volunteerId, messageId);
            showReport(volunteerId, null, petReport);
        }
    }

    private void decideOnProbation(Long volunteerId, Integer messageId) {
        Probation probation = probationService.getProbationByVolunteerIdAndState(volunteerId, ProbationState.ON_THE_DECISION);
        InlineKeyboardMarkup inlineKeyboardMarkup;
        String text;

        if (probation == null) {
            text = "Заканчивающихся сегодня испытательных сроков нет";
            inlineKeyboardMarkup = inlineKeyboardService.getVolunteerMainMenu();
            telegramBotService.editInlineKeyboard(volunteerId, messageId, text, inlineKeyboardMarkup);
        } else {
            volunteerService.setVolunteerState(volunteerId, VolunteerState.DECIDE_ON_PROBATION);
            telegramBotService.deleteMessage(volunteerId, messageId);
            showProbation(volunteerId, null, probation);
        }
    }

    private void showReport(Long volunteerId, Integer messageId, PetReport petReport) {
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

    private void showProbation(Long volunteerId, Integer messageId, Probation probation) {
        Pet pet = probation.getPet();
        User user = probation.getUser();
        Path photoPath = Path.of(pet.getPhotoPath());
        File photo = photoPath.toFile();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.getShowProbationVolunteerMenuKeyboard();
        String text = String.format("Питомец: %s\nУсыновитель: %s\n", pet.getName(), user.getName());

        probationService.setProbationState(probation.getId(), ProbationState.WAITING_FOR_A_DECISION);

        if (messageId == null) {
            telegramBotService.sendInlineKeyboard(volunteerId, photo, text, inlineKeyboardMarkup);
        } else {
            telegramBotService.editInlineKeyboard(volunteerId, messageId, photo, text, inlineKeyboardMarkup);
        }
    }

    private void acceptReport(Long volunteerId, Integer messageId) {
        PetReport petReport = petReportService.getReportByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_A_DECISION);

        petReportService.acceptReport(petReport.getId());

        petReport = petReportService.getReportByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_VERIFICATION);

        if (petReport == null) {
            telegramBotService.deleteMessage(volunteerId, messageId);
            volunteerTextMessageHandler.handleStart(volunteerId, "/start", null);
        } else {
            showReport(volunteerId, messageId, petReport);
        }
    }

    private void allowAdoption(Long volunteerId, Integer messageId) {
        Probation probation = probationService.getProbationByVolunteerIdAndState(volunteerId, ProbationState.WAITING_FOR_A_DECISION);
        User user = probation.getUser();
        Pet pet = probation.getPet();
        String text = String.format("Вы прошли испытательный срок, поздравляем!\n%s теперь ваш", pet.getName());

        adoptionService.createAdoption(user, pet);
        probationService.deleteProbation(probation);
        petService.setPetState(pet.getId(), PetState.ADOPTED);
        telegramBotService.sendMessage(user.getId(), text);

        probation = probationService.getProbationByVolunteerIdAndState(volunteerId, ProbationState.ON_THE_DECISION);

        if (probation == null) {
            telegramBotService.deleteMessage(volunteerId, messageId);
            volunteerTextMessageHandler.handleStart(volunteerId, "/start", null);
        } else {
            showProbation(volunteerId, messageId, probation);
        }
    }

    private void denyReport(Long volunteerId, Integer messageId) {
        PetReport petReport = petReportService.getReportByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_A_DECISION);
        Probation probation = probationService.getProbationByPetId(petReport.getPet().getId());

        petReportService.setReportState(petReport.getId(), PetReportState.WAITING_FOR_COMMENT);
        probationService.setProbationState(probation.getId(), ProbationState.REPORT_DENIED);
        telegramBotService.deleteMessage(volunteerId, messageId);
        volunteerService.setVolunteerState(volunteerId, VolunteerState.COMMENTARY_ON_THE_REPORT);
        telegramBotService.sendMessage(volunteerId, "Опишите проблему в отчете");
    }

    private void refuseAdoption(Long volunteerId, Integer messageId) {
        Probation probation = probationService.getProbationByVolunteerIdAndState(volunteerId, ProbationState.WAITING_FOR_A_DECISION);
        User user = probation.getUser();
        Pet pet = probation.getPet();
        String text = String.format("Вы не прошли испытательный срок, %s должен вернуться к нам", pet.getName());

        probationService.deleteProbation(probation);
        petService.setPetState(pet.getId(), PetState.WAITING_TO_BE_ADOPTED);
        telegramBotService.sendMessage(user.getId(), text);

        probation = probationService.getProbationByVolunteerIdAndState(volunteerId, ProbationState.ON_THE_DECISION);

        if (probation == null) {
            telegramBotService.deleteMessage(volunteerId, messageId);
            volunteerTextMessageHandler.handleStart(volunteerId, "/start", null);
        } else {
            showProbation(volunteerId, messageId, probation);
        }
    }

    private void extendProbation(Long volunteerId, Integer messageId, int days) {
        Probation probation = probationService.getProbationByVolunteerIdAndState(volunteerId, ProbationState.WAITING_FOR_A_DECISION);
        User user = probation.getUser();
        Pet pet = probation.getPet();

        probationService.extendProbation(probation.getId(), days);
        probationService.setProbationState(probation.getId(), ProbationState.WAITING_FOR_A_NEW_REPORT);
        telegramBotService.sendMessage(user.getId(), String.format("Вам добавили %d дней к испытательному сроку для %s", days, pet.getName()));

        probation = probationService.getProbationByVolunteerIdAndState(volunteerId, ProbationState.ON_THE_DECISION);

        if (probation == null) {
            telegramBotService.deleteMessage(volunteerId, messageId);
            volunteerTextMessageHandler.handleStart(volunteerId, "/start", null);
        } else {
            showProbation(volunteerId, messageId, probation);
        }
    }

    private void finishViewingReports(Long volunteerId, Integer messageId) {
        PetReport petReport = petReportService.getReportByVolunteerIdAndState(volunteerId, PetReportState.WAITING_FOR_A_DECISION);
        petReportService.setReportState(petReport.getId(), PetReportState.WAITING_FOR_VERIFICATION);
        telegramBotService.deleteMessage(volunteerId, messageId);
        volunteerTextMessageHandler.handleStart(volunteerId, "/start", null);
    }

    private void finishViewingDecideOnProbation(Long volunteerId, Integer messageId) {
        Probation probation = probationService.getProbationByVolunteerIdAndState(volunteerId, ProbationState.WAITING_FOR_A_DECISION);
        probationService.setProbationState(probation.getId(), ProbationState.ON_THE_DECISION);
        telegramBotService.deleteMessage(volunteerId, messageId);
        volunteerTextMessageHandler.handleStart(volunteerId, "/start", null);
    }
}

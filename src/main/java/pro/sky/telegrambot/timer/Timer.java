package pro.sky.telegrambot.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.model.OverdueReport;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.service.OverdueReportService;
import pro.sky.telegrambot.service.ProbationService;
import pro.sky.telegrambot.service.TelegramBotService;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class Timer {

    private final ProbationService probationService;
    private final TelegramBotService telegramBotService;
    private final OverdueReportService overdueReportService;

    public Timer(ProbationService probationService,
                 TelegramBotService telegramBotService,
                 OverdueReportService overdueReportService) {
        this.probationService = probationService;
        this.telegramBotService = telegramBotService;
        this.overdueReportService = overdueReportService;
    }

    @Transactional
    @Scheduled(cron = "00 50 23 * * *")
    public void checkLastReport() {
        List<Probation> probationList = probationService.getAll();
        LocalDateTime currentDay = LocalDateTime.now();

        probationList.forEach(probation -> {

            if (probation.getLastReportDate().truncatedTo(ChronoUnit.DAYS).plusDays(1).isEqual(currentDay.truncatedTo(ChronoUnit.DAYS))) {
                overdueReportService.createOverdueReport(probation, 1);
            } else if (probation.getLastReportDate().truncatedTo(ChronoUnit.DAYS).plusDays(2).isEqual(currentDay.truncatedTo(ChronoUnit.DAYS))) {
                overdueReportService.createOverdueReport(probation, 2);
            } else if (probation.getLastReportDate().truncatedTo(ChronoUnit.DAYS).plusDays(2).isBefore(currentDay.truncatedTo(ChronoUnit.DAYS))) {
                Period period = Period.between(probation.getLastReportDate().truncatedTo(ChronoUnit.DAYS).toLocalDate(),
                        currentDay.truncatedTo(ChronoUnit.DAYS).toLocalDate());
                overdueReportService.createOverdueReport(probation, period.getDays());
            }

            probationService.setProbationState(probation.getId(), ProbationState.WAITING_FOR_A_NEW_REPORT);
        });
    }

    @Transactional
    @Scheduled(cron = "00 00 01 * * *")
    public void checkEndProbation() {
        List<Probation> probationList = probationService.getAll();
        LocalDateTime currentDay = LocalDateTime.now();

        probationList.forEach(probation -> {
            if (probation.getProbationEndDate().truncatedTo(ChronoUnit.DAYS).isEqual(currentDay.truncatedTo(ChronoUnit.DAYS))) {
                probationService.setProbationState(probation.getId(), ProbationState.ON_THE_DECISION);
                overdueReportService.deleteOverdueReport(probation);
            }
        });
    }

    @Transactional
    @Scheduled(cron = "00 00 07 * * *")
    public void sendNotificationAboutReport() {
        List<OverdueReport> overdueReports = overdueReportService.getAll();

        overdueReports.forEach(overdueReport -> {
            if (overdueReport.getDays() == 1) {
                telegramBotService.sendMessage(overdueReport.getUser().getId(),
                        String.format("Вчера от вас не дождались отчета по питомцу %s (либо отчет не приняли)", overdueReport.getPet().getName()));
            } else if (overdueReport.getDays() == 2) {
                telegramBotService.sendMessage(overdueReport.getUser().getId(),
                        String.format("Второй день от вас нет отчета по питомцу %s (либо отчет не приняли)." +
                                " Если сегодня его не будет, то завтра с вами свяжется волонтер", overdueReport.getPet().getName()));
            } else if (overdueReport.getDays() > 2) {
                telegramBotService.sendMessage(overdueReport.getUser().getId(), "Скоро с вами свяжется волонтер из-за не досданных отчетов");
            }
        });
    }
}

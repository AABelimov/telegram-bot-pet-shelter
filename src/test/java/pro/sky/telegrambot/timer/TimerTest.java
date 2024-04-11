package pro.sky.telegrambot.timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.service.OverdueReportService;
import pro.sky.telegrambot.service.ProbationService;
import pro.sky.telegrambot.service.TelegramBotService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static pro.sky.telegrambot.constants.ProbationConstants.*;
import static pro.sky.telegrambot.constants.OverdueReportConstants.*;

class TimerTest {

    private final ProbationService probationService = mock(ProbationService.class);
    private final TelegramBotService telegramBotService = mock(TelegramBotService.class);
    private final OverdueReportService overdueReportService = mock(OverdueReportService.class);

    private Timer out;

    @BeforeEach
    public void initOut() {
        out = new Timer(probationService, telegramBotService, overdueReportService);
    }

    @Test
    void testCheckLastReport() {
        when(probationService.getAll()).thenReturn(PROBATION_LIST);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(LocalDateTime.MIN.plusDays(4));
            out.checkLastReport();
        }

        verify(overdueReportService, times(1)).createOverdueReport(eq(PROBATION_1), eq(3));
        verify(overdueReportService, times(1)).createOverdueReport(eq(PROBATION_2), eq(2));
        verify(overdueReportService, times(1)).createOverdueReport(eq(PROBATION_3), eq(1));
        verify(probationService, times(3)).setProbationState(any(), any());
    }

    @Test
    void testCheckEndProbation() {
        when(probationService.getAll()).thenReturn(PROBATION_LIST);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(LocalDateTime.MIN.plusDays(32));
            out.checkEndProbation();
        }

        verify(probationService, times(0)).setProbationState(eq(PROBATION_ID_1), eq(ProbationState.ON_THE_DECISION));
        verify(overdueReportService, times(0)).deleteOverdueReport(eq(PROBATION_1));
        verify(probationService, times(1)).setProbationState(eq(PROBATION_ID_2), eq(ProbationState.ON_THE_DECISION));
        verify(overdueReportService, times(1)).deleteOverdueReport(eq(PROBATION_2));
        verify(probationService, times(0)).setProbationState(eq(PROBATION_ID_3), eq(ProbationState.ON_THE_DECISION));
        verify(overdueReportService, times(0)).deleteOverdueReport(eq(PROBATION_3));
    }

    @Test
    void testSendNotificationAboutReport() {
        when(overdueReportService.getAll()).thenReturn(OVERDUE_REPORT_LIST);

        out.sendNotificationAboutReport();

        verify(telegramBotService).sendMessage(eq(OVERDUE_REPORT_1.getUser().getId()),
                eq(String.format("Вчера от вас не дождались отчета по питомцу %s (либо отчет не приняли)", OVERDUE_REPORT_1.getPet().getName())));

        verify(telegramBotService).sendMessage(eq(OVERDUE_REPORT_2.getUser().getId()),
                eq(String.format("Второй день от вас нет отчета по питомцу %s (либо отчет не приняли)." +
                        " Если сегодня его не будет, то завтра с вами свяжется волонтер", OVERDUE_REPORT_2.getPet().getName())));

        verify(telegramBotService).sendMessage(eq(OVERDUE_REPORT_3.getUser().getId()),
                eq("Скоро с вами свяжется волонтер из-за не досданных отчетов"));
    }

}

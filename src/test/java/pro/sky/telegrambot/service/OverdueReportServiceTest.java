package pro.sky.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.repository.OverdueReportRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static pro.sky.telegrambot.constants.ProbationConstants.*;
import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;
import static pro.sky.telegrambot.constants.OverdueReportConstants.*;

@ExtendWith(MockitoExtension.class)
class OverdueReportServiceTest {

    private final OverdueReportRepository overdueReportRepositoryMock = mock(OverdueReportRepository.class);
    private OverdueReportService out;

    @BeforeEach
    public void initOut() {
        out = new OverdueReportService(overdueReportRepositoryMock);
    }

    @Test
    void testCreateOverdueReport() {
        out.createOverdueReport(PROBATION_1, OVERDUE_REPORT_DAYS_1);
        verify(overdueReportRepositoryMock).save(argThat(x -> {
            assertEquals(OVERDUE_REPORT_ID_1, x.getId());
            assertEquals(OVERDUE_REPORT_DAYS_1, x.getDays());
            assertEquals(USER_1, x.getUser());
            assertEquals(PET_1, x.getPet());
            assertEquals(VOLUNTEER_1, x.getVolunteer());
            return true;
        }));
    }
}
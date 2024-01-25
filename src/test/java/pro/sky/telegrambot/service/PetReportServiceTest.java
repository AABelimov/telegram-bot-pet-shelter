package pro.sky.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.mapper.PetReportMapper;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.PetReportConstants.*;
import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

@ExtendWith(MockitoExtension.class)
class PetReportServiceTest {

    private final PetReportRepository petReportRepositoryMock = mock(PetReportRepository.class);
    private final UserService userServiceMock = mock(UserService.class);
    private final TelegramBotService telegramBotServiceMock = mock(TelegramBotService.class);
    private final PetReportMapper petReportMapperMock = mock(PetReportMapper.class);
    private final ProbationService probationServiceMock = mock(ProbationService.class);
    private final OverdueReportService overdueReportServiceMock = mock(OverdueReportService.class);
    private PetReportService out;

    @BeforeEach
    public void initOut() {
        out = new PetReportService(petReportRepositoryMock,
                userServiceMock, telegramBotServiceMock,
                petReportMapperMock, probationServiceMock,
                overdueReportServiceMock);
    }

    @Test
    void shouldCallMethodToSavePetReport() {
        out.createPetReport(PET_1, USER_1, VOLUNTEER_1, ShelterType.CAT_SHELTER);
        verify(petReportRepositoryMock).save(argThat(x -> {
            assertEquals(PET_1, x.getPet());
            assertEquals(ShelterType.CAT_SHELTER.name(), x.getShelterType());
            assertEquals(ShelterType.CAT_SHELTER.name(), x.getShelterType());
            assertEquals(USER_1, x.getUser());
            assertEquals(VOLUNTEER_1, x.getVolunteer());
            assertEquals(PET_REPORT_STATE_1, x.getState());
            return true;
        }));
    }

    @Test
    void shouldReturnListPetReportDtoOutAfterMappingPetReports() {
        when(petReportRepositoryMock.findAllByState(eq(PetReportState.WAITING_FOR_VERIFICATION.name()))).thenReturn(UNVERIFIED_REPORTS_LIST);
        when(petReportMapperMock.toDto(PET_REPORT_1)).thenReturn(PET_REPORT_DTO_OUT_1);
        when(petReportMapperMock.toDto(PET_REPORT_2)).thenReturn(PET_REPORT_DTO_OUT_2);
        when(petReportMapperMock.toDto(PET_REPORT_3)).thenReturn(PET_REPORT_DTO_OUT_3);

        assertIterableEquals(UNVERIFIED_REPORTS_DTO_OUT_LIST, out.getUnverifiedReports());
    }

}
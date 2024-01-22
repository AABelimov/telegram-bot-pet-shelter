package pro.sky.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.mapper.PetReportMapper;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pro.sky.telegrambot.constants.PetReportConstants.*;

@ExtendWith(MockitoExtension.class)
class PetReportServiceTest {

    private final PetReportRepository petReportRepositoryMock = mock(PetReportRepository.class);
    private final UserService userServiceMock = mock(UserService.class);
    private final TelegramBotService telegramBotServiceMock = mock(TelegramBotService.class);
    private final PetReportMapper petReportMapperMock = spy(PetReportMapper.class);
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
    void shouldReturnPetReportDtoOutWhenIdIsCorrect() {
        when(petReportRepositoryMock.findById(PET_REPORT_ID_1)).thenReturn(Optional.of(PET_REPORT_1));
    }
}
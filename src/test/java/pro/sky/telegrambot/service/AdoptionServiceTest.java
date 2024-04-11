package pro.sky.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.mapper.AdoptionMapper;
import pro.sky.telegrambot.repository.AdoptionRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pro.sky.telegrambot.constants.AdoptionConstants.*;
import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.ProbationConstants.*;

@ExtendWith({MockitoExtension.class})
class AdoptionServiceTest {

    private final AdoptionRepository adoptionRepositoryMock = mock(AdoptionRepository.class);
    private final ProbationService probationServiceMock = mock(ProbationService.class);
    private final PetService petServiceMock = mock(PetService.class);
    private final TelegramBotService telegramBotServiceMock = mock(TelegramBotService.class);
    private final AdoptionMapper adoptionMapperMock = mock(AdoptionMapper.class);
    private AdoptionService out;

    @BeforeEach
    public void initOut() {
        out = new AdoptionService(
                adoptionRepositoryMock,
                probationServiceMock,
                petServiceMock,
                telegramBotServiceMock,
                adoptionMapperMock
        );
    }

    @Test
    void testCreateAdoption() {
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(ADOPTION_LOCAL_DATE_TIME_1);
            out.createAdoption(PROBATION_1);
            verify(adoptionRepositoryMock).save(argThat(x -> {
                assertNull(x.getId());
                assertEquals(USER_1, x.getUser());
                assertEquals(PET_1, x.getPet());
                assertEquals(ADOPTION_LOCAL_DATE_TIME_1, x.getAdoptionTime());
                return true;
            }));
        }
    }

}

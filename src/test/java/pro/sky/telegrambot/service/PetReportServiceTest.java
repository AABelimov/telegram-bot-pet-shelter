package pro.sky.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.exception.PetReportNotFoundException;
import pro.sky.telegrambot.mapper.PetReportMapper;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void testCreateReport() {
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
    void testFillReport() {
        out.fillReport(USER_ID_1, PET_REPORT_1);
        verify(userServiceMock, times(0)).setUserState(eq(USER_ID_1), eq(UserState.FILL_OUT_THE_REPORT_PHOTO));
        verify(telegramBotServiceMock, times(0)).sendMessage(eq(USER_ID_1), eq("Отправте фото животного"));

        out.fillReport(USER_ID_2, PET_REPORT_2);
        verify(userServiceMock, times(1)).setUserState(eq(USER_ID_2), eq(UserState.FILL_OUT_THE_REPORT_PHOTO));
        verify(telegramBotServiceMock, times(1)).sendMessage(eq(USER_ID_2), eq("Отправте фото животного"));
    }

    @Test
    void testSetPhotoPath() {
        when(petReportRepositoryMock.findById(eq(PET_REPORT_ID_2))).thenReturn(Optional.of(PET_REPORT_2));
        out.setPhotoPath(PET_REPORT_ID_2, PET_PHOTO_PATH_1);

        verify(petReportRepositoryMock).save(argThat(x -> {
            assertEquals(PET_REPORT_ID_2, x.getId());
            assertNotEquals(PET_PHOTO_PATH_2, x.getPhotoPath());
            assertEquals(PET_PHOTO_PATH_1, x.getPhotoPath());
            return true;
        }));

        assertThrows(PetReportNotFoundException.class, () -> out.setPhotoPath(PET_REPORT_ID_1, PET_REPORT_PHOTO_PATH_2));
    }

    @Test
    void testSetDiet() {
        when(petReportRepositoryMock.findById(eq(PET_REPORT_ID_2))).thenReturn(Optional.of(PET_REPORT_2));
        out.setDiet(PET_REPORT_ID_2, PET_REPORT_DIET_1);

        verify(petReportRepositoryMock).save(argThat(x -> {
            assertEquals(PET_REPORT_ID_2, x.getId());
            assertNotEquals(PET_REPORT_DIET_2, x.getDiet());
            assertEquals(PET_REPORT_DIET_1, x.getDiet());
            return true;
        }));

        assertThrows(PetReportNotFoundException.class, () -> out.setDiet(PET_REPORT_ID_1, PET_REPORT_DIET_2));
    }

    @Test
    void testSetWellBeing() {
        when(petReportRepositoryMock.findById(eq(PET_REPORT_ID_2))).thenReturn(Optional.of(PET_REPORT_2));
        out.setWellBeing(PET_REPORT_ID_2, PET_REPORT_WELL_BEING_1);

        verify(petReportRepositoryMock).save(argThat(x -> {
            assertEquals(PET_REPORT_ID_2, x.getId());
            assertNotEquals(PET_REPORT_WELL_BEING_2, x.getWellBeing());
            assertEquals(PET_REPORT_WELL_BEING_1, x.getWellBeing());
            return true;
        }));

        assertThrows(PetReportNotFoundException.class, () -> out.setWellBeing(PET_REPORT_ID_1, PET_REPORT_WELL_BEING_2));
    }

    @Test
    void testSetChangeInBehavior() {
        when(petReportRepositoryMock.findById(eq(PET_REPORT_ID_2))).thenReturn(Optional.of(PET_REPORT_2));
        out.setChangeInBehavior(PET_REPORT_ID_2, PET_REPORT_CHANGE_IN_BEHAVIOR_1);

        verify(petReportRepositoryMock).save(argThat(x -> {
            assertEquals(PET_REPORT_ID_2, x.getId());
            assertNotEquals(PET_REPORT_CHANGE_IN_BEHAVIOR_2, x.getChangeInBehavior());
            assertEquals(PET_REPORT_CHANGE_IN_BEHAVIOR_1, x.getChangeInBehavior());
            return true;
        }));

        assertThrows(PetReportNotFoundException.class, () -> out.setChangeInBehavior(PET_REPORT_ID_1, PET_REPORT_CHANGE_IN_BEHAVIOR_2));
    }

    @Test
    void testSetTimeSendingReport() {
        when(petReportRepositoryMock.findById(eq(PET_REPORT_ID_2))).thenReturn(Optional.of(PET_REPORT_2));

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(PET_REPORT_TIME_SENDING_REPORT_2);
            out.setTimeSendingReport(PET_REPORT_ID_2);
        }

        verify(petReportRepositoryMock).save(argThat(x -> {
            assertEquals(PET_REPORT_ID_2, x.getId());
            assertEquals(PET_REPORT_TIME_SENDING_REPORT_2, x.getTimeSendingReport());
            return true;
        }));

        assertThrows(PetReportNotFoundException.class, () -> out.setTimeSendingReport(PET_REPORT_ID_1));
    }

    @Test
    void testSetReportState() {
        when(petReportRepositoryMock.findById(eq(PET_REPORT_ID_2))).thenReturn(Optional.of(PET_REPORT_2));
        out.setReportState(PET_REPORT_ID_2, PetReportState.valueOf(PET_REPORT_STATE_1));

        verify(petReportRepositoryMock).save(argThat(x -> {
            assertEquals(PET_REPORT_ID_2, x.getId());
            assertNotEquals(PET_REPORT_STATE_2, x.getState());
            assertEquals(PET_REPORT_STATE_1, x.getState());
            return true;
        }));

        assertThrows(PetReportNotFoundException.class, () -> out.setReportState(PET_REPORT_ID_1, PetReportState.valueOf(PET_REPORT_STATE_2)));
    }

    @Test
    void testGetUnverifiedReports() {
        when(petReportRepositoryMock.findAllByState(eq(PetReportState.WAITING_FOR_VERIFICATION.name()))).thenReturn(UNVERIFIED_REPORTS_LIST);
        when(petReportMapperMock.toDto(PET_REPORT_1)).thenReturn(PET_REPORT_DTO_OUT_1);
        when(petReportMapperMock.toDto(PET_REPORT_2)).thenReturn(PET_REPORT_DTO_OUT_2);
        when(petReportMapperMock.toDto(PET_REPORT_3)).thenReturn(PET_REPORT_DTO_OUT_3);

        assertIterableEquals(UNVERIFIED_REPORTS_DTO_OUT_LIST, out.getUnverifiedReports());
    }

}
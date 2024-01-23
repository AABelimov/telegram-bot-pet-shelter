package pro.sky.telegrambot.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.exception.UserNotFoundException;
import pro.sky.telegrambot.exception.VolunteerNotFoundException;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.service.PetService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static pro.sky.telegrambot.constants.ProbationConstants.*;
import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

@ExtendWith(MockitoExtension.class)
class ProbationMapperTest {

    @Mock
    private UserService userService;
    @Mock
    private PetService petService;
    @Mock
    private VolunteerService volunteerService;

    @Spy
    private UserMapper userMapper;
    @Spy
    private PetMapper petMapper;
    @Spy
    private VolunteerMapper volunteerMapper;

    @InjectMocks
    private ProbationMapper out;

    @Test
    void shouldReturnProbationDtoOut() {
        assertEquals(PROBATION_DTO_OUT_1, out.toDto(PROBATION_1));
    }

    @Test
    void shouldReturnProbation() {
        Probation acatualProbation;
        when(userService.getUser(eq(USER_ID_1))).thenReturn(USER_1);
        when(petService.getPet(eq(PET_ID_1))).thenReturn(PET_1);
        when(volunteerService.getVolunteer(eq(VOLUNTEER_ID_1))).thenReturn(VOLUNTEER_1);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(LocalDateTime.MIN);
            acatualProbation = out.toEntity(PROBATION_DTO_IN_1);
        }

        assertEquals(PROBATION_1.getUser(), acatualProbation.getUser());
        assertEquals(PROBATION_1.getPet(), acatualProbation.getPet());
        assertEquals(PROBATION_1.getShelterType(), acatualProbation.getShelterType());
        assertEquals(PROBATION_1.getProbationEndDate(), acatualProbation.getProbationEndDate());
        assertEquals(PROBATION_1.getLastReportDate(), acatualProbation.getLastReportDate());
        assertEquals(PROBATION_1.getVolunteer(), acatualProbation.getVolunteer());
        assertEquals(PROBATION_1.getState(), acatualProbation.getState());
    }

    @Test
    void shouldTrowException() {
        assertThrows(UserNotFoundException.class, () -> out.toEntity(PROBATION_DTO_IN_1));
        when(userService.getUser(eq(USER_ID_1))).thenReturn(USER_1);
        assertThrows(VolunteerNotFoundException.class, () -> out.toEntity(PROBATION_DTO_IN_1));
    }
}
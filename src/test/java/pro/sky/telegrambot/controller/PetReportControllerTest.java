package pro.sky.telegrambot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.mapper.*;
import pro.sky.telegrambot.repository.*;
import pro.sky.telegrambot.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.PetReportConstants.*;
import static pro.sky.telegrambot.constants.ProbationConstants.*;

@WebMvcTest(controllers = PetReportController.class)
class PetReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetReportRepository petReportRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PetRepository petRepository;
    @MockBean
    private VolunteerRepository volunteerRepository;
    @MockBean
    private ProbationRepository probationRepository;
    @MockBean
    private OverdueReportRepository overdueReportRepository;
    @MockBean
    private TelegramBotService telegramBotService;

    @SpyBean
    private PetReportService petReportService;
    @SpyBean
    private UserService userService;
    @SpyBean
    private PetService petService;
    @SpyBean
    private VolunteerService volunteerService;
    @SpyBean
    private PetReportMapper petReportMapper;
    @SpyBean
    private UserMapper userMapper;
    @SpyBean
    private PetMapper petMapper;
    @SpyBean
    private VolunteerMapper volunteerMapper;
    @SpyBean
    private ProbationMapper probationMapper;
    @SpyBean
    private ProbationService probationService;
    @SpyBean
    private OverdueReportService overdueReportService;

    @Autowired
    private ObjectMapper objectMapper;

    /*@Test
    void testGetUnverifiedReports() throws Exception {
        when(petReportRepository.findAllByState(eq(PetReportState.WAITING_FOR_VERIFICATION.name()))).thenReturn(UNVERIFIED_REPORTS_LIST);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reports/unverified")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertEquals(objectMapper.writeValueAsString(UNVERIFIED_REPORTS_DTO_OUT_LIST),
                            result.getResponse().getContentAsString());
                });
    }*/

    @Test
    void testGetReport() throws Exception {
        when(petReportRepository.findById(eq(PET_REPORT_ID_1))).thenReturn(Optional.of(PET_REPORT_1));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reports/" + PET_REPORT_ID_1)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PET_REPORT_ID_1))
                .andExpect(jsonPath("$.user").value(PET_REPORT_USER_1))
                .andExpect(jsonPath("$.pet").value(PET_REPORT_PET_1))
                .andExpect(jsonPath("$.volunteer").value(PET_REPORT_VOLUNTEER_1))
                .andExpect(jsonPath("$.timeSendingReport").value(PET_REPORT_TIME_SENDING_REPORT_1.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$.diet").value(PET_REPORT_DIET_1))
                .andExpect(jsonPath("$.wellBeing").value(PET_REPORT_WELL_BEING_1))
                .andExpect(jsonPath("$.changeInBehavior").value(PET_REPORT_CHANGE_IN_BEHAVIOR_1));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reports/" + PET_REPORT_ID_2)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertNotNull(responseString);
                    assertEquals("Report with id = " + PET_REPORT_ID_2 + " not found!", responseString);
                });
    }

    @Test
    void testAcceptReport() throws Exception {
        when(petReportRepository.findById(eq(PET_REPORT_ID_1))).thenReturn(Optional.of(PET_REPORT_1));
        when(probationRepository.findByPetId(eq(PET_ID_1))).thenReturn(PROBATION_1);
        when(probationRepository.findById(eq(PROBATION_ID_1))).thenReturn(Optional.of(PROBATION_1));

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(PET_REPORT_TIME_SENDING_REPORT_1);

            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .patch("/reports/" + PET_REPORT_ID_1 + "/accept")
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(PET_REPORT_ID_1))
                    .andExpect(jsonPath("$.user").value(PET_REPORT_USER_1))
                    .andExpect(jsonPath("$.pet").value(PET_REPORT_PET_1))
                    .andExpect(jsonPath("$.volunteer").value(PET_REPORT_VOLUNTEER_1))
                    .andExpect(jsonPath("$.timeSendingReport").value(PET_REPORT_TIME_SENDING_REPORT_1.format(DateTimeFormatter.ISO_DATE_TIME)))
                    .andExpect(jsonPath("$.diet").value(PET_REPORT_DIET_1))
                    .andExpect(jsonPath("$.wellBeing").value(PET_REPORT_WELL_BEING_1))
                    .andExpect(jsonPath("$.changeInBehavior").value(PET_REPORT_CHANGE_IN_BEHAVIOR_1));
        }

        verify(probationService, times(1)).setProbationState(eq(PROBATION_ID_1), eq(ProbationState.REPORT_ACCEPTED));
        verify(probationService, times(1)).setLastReportDate(eq(PROBATION_ID_1));
        verify(overdueReportService, times(1)).deleteOverdueReport(eq(PROBATION_1));


        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/reports/" + PET_REPORT_ID_2 + "/accept")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertNotNull(responseString);
                    assertEquals("Report with id = " + PET_REPORT_ID_2 + " not found!", responseString);
                });
    }

    @Test
    void testDenyReport() throws Exception {
        String comment = "reason";
        String textMessage = PROBATION_USER_1.getName() + ", ваш отчет по животному с кличкой " + PROBATION_PET_1.getName() +
                " не был принят по причине:\n" + comment;

        when(probationRepository.findByPetId(eq(PET_ID_1))).thenReturn(PROBATION_1);
        when(probationRepository.findById(eq(PROBATION_ID_1))).thenReturn(Optional.of(PROBATION_1));
        when(petReportRepository.findById(eq(PET_REPORT_ID_1))).thenReturn(Optional.of(PET_REPORT_1));

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(LocalDateTime.MIN);

            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .patch("/reports/" + PET_REPORT_ID_1 + "/deny?comment=" + comment)
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(PET_REPORT_ID_1))
                    .andExpect(jsonPath("$.user").value(PET_REPORT_USER_1))
                    .andExpect(jsonPath("$.pet").value(PET_REPORT_PET_1))
                    .andExpect(jsonPath("$.volunteer").value(PET_REPORT_VOLUNTEER_1))
                    .andExpect(jsonPath("$.timeSendingReport").value(PET_REPORT_TIME_SENDING_REPORT_1.format(DateTimeFormatter.ISO_DATE_TIME)))
                    .andExpect(jsonPath("$.diet").value(PET_REPORT_DIET_1))
                    .andExpect(jsonPath("$.wellBeing").value(PET_REPORT_WELL_BEING_1))
                    .andExpect(jsonPath("$.changeInBehavior").value(PET_REPORT_CHANGE_IN_BEHAVIOR_1));
        }

        verify(probationService, times(1)).setProbationState(eq(PROBATION_ID_1), eq(ProbationState.REPORT_DENIED));
        verify(telegramBotService, times(1)).sendMessage(eq(PROBATION_1.getUser().getId()), eq(textMessage));


        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/reports/" + PET_REPORT_ID_2 + "/deny?comment=comment")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertNotNull(responseString);
                    assertEquals("Report with id = " + PET_REPORT_ID_2 + " not found!", responseString);
                });
    }
}
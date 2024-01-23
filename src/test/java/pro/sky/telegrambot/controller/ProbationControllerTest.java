package pro.sky.telegrambot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.telegrambot.mapper.PetMapper;
import pro.sky.telegrambot.mapper.ProbationMapper;
import pro.sky.telegrambot.mapper.UserMapper;
import pro.sky.telegrambot.mapper.VolunteerMapper;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.repository.PetRepository;
import pro.sky.telegrambot.repository.ProbationRepository;
import pro.sky.telegrambot.repository.UserRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;
import pro.sky.telegrambot.service.PetService;
import pro.sky.telegrambot.service.ProbationService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.ProbationConstants.*;
import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;

@WebMvcTest(controllers = ProbationController.class)
class ProbationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProbationRepository probationRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PetRepository petRepository;
    @MockBean
    private VolunteerRepository volunteerRepository;

    @SpyBean
    private ProbationService probationService;
    @SpyBean
    private ProbationMapper probationMapper;
    @SpyBean
    private UserMapper userMapper;
    @SpyBean
    private PetMapper petMapper;
    @SpyBean
    private VolunteerMapper volunteerMapper;
    @SpyBean
    private PetService petService;
    @SpyBean
    private UserService userService;
    @SpyBean
    private VolunteerService volunteerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProbation() throws Exception {
        Probation probation = PROBATION_1;
        probation.setId(null);

        when(probationRepository.findByPetId(eq(PET_ID_1))).thenReturn(null);
        when(probationRepository.save(eq(probation))).thenReturn(PROBATION_1);
        when(petRepository.findById(PET_ID_1)).thenReturn(Optional.of(PET_1));
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(USER_1));
        when(volunteerRepository.findById(VOLUNTEER_ID_1)).thenReturn(Optional.of(VOLUNTEER_1));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/probation")
                        .content(objectMapper.writeValueAsString(PROBATION_DTO_IN_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user").value(USER_1))
                .andExpect(jsonPath("$.pet").value(PET_1))
                .andExpect(jsonPath("$.probationEndDate").value(PROBATION_END_DATE_1.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$.lastReportDate").value(PROBATION_LAST_REPORT_DATE_1.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$.volunteer").value(VOLUNTEER_1));


        when(probationRepository.findByPetId(eq(PET_ID_2))).thenReturn(PROBATION_2);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/probation")
                                .content(objectMapper.writeValueAsString(PROBATION_DTO_IN_2))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertNotNull(responseString);
                    assertEquals("Pet with id = " + PET_ID_2 + " is already on probation", responseString);
                });


        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/probation")
                                .content(objectMapper.writeValueAsString(NOT_FOUNT_PET_ID_PROBATION_DTO_IN))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertNotNull(responseString);
                    assertEquals("Pet with id = " + NOT_FOUND_PET_ID + " not found!", responseString);
                });


        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/probation")
                                .content(objectMapper.writeValueAsString(NOT_FOUNT_USER_ID_PROBATION_DTO_IN))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertNotNull(responseString);
                    assertEquals("User with id = " + NOT_FOUND_USER_ID + " not found!", responseString);
                });


        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/probation")
                                .content(objectMapper.writeValueAsString(NOT_FOUNT_VOLUNTEER_ID_PROBATION_DTO_IN))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertNotNull(responseString);
                    assertEquals("Volunteer with id = " + NOT_FOUND_VOLUNTEER_ID + " not found!", responseString);
                });
    }

}
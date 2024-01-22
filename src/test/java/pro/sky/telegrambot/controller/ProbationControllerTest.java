package pro.sky.telegrambot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        when(probationRepository.findByPetId(eq(PET_ID_1))).thenReturn(null);
        when(probationRepository.save(any(Probation.class))).thenReturn(PROBATION_1); //TODO: any to eq
        when(petRepository.findById(PET_ID_1)).thenReturn(Optional.of(PET_1));
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(USER_1));
        when(volunteerRepository.findById(VOLUNTEER_ID_1)).thenReturn(Optional.of(VOLUNTEER_1));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/probation")
                        .content(objectMapper.writeValueAsString(PROBATION_DTO_IN_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").value("FACULTY_ID_1"));
    }

}
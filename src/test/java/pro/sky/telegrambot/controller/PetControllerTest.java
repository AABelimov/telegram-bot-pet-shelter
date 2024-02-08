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
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.repository.PetRepository;
import pro.sky.telegrambot.service.PetService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.telegrambot.constants.PetConstants.*;

@WebMvcTest(controllers = PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetRepository petRepository;

    @SpyBean
    private PetService petService;
    @SpyBean
    private PetMapper petMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreatePet() throws Exception {
        Pet pet = new Pet(PET_1);
        pet.setId(null);

        when(petRepository.save(eq(pet))).thenReturn(PET_1);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/pets")
                        .content(objectMapper.writeValueAsString(PET_DTO_IN_1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PET_ID_1))
                .andExpect(jsonPath("$.kindOfPet").value(KIND_OF_PET_1))
                .andExpect(jsonPath("$.name").value(PET_NAME_1));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/pets")
                                .content(objectMapper.writeValueAsString(PET_DTO_IN_INVALID))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseString = result.getResponse().getContentAsString();
                    assertNotNull(responseString);
                    assertEquals("We don't keep " + KIND_OF_PET_INVALID + " in our shelters", responseString);
                });
    }

}
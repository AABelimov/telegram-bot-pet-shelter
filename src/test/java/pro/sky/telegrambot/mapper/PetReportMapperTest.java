package pro.sky.telegrambot.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.dto.PetReportDtoOut;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.PetConstants.*;
import static pro.sky.telegrambot.constants.VolunteerConstants.*;
import static pro.sky.telegrambot.constants.PetReportConstants.*;

@ExtendWith(MockitoExtension.class)
class PetReportMapperTest {

    @Mock
    private UserMapper userMapperMock;
    @Mock
    private PetMapper petMapperMock;
    @Mock
    private VolunteerMapper volunteerMapperMock;
    @InjectMocks
    private PetReportMapper out;

    @Test
    void testToDto() {
        PetReportDtoOut actualPetReportDtoOut;

        when(userMapperMock.toDto(eq(USER_1))).thenReturn(USER_DTO_OUT_1);
        when(petMapperMock.toDto(eq(PET_1))).thenReturn(PET_DTO_OUT_1);
        when(volunteerMapperMock.toDto(eq(VOLUNTEER_1))).thenReturn(VOLUNTEER_DTO_OUT_1);

        actualPetReportDtoOut = out.toDto(PET_REPORT_1);

        assertEquals(PET_REPORT_DTO_OUT_1.getId(), actualPetReportDtoOut.getId());
        assertEquals(PET_REPORT_DTO_OUT_1.getUser(), actualPetReportDtoOut.getUser());
        assertEquals(PET_REPORT_DTO_OUT_1.getPet(), actualPetReportDtoOut.getPet());
        assertEquals(PET_REPORT_DTO_OUT_1.getVolunteer(), actualPetReportDtoOut.getVolunteer());
        assertEquals(PET_REPORT_DTO_OUT_1.getTimeSendingReport(), actualPetReportDtoOut.getTimeSendingReport());
        assertEquals(PET_REPORT_DTO_OUT_1.getDiet(), actualPetReportDtoOut.getDiet());
        assertEquals(PET_REPORT_DTO_OUT_1.getWellBeing(), actualPetReportDtoOut.getWellBeing());
        assertEquals(PET_REPORT_DTO_OUT_1.getChangeInBehavior(), actualPetReportDtoOut.getChangeInBehavior());
    }

}
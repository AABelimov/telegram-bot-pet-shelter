package pro.sky.telegrambot.mapper;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.dto.PetReportDtoOut;
import pro.sky.telegrambot.model.PetReport;

@Component
public class PetReportMapper {

    private final UserMapper userMapper;
    private final PetMapper petMapper;
    private final VolunteerMapper volunteerMapper;

    public PetReportMapper(UserMapper userMapper,
                           PetMapper petMapper,
                           VolunteerMapper volunteerMapper) {
        this.userMapper = userMapper;
        this.petMapper = petMapper;
        this.volunteerMapper = volunteerMapper;
    }

    public PetReportDtoOut toDto(PetReport petReport) {
        PetReportDtoOut petReportDtoOut = new PetReportDtoOut();

        petReportDtoOut.setId(petReport.getId());
        petReportDtoOut.setPhotoPath("http://localhost:8080/reports/" + petReport.getId() + "/photo");
        petReportDtoOut.setUser(userMapper.toDto(petReport.getUser()));
        petReportDtoOut.setPet(petMapper.toDto(petReport.getPet()));
        petReportDtoOut.setVolunteer(volunteerMapper.toDto(petReport.getVolunteer()));
        petReportDtoOut.setTimeSendingReport(petReport.getTimeSendingReport());
        petReportDtoOut.setDiet(petReport.getDiet());
        petReportDtoOut.setWellBeing(petReport.getWellBeing());
        petReportDtoOut.setChangeInBehavior(petReport.getChangeInBehavior());

        return petReportDtoOut;
    }
}

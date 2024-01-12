package pro.sky.telegrambot.mapper;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.dto.VolunteerDtoOut;
import pro.sky.telegrambot.model.Volunteer;

@Component
public class VolunteerMapper {

    public VolunteerDtoOut toDto(Volunteer volunteer) {
        VolunteerDtoOut volunteerDtoOut = new VolunteerDtoOut();

        volunteerDtoOut.setId(volunteer.getId());
        volunteerDtoOut.setName(volunteer.getName());

        return volunteerDtoOut;
    }
}

package pro.sky.telegrambot.mapper;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.dto.ProbationDtoOut;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.exception.UserNotFoundException;
import pro.sky.telegrambot.exception.VolunteerNotFoundException;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.PetService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

import java.time.LocalDateTime;

@Component
public class ProbationMapper {

    private final UserService userService;
    private final PetService petService;
    private final VolunteerService volunteerService;
    private final UserMapper userMapper;
    private final PetMapper petMapper;
    private final VolunteerMapper volunteerMapper;

    public ProbationMapper(UserService userService,
                           PetService petService,
                           VolunteerService volunteerService,
                           UserMapper userMapper,
                           PetMapper petMapper,
                           VolunteerMapper volunteerMapper) {
        this.userService = userService;
        this.petService = petService;
        this.volunteerService = volunteerService;
        this.userMapper = userMapper;
        this.petMapper = petMapper;
        this.volunteerMapper = volunteerMapper;
    }

    @Transactional
    public Probation toEntity(ProbationDtoIn probationDtoIn) {
        Probation probation = new Probation();
        User user = userService.getUser(probationDtoIn.getUserId());
        Pet pet = petService.getPet(probationDtoIn.getPetId());
        Volunteer volunteer = volunteerService.getVolunteer(probationDtoIn.getVolunteerId());
        ShelterType shelterType;
        String kindOfPet;

        if (user == null) {
            throw new UserNotFoundException(probationDtoIn.getUserId());
        }
        if (volunteer == null) {
            throw new VolunteerNotFoundException(probationDtoIn.getVolunteerId());
        }

        kindOfPet = pet.getKindOfPet();
        shelterType = kindOfPet.equals("CAT") ? ShelterType.CAT_SHELTER : ShelterType.DOG_SHELTER;

        probation.setUser(user);
        probation.setPet(pet);
        probation.setShelterType(shelterType.name());
        probation.setVolunteer(volunteer);
        probation.setProbationEndDate(LocalDateTime.now().plusDays(30));
        probation.setLastReportDate(LocalDateTime.now());
        probation.setState(ProbationState.NEW_PROBATION.name());

        return probation;
    }

    public ProbationDtoOut toDto(Probation probation) {
        ProbationDtoOut probationDtoOut = new ProbationDtoOut();

        probationDtoOut.setUser(userMapper.toDto(probation.getUser()));
        probationDtoOut.setPet(petMapper.toDto(probation.getPet()));
        probationDtoOut.setProbationEndDate(probation.getProbationEndDate());
        probationDtoOut.setLastReportDate(probation.getLastReportDate());
        probationDtoOut.setVolunteer(volunteerMapper.toDto(probation.getVolunteer()));

        return probationDtoOut;
    }
}

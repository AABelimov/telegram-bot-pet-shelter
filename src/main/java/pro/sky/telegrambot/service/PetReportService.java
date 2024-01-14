package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.time.LocalDateTime;

@Service
public class PetReportService {

    private final PetReportRepository petReportRepository;
    private final UserService userService;
    private final TelegramBotService telegramBotService;

    public PetReportService(PetReportRepository petReportRepository,
                            UserService userService,
                            TelegramBotService telegramBotService) {
        this.petReportRepository = petReportRepository;
        this.userService = userService;
        this.telegramBotService = telegramBotService;
    }

    public PetReport getReport(Long id) {
        return petReportRepository.findById(id).orElseThrow(); // TODO: todo
    }

    public PetReport getReportByPetIdAndState(Long petId, PetReportState state) {
        return petReportRepository.findByPetIdAndState(petId, state.name());
    }

    public PetReport createPetReport(Pet pet, User user, Volunteer volunteer, ShelterType shelterType) {
        PetReport petReport = new PetReport();

        petReport.setPet(pet);
        petReport.setShelterType(shelterType.name());
        petReport.setUser(user);
        petReport.setVolunteer(volunteer);
        petReport.setState(PetReportState.FILLING.name());

        return petReportRepository.save(petReport);
    }

    public void fillReport(Long userId, PetReport petReport) {
        if (petReport.getPhotoPath() == null) {
            userService.setUserState(userId, UserState.FILL_OUT_THE_REPORT_PHOTO);
            telegramBotService.sendMessage(userId, "Отправте фото животного");
        }
    }

    public PetReport getReportByUserIdAndShelterTypeAndState(Long userId, ShelterType shelterType, PetReportState state) {
        return petReportRepository.findByUserIdAndShelterTypeAndState(userId, shelterType.name(), state.name());
    }

    public void setPhotoPath(Long id, String photoPath) {
        PetReport petReport = getReport(id);
        petReport.setPhotoPath(photoPath);
        petReportRepository.save(petReport);
    }

    public void setDiet(Long id, String text) {
        PetReport petReport = getReport(id);
        petReport.setDiet(text);
        petReportRepository.save(petReport);
    }

    public PetReport getReportByUserIdAndState(Long userId, PetReportState petReportState) {
        User user = userService.getUser(userId);
        ShelterType shelterType = ShelterType.valueOf(user.getSelectedShelter());
        return getReportByUserIdAndShelterTypeAndState(userId, shelterType, PetReportState.FILLING);
    }

    public void setWellBeing(Long id, String text) {
        PetReport petReport = getReport(id);
        petReport.setWellBeing(text);
        petReportRepository.save(petReport);
    }

    public void setChangeInBehavior(Long id, String text) {
        PetReport petReport = getReport(id);
        petReport.setChangeInBehavior(text);
        petReportRepository.save(petReport);
    }

    public void setTimeSendingReport(Long id) {
        PetReport petReport = getReport(id);
        petReport.setTimeSendingReport(LocalDateTime.now());
        petReportRepository.save(petReport);
    }

    public void setReportState(Long id, PetReportState state) {
        PetReport petReport = getReport(id);
        petReport.setState(state.name());
        petReportRepository.save(petReport);
    }

    public PetReport getReportByVolunteerIdAndState(Long volunteerId, PetReportState state) {
        return petReportRepository.findFirstByVolunteerIdAndState(volunteerId, state.name());
    }
}

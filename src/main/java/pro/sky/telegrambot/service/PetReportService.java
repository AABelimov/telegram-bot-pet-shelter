package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.dto.PetReportDtoOut;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.exception.PetReportNotFoundException;
import pro.sky.telegrambot.mapper.PetReportMapper;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetReportService {

    private final PetReportRepository petReportRepository;
    private final UserService userService;
    private final TelegramBotService telegramBotService;
    private final PetReportMapper petReportMapper;
    private final ProbationService probationService;
    private final OverdueReportService overdueReportService;

    public PetReportService(PetReportRepository petReportRepository,
                            UserService userService,
                            TelegramBotService telegramBotService,
                            PetReportMapper petReportMapper,
                            ProbationService probationService,
                            OverdueReportService overdueReportService) {
        this.petReportRepository = petReportRepository;
        this.userService = userService;
        this.telegramBotService = telegramBotService;
        this.petReportMapper = petReportMapper;
        this.probationService = probationService;
        this.overdueReportService = overdueReportService;
    }

    public PetReport getReport(Long id) {
        return petReportRepository.findById(id).orElseThrow(() -> new PetReportNotFoundException(id));
    }

    public PetReportDtoOut getReportDto(Long id) {
        return petReportMapper.toDto(getReport(id));
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

    @Transactional
    public void setPhotoPath(Long id, String photoPath) {
        PetReport petReport = getReport(id);
        petReport.setPhotoPath(photoPath);
        petReportRepository.save(petReport);
    }

    @Transactional
    public void setDiet(Long id, String text) {
        PetReport petReport = getReport(id);
        petReport.setDiet(text);
        petReportRepository.save(petReport);
    }

    @Transactional
    public PetReport getReportByUserIdAndState(Long userId) {
        User user = userService.getUser(userId);
        ShelterType shelterType = ShelterType.valueOf(user.getSelectedShelter());
        return getReportByUserIdAndShelterTypeAndState(userId, shelterType, PetReportState.FILLING);
    }

    public List<PetReport> getPetReportsByState(PetReportState state) {
        return petReportRepository.findAllByState(state.name());
    }

    @Transactional
    public void setWellBeing(Long id, String text) {
        PetReport petReport = getReport(id);
        petReport.setWellBeing(text);
        petReportRepository.save(petReport);
    }

    @Transactional
    public void setChangeInBehavior(Long id, String text) {
        PetReport petReport = getReport(id);
        petReport.setChangeInBehavior(text);
        petReportRepository.save(petReport);
    }

    @Transactional
    public void setTimeSendingReport(Long id) {
        PetReport petReport = getReport(id);
        petReport.setTimeSendingReport(LocalDateTime.now());
        petReportRepository.save(petReport);
    }

    @Transactional
    public void setReportState(Long id, PetReportState state) {
        PetReport petReport = getReport(id);
        petReport.setState(state.name());
        petReportRepository.save(petReport);
    }

    public PetReport getReportByVolunteerIdAndState(Long volunteerId, PetReportState state) {
        return petReportRepository.findFirstByVolunteerIdAndState(volunteerId, state.name());
    }

    public List<PetReportDtoOut> getUnverifiedReports() {
        List<PetReport> petReports = getPetReportsByState(PetReportState.WAITING_FOR_VERIFICATION);
        return petReports.stream()
                .map(petReportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PetReportDtoOut acceptReport(Long id) {
        PetReport petReport = getReport(id);
        Probation probation = probationService.getProbationByPetId(petReport.getPet().getId());
        setReportState(id, PetReportState.ACCEPTED);
        setTimeSendingReport(id);
        probationService.setProbationState(probation.getId(), ProbationState.REPORT_ACCEPTED);
        probationService.setLastReportDate(probation.getId());
        overdueReportService.deleteOverdueReport(probation);
        return petReportMapper.toDto(petReport);
    }

    @Transactional
    public PetReportDtoOut denyReport(Long id, String comment) {
        PetReport petReport = getReport(id);
        Probation probation = probationService.getProbationByPetId(petReport.getPet().getId());
        comment = String.format("%s, ваш отчет по животному с кличкой %s не был принят по причине:\n%s",
                probation.getUser().getName(), petReport.getPet().getName(), comment);

        probationService.setProbationState(probation.getId(), ProbationState.REPORT_DENIED);
        setReportState(id, PetReportState.DENIED);

        telegramBotService.sendMessage(probation.getUser().getId(), comment);

        return petReportMapper.toDto(petReport);
    }

    public List<PetReport> getReportsByVolunteerIdAndState(Long volunteerId, PetReportState state) {
        return petReportRepository.findAllByVolunteerIdAndState(volunteerId, state.name());
    }
}

package pro.sky.telegrambot.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.dto.PetReportDtoOut;
import pro.sky.telegrambot.enums.*;
import pro.sky.telegrambot.exception.PetReportNotFoundException;
import pro.sky.telegrambot.mapper.PetReportMapper;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public PetReport createReport(Pet pet, User user, Volunteer volunteer, String shelterType) {
        PetReport petReport = new PetReport();

        petReport.setPet(pet);
        petReport.setShelterType(shelterType);
        petReport.setUser(user);
        petReport.setVolunteer(volunteer);
        petReport.setState(PetReportState.FILLING.name());

        return petReportRepository.save(petReport);
    }

    public PetReport getReport(Long id) {
        return petReportRepository.findById(id).orElseThrow(() -> new PetReportNotFoundException(id));
    }

    public PetReport getReport(Long petId, PetReportState state) {
        return petReportRepository.findByPetIdAndState(petId, state.name());
    }

    public PetReport getReport(Long userId, ShelterType shelterType, PetReportState state) {
        return petReportRepository.findByUserIdAndShelterTypeAndState(userId, shelterType.name(), state.name());
    }

    public PetReport getReportByVolunteerIdAndState(Long volunteerId, PetReportState state) {
        return petReportRepository.findFirstByVolunteerIdAndState(volunteerId, state.name());
    }

    public PetReportDtoOut getReportDto(Long id) {
        return petReportMapper.toDto(getReport(id));
    }

    public List<PetReport> getReports(PetReportState state, Integer page) {
        return petReportRepository.findAllByState(state.name(), PageRequest.of(page, 10));
    }

    public List<PetReport> getReports(Long volunteerId, PetReportState state) {
        return petReportRepository.findAllByVolunteerIdAndState(volunteerId, state.name());
    }

    public List<PetReportDtoOut> getReports(String shelterType, String state, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        PetReportState petReportState;
        ShelterType typeOfShelter;

        if (shelterType.equals("all") && state == null) {
            return getAllReports(pageRequest);
        } else if (shelterType.equals("all")) {
            petReportState = PetReportState.valueOf(state.toUpperCase());
            return getReports(petReportState, page).stream()
                    .map(petReportMapper::toDto)
                    .collect(Collectors.toList());
        }
        typeOfShelter = ShelterType.valueOf(shelterType.toUpperCase());
        return getReportsByShelterTypeAndState(typeOfShelter, state, pageRequest);
    }

    private List<PetReportDtoOut> getAllReports(PageRequest pageRequest) {
        return petReportRepository.findAll(pageRequest).getContent().stream()
                .map(petReportMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<PetReportDtoOut> getReportsByShelterTypeAndState(ShelterType shelterType, String state, PageRequest pageRequest) {
        PetReportState petReportState;
        List<PetReport> petReports;

        if (state == null) {
            petReports = petReportRepository.findAllByShelterType(shelterType.name(), pageRequest);
        } else {
            petReportState = PetReportState.valueOf(state.toUpperCase());
            petReports = petReportRepository.findAllByShelterTypeAndState(shelterType.name(), petReportState.name(), pageRequest);
        }
        return petReports.stream()
                .map(petReportMapper::toDto)
                .collect(Collectors.toList());
    }

    public byte[] getPhoto(Long id) throws IOException {
        PetReport petReport = getReport(id);
        return Files.readAllBytes(Path.of(petReport.getPhotoPath()));
    }

    public void startFillingOutTheReport(Long userId, PetReport petReport) {
        if (petReport.getPhotoPath() == null) {
            userService.setUserState(userId, UserState.FILL_OUT_THE_REPORT_PHOTO);
            telegramBotService.sendMessage(userId, "Отправьте фото животного");
        }
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

    public void setWellBeing(Long id, String text) {
        PetReport petReport = getReport(id);
        petReport.setWellBeing(text);
        petReportRepository.save(petReport);
    }

    public void setChangeInBehavior(Long id, String text) {
        PetReport petReport = getReport(id);
        petReport.setChangeInBehavior(text);
        petReport.setTimeSendingReport(LocalDateTime.now());
        petReportRepository.save(petReport);
    }

    public void setReportState(Long id, PetReportState state) {
        PetReport petReport = getReport(id);
        petReport.setState(state.name());
        petReportRepository.save(petReport);
    }

    @Transactional
    public void acceptReport(Long id) {
        PetReport petReport = getReport(id);
        Probation probation = probationService.getProbationByPetId(petReport.getPet().getId());
        setReportState(id, PetReportState.ACCEPTED);
        probationService.setProbationState(probation.getId(), ProbationState.REPORT_ACCEPTED);
        probationService.setLastReportDate(probation.getId());
        overdueReportService.deleteOverdueReport(probation);
    }

    @Transactional
    public void denyReport(Long id, String comment) {
        PetReport petReport = getReport(id);
        Probation probation = probationService.getProbationByPetId(petReport.getPet().getId());
        comment = String.format("%s, ваш отчет по животному с кличкой %s не был принят по причине:\n%s",
                probation.getUser().getName(), petReport.getPet().getName(), comment);

        probationService.setProbationState(probation.getId(), ProbationState.REPORT_DENIED);
        setReportState(id, PetReportState.DENIED);

        telegramBotService.sendMessage(probation.getUser().getId(), comment);
    }
}

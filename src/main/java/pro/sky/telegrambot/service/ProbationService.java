package pro.sky.telegrambot.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.dto.ProbationDtoOut;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.exception.PetIsAlreadyOnProbationException;
import pro.sky.telegrambot.exception.ProbationNotFoundException;
import pro.sky.telegrambot.mapper.ProbationMapper;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.repository.ProbationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbationService {

    private final ProbationRepository probationRepository;
    private final ProbationMapper probationMapper;
    private final PetService petService;
    private final TelegramBotService telegramBotService;

    public ProbationService(ProbationRepository probationRepository,
                            ProbationMapper probationMapper,
                            PetService petService,
                            TelegramBotService telegramBotService) {
        this.probationRepository = probationRepository;
        this.probationMapper = probationMapper;
        this.petService = petService;
        this.telegramBotService = telegramBotService;
    }

    @Transactional
    public void createProbation(ProbationDtoIn probationDtoIn) {
        Probation probation = getProbationByPetId(probationDtoIn.getPetId());

        if (probation != null) {
            throw new PetIsAlreadyOnProbationException(probationDtoIn.getPetId());
        }

        petService.setPetState(probationDtoIn.getPetId(), PetState.ON_PROBATION);
        probationMapper.toDto(probationRepository.save(probationMapper.toEntity(probationDtoIn)));
    }

    public Probation getProbation(Long id) {
        return probationRepository.findById(id).orElseThrow(() -> new ProbationNotFoundException(id));
    }

    public ProbationDtoOut getProbationDto(Long id) {
        return probationMapper.toDto(getProbation(id));
    }

    public Probation getProbationByPetId(Long petId) {
        return probationRepository.findByPetId(petId);
    }

    public Probation getProbationByVolunteerIdAndState(Long volunteerId, ProbationState state) {
        return probationRepository.findFirstByVolunteerIdAndState(volunteerId, state.name());
    }

    public Probation getProbationByUserIdAndShelterTypeAndState(Long userId, String shelterType, ProbationState state) {
        List<Probation> probationList = getProbationListByShelterTypeAndState(userId, shelterType, state);
        if (probationList.size() == 0) {
            return null;
        } else {
            return probationList.get(0);
        }
    }

    public List<Probation> getProbationListByShelterTypeAndState(Long userId, String shelterType, ProbationState state) {
        return probationRepository.findAllByUserIdAndShelterTypeAndState(userId, shelterType, state.name());
    }

    public List<Probation> getProbationListByUserIdAndShelterType(Long userId, String shelterType) {
        return probationRepository.findAllByUserIdAndShelterType(userId, shelterType);
    }

    public List<Probation> getProbationListByVolunteerIdAndState(Long volunteerId, ProbationState state) {
        return probationRepository.findAllByVolunteerIdAndState(volunteerId, state.name());
    }

    public List<ProbationDtoOut> getProbationListByState(ProbationState probationState, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        List<Probation> probationList = probationRepository.findAllByState(probationState.name(), pageRequest);
        return probationList.stream()
                .map(probationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProbationDtoOut> getAll(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return probationRepository.findAll(pageRequest).getContent().stream()
                .map(probationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<Probation> getAll() {
        return probationRepository.findAll();
    }

    public void setProbationState(Long id, ProbationState state) {
        Probation probation = getProbation(id);
        probation.setState(state.name());
        probationRepository.save(probation);
    }

    public void setLastReportDate(Long id) {
        Probation probation = getProbation(id);
        probation.setLastReportDate(LocalDateTime.now());
        probationRepository.save(probation);
    }

    public void deleteProbation(Probation probation) {
        probationRepository.delete(probation);
    }

    public void extendProbation(Probation probation, int days) {
        User user = probation.getUser();
        Pet pet = probation.getPet();

        probation.setProbationEndDate(probation.getProbationEndDate().plusDays(days));
        probation.setState(ProbationState.WAITING_FOR_A_NEW_REPORT.name());
        probationRepository.save(probation);
        telegramBotService.sendMessage(user.getId(), String.format("Вам добавили %d дней к испытательному сроку для %s", days, pet.getName()));
    }

    @Transactional
    public void refuseAdoption(Probation probation) {
        User user = probation.getUser();
        Pet pet = probation.getPet();
        String text = String.format("Вы не прошли испытательный срок, %s должен вернуться к нам", pet.getName());

        probationRepository.delete(probation);
        petService.setPetState(pet.getId(), PetState.WAITING_TO_BE_ADOPTED);
        telegramBotService.sendMessage(user.getId(), text);
    }
}

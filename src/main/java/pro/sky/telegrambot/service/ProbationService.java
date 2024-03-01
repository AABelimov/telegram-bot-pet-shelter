package pro.sky.telegrambot.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.dto.ProbationDtoOut;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.exception.PetIsAlreadyOnProbationException;
import pro.sky.telegrambot.exception.ProbationNotFoundException;
import pro.sky.telegrambot.mapper.ProbationMapper;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.repository.ProbationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbationService {

    private final ProbationRepository probationRepository;
    private final ProbationMapper probationMapper;
    private final PetService petService;

    public ProbationService(ProbationRepository probationRepository,
                            ProbationMapper probationMapper,
                            PetService petService) {
        this.probationRepository = probationRepository;
        this.probationMapper = probationMapper;
        this.petService = petService;
    }

    public ProbationDtoOut createProbation(ProbationDtoIn probationDtoIn) {
        Probation probation = getProbationByPetId(probationDtoIn.getPetId());

        if (probation != null) {
            throw new PetIsAlreadyOnProbationException(probationDtoIn.getPetId());
        }

        petService.setPetState(probationDtoIn.getPetId(), PetState.ON_PROBATION);
        return probationMapper.toDto(probationRepository.save(probationMapper.toEntity(probationDtoIn)));
    }

    public Probation getProbation(Long id) {
        return probationRepository.findById(id).orElseThrow(() -> new ProbationNotFoundException(id));
    }

    public List<Probation> getProbationList(Long userId, ShelterType shelterType, ProbationState state) {
        return probationRepository.findByUserIdAndShelterTypeAndState(userId, shelterType.name(), state.name());
    }

    public List<Probation> getProbationList(Long userId, ShelterType shelterType) {
        return probationRepository.findByUserIdAndShelterType(userId, shelterType.name());
    }

    public Probation getProbationByPetId(Long petId) {
        return probationRepository.findByPetId(petId);
    }

    public Probation getProbationByUserIdAndShelterTypeAndState(Long userId, ShelterType shelterType, ProbationState state) {
        List<Probation> probationList = getProbationList(userId, shelterType, state);
        if (probationList.size() == 0) {
            return null;
        } else {
            return probationList.get(0);
        }
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

    public Probation getProbationByVolunteerIdAndState(Long volunteerId, ProbationState state) {
        return probationRepository.findFirstByVolunteerIdAndState(volunteerId, state.name());
    }

    public void deleteProbation(Probation probation) {
        probationRepository.delete(probation);
    }

    public void extendProbation(Long id, int days) {
        Probation probation = getProbation(id);
        probation.setProbationEndDate(probation.getProbationEndDate().plusDays(days));
        probationRepository.save(probation);
    }

    public List<Probation> getAll() {
        return probationRepository.findAll();
    }

    public List<Probation> getProbationListByVolunteerIdAndState(Long volunteerId, ProbationState state) {
        return probationRepository.findAllByVolunteerIdAndState(volunteerId, state.name());
    }

    public List<ProbationDtoOut> getProbationsByState(ProbationState probationState, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        List<Probation> probations = probationRepository.findAllByState(probationState.name(), pageRequest);
        return probations.stream()
                .map(probationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProbationDtoOut getProbationDto(Long id) {
        return probationMapper.toDto(getProbation(id));
    }
}

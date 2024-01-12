package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.dto.ProbationDtoOut;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.mapper.ProbationMapper;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.repository.ProbationRepository;

import java.util.List;

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
        petService.setPetState(probationDtoIn.getPetId(), PetState.ON_PROBATION);
        return probationMapper.toDto(probationRepository.save(probationMapper.toEntity(probationDtoIn)));
    }

    public Probation getProbation(Long id) {
        return probationRepository.findById(id).orElseThrow(); // TODO: todo
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
}

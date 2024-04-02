package pro.sky.telegrambot.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.dto.AdoptionDtoOut;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.mapper.AdoptionMapper;
import pro.sky.telegrambot.model.Adoption;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.repository.AdoptionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final ProbationService probationService;
    private final PetService petService;
    private final TelegramBotService telegramBotService;
    private final AdoptionMapper adoptionMapper;

    public AdoptionService(AdoptionRepository adoptionRepository,
                           ProbationService probationService,
                           PetService petService,
                           TelegramBotService telegramBotService,
                           AdoptionMapper adoptionMapper) {
        this.adoptionRepository = adoptionRepository;
        this.probationService = probationService;
        this.petService = petService;
        this.telegramBotService = telegramBotService;
        this.adoptionMapper = adoptionMapper;
    }

    public void createAdoption(Probation probation) {
        Adoption adoption = new Adoption();
        User user = probation.getUser();
        Pet pet = probation.getPet();
        String text = String.format("Вы прошли испытательный срок, поздравляем!\n%s теперь ваш", pet.getName());

        adoption.setUser(user);
        adoption.setPet(pet);
        adoption.setAdoptionTime(LocalDateTime.now());
        adoptionRepository.save(adoption);

        probationService.deleteProbation(probation);
        petService.setPetState(pet.getId(), PetState.ADOPTED);
        telegramBotService.sendMessage(user.getId(), text);
    }

    public List<AdoptionDtoOut> getAllAdoptions(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return adoptionRepository.findAll(pageRequest).getContent().stream()
                .map(adoptionMapper::toDto)
                .collect(Collectors.toList());
    }
}

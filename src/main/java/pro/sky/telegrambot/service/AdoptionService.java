package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Adoption;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.repository.AdoptionRepository;

import java.time.LocalDateTime;

@Service
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;

    public AdoptionService(AdoptionRepository adoptionRepository) {
        this.adoptionRepository = adoptionRepository;
    }

    public void createAdoption(User user, Pet pet) {
        Adoption adoption = new Adoption();
        adoption.setUser(user);
        adoption.setPet(pet);
        adoption.setAdoptionTime(LocalDateTime.now());
        adoptionRepository.save(adoption);
    }
}

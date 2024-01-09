package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.ProbationRepository;

@Service
public class ProbationService {

    private final ProbationRepository probationRepository;

    public ProbationService(ProbationRepository probationRepository) {
        this.probationRepository = probationRepository;
    }
}

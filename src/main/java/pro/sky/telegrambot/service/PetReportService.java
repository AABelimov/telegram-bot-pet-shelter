package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.PetReportRepository;

@Service
public class PetReportService {

    private final PetReportRepository petReportRepository;

    public PetReportService(PetReportRepository petReportRepository) {
        this.petReportRepository = petReportRepository;
    }
}

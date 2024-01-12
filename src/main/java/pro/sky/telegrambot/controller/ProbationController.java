package pro.sky.telegrambot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.dto.ProbationDtoOut;
import pro.sky.telegrambot.service.ProbationService;

@RestController
@RequestMapping("probation")
public class ProbationController {

    private final ProbationService probationService;

    public ProbationController(ProbationService probationService) {
        this.probationService = probationService;
    }

    @PostMapping
    public ProbationDtoOut createProbation(@RequestBody ProbationDtoIn probationDtoIn) {
        return probationService.createProbation(probationDtoIn);
    }
}

package pro.sky.telegrambot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.enums.ProbationState;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.service.PetService;
import pro.sky.telegrambot.service.ProbationService;

@Controller
@RequestMapping("probations")
public class ProbationController {

    private final ProbationService probationService;
    private final PetService petService;

    public ProbationController(ProbationService probationService, PetService petService) {
        this.probationService = probationService;
        this.petService = petService;
    }

    @PostMapping
    public String createProbation(@ModelAttribute ProbationDtoIn probationDtoIn, Model model) {
        probationService.createProbation(probationDtoIn);
        model.addAttribute("pets", petService.getPets("all", PetState.WAITING_TO_BE_ADOPTED.name(), 0));
        model.addAttribute("page", 0);
        model.addAttribute("shelterType", null);
        return "pets/pets";
    }

    @GetMapping
    public String getProbations(@RequestParam Integer page, Model model) {
        model.addAttribute("probations", probationService.getAll(page));
        model.addAttribute("state", null);
        return "probations/probations";
    }

    @GetMapping("coming-to-end")
    public String getProbationaryPeriodsComingToEnd(@RequestParam Integer page, Model model) {
        model.addAttribute("probations", probationService.getProbationListByState(ProbationState.ON_THE_DECISION, page));
        model.addAttribute("state", "coming-to-end");
        return "probations/probations";
    }

    @GetMapping("{id}")
    public String getProbation(@PathVariable Long id, Model model) {
        model.addAttribute("probation", probationService.getProbationDto(id));
        return "probations/probation";
    }

    @PatchMapping("{id}")
    public String extendProbation(@PathVariable Long id, @RequestParam Integer days, Model model) {
        Probation probation = probationService.getProbation(id);
        probationService.extendProbation(probation, days);
        model.addAttribute("probations", probationService.getProbationListByState(ProbationState.ON_THE_DECISION, 0));
        return "probations/probations";
    }

    @DeleteMapping("{id}")
    public String refuseAdoption(@PathVariable Long id, Model model) {
        Probation probation = probationService.getProbation(id);
        probationService.refuseAdoption(probation);
        model.addAttribute("probations", probationService.getProbationListByState(ProbationState.ON_THE_DECISION, 0));
        return "probations/probations";
    }
}

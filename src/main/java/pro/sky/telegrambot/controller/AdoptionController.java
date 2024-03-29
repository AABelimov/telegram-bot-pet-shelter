package pro.sky.telegrambot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.service.AdoptionService;
import pro.sky.telegrambot.service.ProbationService;

@Controller
@RequestMapping("adoptions")
public class AdoptionController {

    private final AdoptionService adoptionService;
    private final ProbationService probationService;

    public AdoptionController(AdoptionService adoptionService, ProbationService probationService) {
        this.adoptionService = adoptionService;
        this.probationService = probationService;
    }

    @PostMapping
    public String createAdoption(@RequestParam(name = "probation-id") Long probationId, Model model) {
        Probation probation = probationService.getProbation(probationId);
        adoptionService.createAdoption(probation);
        model.addAttribute("page", 0);
        model.addAttribute("adoptions", adoptionService.getAllAdoptions(0));
        return "adoptions/adoptions";
    }

    @GetMapping
    public String getAdoptions(@RequestParam Integer page, Model model) {
        model.addAttribute("page", page);
        model.addAttribute("adoptions", adoptionService.getAllAdoptions(page));
        return "adoptions/adoptions";
    }
}

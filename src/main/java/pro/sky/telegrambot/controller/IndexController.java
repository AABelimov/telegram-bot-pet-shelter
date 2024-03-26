package pro.sky.telegrambot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pro.sky.telegrambot.service.PetService;

@Controller
public class IndexController {

    private final PetService petService;

    public IndexController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pets", petService.getAllPetsAvailableForAdoption(0));
        model.addAttribute("page", 0);
        model.addAttribute("shelterType", null);
        return "pets/pets";
    }
}

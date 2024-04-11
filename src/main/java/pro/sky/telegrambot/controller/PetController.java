package pro.sky.telegrambot.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.dto.PetDtoEdit;
import pro.sky.telegrambot.dto.PetDtoIn;
import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.enums.KindOfPet;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.service.PetService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

import java.io.IOException;

@Controller
@RequestMapping("pets")
public class PetController {

    private final PetService petService;
    private final UserService userService;
    private final VolunteerService volunteerService;

    public PetController(PetService petService,
                         UserService userService,
                         VolunteerService volunteerService) {
        this.petService = petService;
        this.userService = userService;
        this.volunteerService = volunteerService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createPet(@ModelAttribute PetDtoIn pet,
                            @RequestParam MultipartFile file,
                            Model model) throws IOException {
        petService.createPet(pet, file);
        KindOfPet kindOfPet = KindOfPet.valueOf(pet.getKindOfPet());
        model.addAttribute("pets", petService.getPets(kindOfPet.getShelterType(), PetState.WAITING_TO_BE_ADOPTED.name(), 0));
        model.addAttribute("page", 0);
        model.addAttribute("shelterType", kindOfPet.getShelterType().toLowerCase());
        model.addAttribute("state", "all");
        return "pets/pets";
    }

    @GetMapping("{id:\\d+}")
    public String getPet(@PathVariable Long id, Model model) {
        model.addAttribute("pet", petService.getDtoPet(id));
        return "pets/pet";
    }

    @GetMapping("{shelterType}/{state}")
    public String getPets(@PathVariable String shelterType,
                          @PathVariable String state,
                          @RequestParam Integer page,
                          Model model) {
        model.addAttribute("pets", petService.getPets(shelterType, state, page));
        model.addAttribute("page", page);
        model.addAttribute("shelterType", shelterType);
        model.addAttribute("state", state);
        return "pets/pets";
    }

    @GetMapping(value = "{id}/photo", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getPhoto(@PathVariable Long id) throws IOException {
        return petService.getPhoto(id);
    }

    @GetMapping("{id}/potential-parents")
    public String getPotentialParents(@PathVariable Long id,
                                      @RequestParam(required = false) String phone,
                                      Model model) {
        model.addAttribute("petId", id);
        model.addAttribute("parents", userService.getUsersByPhoneNumber(phone));
        model.addAttribute("volunteers", volunteerService.getVolunteers());
        model.addAttribute("probation", new ProbationDtoIn());
        return "users/potential-parents";
    }

    @GetMapping("{shelterType}/create-form")
    public String getFormToCreatePet(@PathVariable String shelterType, Model model) {
        model.addAttribute("pet", new PetDtoIn());
        model.addAttribute("shelterType", shelterType);
        return "pets/create-pet";
    }

    @GetMapping("{id}/edit-form")
    public String getFormToEDitPet(@PathVariable Long id, Model model) {
        model.addAttribute("pet", new PetDtoEdit());
        model.addAttribute("id", id);
        return "pets/edit-pet";
    }

    @PatchMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String editPet(@PathVariable Long id,
                          @ModelAttribute PetDtoEdit petDtoEdit,
                          @RequestParam(required = false) MultipartFile file,
                          Model model) throws IOException {
        petService.editPet(id, petDtoEdit, file);
        model.addAttribute("pet", petService.getDtoPet(id));
        return "pets/pet";
    }

    @DeleteMapping("{id}")
    public String deletePet(@PathVariable Long id, Model model) {
        petService.deletePet(id);
        model.addAttribute("pets", petService.getPets("all", PetState.WAITING_TO_BE_ADOPTED.name(), 0));
        model.addAttribute("page", 0);
        model.addAttribute("shelterType", "all");
        return "pets/pets";
    }
}

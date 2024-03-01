package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.dto.PetDtoEdit;
import pro.sky.telegrambot.dto.PetDtoIn;
import pro.sky.telegrambot.dto.PetDtoOut;
import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.service.PetService;
import pro.sky.telegrambot.service.UserService;
import pro.sky.telegrambot.service.VolunteerService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("pets")
public class PetController {

    private final PetService petService;
    private final UserService userService;
    private final VolunteerService volunteerService;

    public PetController(PetService petService, UserService userService, VolunteerService volunteerService) {
        this.petService = petService;
        this.userService = userService;
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Create pet",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pet created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Kind of pet isn't correct",
                            content = @Content
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createPet(@ModelAttribute PetDtoIn pet,//@ModelAttribute //@RequestPart
                            @RequestParam MultipartFile file,//@RequestPart //@RequestParam
                            Model model) throws IOException {
        petService.createPet(pet, file);
        ShelterType shelterType = pet.getKindOfPet().equals("CAT") ? ShelterType.CAT_SHELTER : ShelterType.DOG_SHELTER;
        model.addAttribute("pets", petService.getAllPetsByShelterTypeAvailableForAdoption(shelterType.name().toLowerCase(), 0));
        model.addAttribute("page", 0);
        model.addAttribute("shelterType", shelterType.name().toLowerCase());
        /*model.addAttribute("pet", petService.getPet(petDtoOut.getId()));*/
        return "pets/pets";
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

    @GetMapping("{id}/search-parent-form")
    public String getFormForParentSearch(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "pets/parent-search";
    }

    @GetMapping("{id}/potential-parents")
    public String getPotentialParents(@PathVariable Long id,
                                      @RequestParam String phone,
                                      Model model) {
        model.addAttribute("petId", id);
        model.addAttribute("parents", userService.getUsersByPhoneNumber(phone));
        model.addAttribute("volunteers", volunteerService.getVolunteers());
        model.addAttribute("probation", new ProbationDtoIn());
        return "pets/potential-parents";
    }

    @GetMapping()
    public String getPetsAvailableForAdoption(@RequestParam Integer page, Model model) {
        model.addAttribute("pets", petService.getAllPetsAvailableForAdoption(page));
        model.addAttribute("page", page);
        model.addAttribute("shelterType", null);
        return "pets/pets";
    }

    @GetMapping("{id:\\d+}")
    public String getPet(@PathVariable Long id, Model model) {
        model.addAttribute("pet", petService.getDtoPet(id));
        return "pets/pet";
    }

    @GetMapping("{shelterType:[a-z_]+}")
    public String getPetsByShelterTypeAvailableForAdoption(@PathVariable String shelterType, @RequestParam Integer page, Model model) {
        model.addAttribute("pets", petService.getAllPetsByShelterTypeAvailableForAdoption(shelterType, page));
        model.addAttribute("page", page);
        model.addAttribute("shelterType", shelterType);
        return "pets/pets";
    }

    @GetMapping("{id}/photo")
    public void getPhoto(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Pet pet = petService.getPet(id);
        Path path = Path.of(pet.getPhotoPath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream()
        ) {
            response.setStatus(200);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            is.transferTo(os);
        }
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

/*    @Operation(
            summary = "Upload a photo to pet's avatar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avatar uploaded",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetDtoOut.class)
                            )
                    )
            }
    )
    @PatchMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PetDtoOut uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile file) {
        try {
            return petService.uploadAvatar(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    @DeleteMapping("{id}")
    public String deletePet(@PathVariable Long id, Model model) {
        petService.deletePet(id);
        model.addAttribute("pets", petService.getAllPetsAvailableForAdoption(0));
        model.addAttribute("page", 0);
        model.addAttribute("shelterType", null);
        return "pets/pets";
    }
}

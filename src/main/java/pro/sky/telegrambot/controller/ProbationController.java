package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.dto.ProbationDtoIn;
import pro.sky.telegrambot.dto.ProbationDtoOut;
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

    @Operation(
            summary = "Create probation",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Probation created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProbationDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If pet is already on probation",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User, volunteer or pet not found",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public String createProbation(@Parameter(description = "")
                                  @ModelAttribute ProbationDtoIn probationDtoIn,
                                  Model model) {
        probationService.createProbation(probationDtoIn);
        model.addAttribute("pets", petService.getAllPetsAvailableForAdoption(0));
        model.addAttribute("page", 0);
        model.addAttribute("shelterType", null);
        return "pets/pets";
    }

    @GetMapping
    public String getProbations(@RequestParam Integer page, Model model) {
        model.addAttribute("probations", probationService.getAllProbations(page));
        model.addAttribute("state", null);
        return "probations/probations";
    }

    @GetMapping("coming-to-end")
    public String getProbationaryPeriodsComingToEnd(@RequestParam Integer page, Model model) {
        model.addAttribute("probations", probationService.getProbationsByState(ProbationState.ON_THE_DECISION, page));
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
        model.addAttribute("probations", probationService.getProbationsByState(ProbationState.ON_THE_DECISION, 0));
        return "probations/probations";
    }

    @DeleteMapping("{id}")
    public String refuseAdoption(@PathVariable Long id, Model model) {
        Probation probation = probationService.getProbation(id);
        probationService.refuseAdoption(probation);
        model.addAttribute("probations", probationService.getProbationsByState(ProbationState.ON_THE_DECISION, 0));
        return "probations/probations";
    }
}

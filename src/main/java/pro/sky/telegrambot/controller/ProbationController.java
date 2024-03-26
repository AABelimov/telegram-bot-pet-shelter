package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
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
    public ProbationDtoOut createProbation(@Parameter(description = "")
                                           @RequestBody ProbationDtoIn probationDtoIn) {
        return probationService.createProbation(probationDtoIn);
    }
}

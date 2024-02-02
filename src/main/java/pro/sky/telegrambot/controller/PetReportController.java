package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.dto.PetReportDtoOut;
import pro.sky.telegrambot.service.PetReportService;

import java.util.List;

@RestController
@RequestMapping("reports")
public class PetReportController {

    private final PetReportService petReportService;

    public PetReportController(PetReportService petReportService) {
        this.petReportService = petReportService;
    }

    @Operation(
            summary = "Get report by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Report found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetReportDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("{id}")
    public PetReportDtoOut getReport(@Parameter(description = "id of report to be searched")
                                     @PathVariable Long id) {
        return petReportService.getReportDto(id);
    }

    @Operation(
            summary = "Get all unverified reports",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All unverified reports found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PetReportDtoOut.class))
                            )
                    )

            }
    )
    @GetMapping("unverified")
    public List<PetReportDtoOut> getUnverifiedReports() {
        return petReportService.getUnverifiedReports();
    }

    @Operation(
            summary = "Accept report by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Report was accepted",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetReportDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report not found",
                            content = @Content
                    )
            }
    )
    @PatchMapping("{id}/accept")
    public PetReportDtoOut acceptReport(@Parameter(description = "id of the report that will be accepted")
                                        @PathVariable Long id) {
        return petReportService.acceptReport(id);
    }

    @Operation(
            summary = "Deny report by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Report was denied",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetReportDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report not found",
                            content = @Content
                    )
            }
    )
    @PatchMapping("{id}/deny")
    public PetReportDtoOut denyReport(@Parameter(description = "id of the report that will be denied")
                                      @PathVariable Long id,
                                      @Parameter(description = "rejection reason")
                                      @RequestParam String comment) {
        return petReportService.denyReport(id, comment);
    }
}

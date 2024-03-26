package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.dto.PetReportDtoOut;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.service.PetReportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("reports")
public class PetReportController {

    private final PetReportService petReportService;

    public PetReportController(PetReportService petReportService) {
        this.petReportService = petReportService;
    }

    @GetMapping
    public String getReports(@RequestParam(required = false, name = "shelter-type") String shelterType,
                             @RequestParam(required = false) String state,
                             @RequestParam Integer page,
                             Model model) {
        model.addAttribute("reports", petReportService.getReports(shelterType, state, page));
        model.addAttribute("shelterType", shelterType);
        return "reports/reports";
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
    public String getReport(@Parameter(description = "id of report to be searched")
                            @PathVariable Long id,
                            Model model) {
        model.addAttribute("report", petReportService.getReportDto(id));
        return "reports/report";
    }

/*    @Operation(
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
    public String getUnverifiedReports(@RequestParam Integer page, Model model) {
        model.addAttribute("reports", petReportService.getUnverifiedReports(page));
        return "reports/reports";
    }*/

    @GetMapping("{id}/photo")
    public void getPhoto(@PathVariable Long id, HttpServletResponse response) throws IOException {
        PetReport petReport = petReportService.getReport(id);
        Path path = Path.of(petReport.getPhotoPath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream()
        ) {
            response.setStatus(200);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            is.transferTo(os);
        }
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
    public String acceptReport(@Parameter(description = "id of the report that will be accepted")
                               @PathVariable Long id,
                               Model model) {
        petReportService.acceptReport(id);
        model.addAttribute("reports", petReportService.getUnverifiedReports(0));
        return "reports/reports";
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
    public String denyReport(@Parameter(description = "id of the report that will be denied")
                             @PathVariable Long id,
                             @Parameter(description = "rejection reason")
                             @RequestParam String comment,
                             Model model) {
        petReportService.denyReport(id, comment);
        model.addAttribute("reports", petReportService.getUnverifiedReports(0));
        return "reports/reports";
    }
}

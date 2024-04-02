package pro.sky.telegrambot.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.enums.PetReportState;
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
        model.addAttribute("state", state);
        return "reports/reports";
    }

    @GetMapping("{id}")
    public String getReport(@PathVariable Long id, Model model) {
        model.addAttribute("report", petReportService.getReportDto(id));
        return "reports/report";
    }

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

    @PatchMapping("{id}/accept")
    public String acceptReport(@PathVariable Long id, Model model) {
        petReportService.acceptReport(id);
        model.addAttribute("reports", petReportService.getReports(PetReportState.WAITING_FOR_VERIFICATION, 0));
        return "reports/reports";
    }

    @PatchMapping("{id}/deny")
    public String denyReport(@PathVariable Long id, @RequestParam String comment,
                             Model model) {
        petReportService.denyReport(id, comment);
        model.addAttribute("reports", petReportService.getReports(PetReportState.WAITING_FOR_VERIFICATION, 0));
        return "reports/reports";
    }
}

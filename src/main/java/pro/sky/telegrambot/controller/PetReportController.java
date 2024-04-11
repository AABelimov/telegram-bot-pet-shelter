package pro.sky.telegrambot.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.service.PetReportService;

import java.io.IOException;

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

    @GetMapping(value = "{id}/photo", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getPhoto(@PathVariable Long id) throws IOException {
        return petReportService.getPhoto(id);
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

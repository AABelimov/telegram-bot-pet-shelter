package pro.sky.telegrambot.controller;

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

    @GetMapping("unverified")
    public List<PetReportDtoOut> getUnverifiedReports() {
        return petReportService.getUnverifiedReports();
    }

    @GetMapping("{id}")
    public PetReportDtoOut getReport(@PathVariable Long id) {
        return petReportService.getReportDto(id);
    }

    @PatchMapping("{id}/accept")
    public PetReportDtoOut acceptReport(@PathVariable Long id) {
        return petReportService.acceptReport(id);
    }

    @PatchMapping("{id}/deny")
    public PetReportDtoOut denyReport(@PathVariable Long id, @RequestParam String comment) {
        return petReportService.denyReport(id, comment);
    }
}

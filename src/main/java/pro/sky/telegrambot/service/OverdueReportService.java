package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.OverdueReport;
import pro.sky.telegrambot.model.Probation;
import pro.sky.telegrambot.repository.OverdueReportRepository;

import java.util.List;

@Service
public class OverdueReportService {

    private final OverdueReportRepository overdueReportRepository;

    public OverdueReportService(OverdueReportRepository overdueReportRepository) {
        this.overdueReportRepository = overdueReportRepository;
    }

    public void createOverdueReport(Probation probation, int days) {
        OverdueReport overdueReport = new OverdueReport();
        overdueReport.setId(probation.getId());
        overdueReport.setDays(days);
        overdueReport.setUser(probation.getUser());
        overdueReport.setPet(probation.getPet());
        overdueReport.setVolunteer(probation.getVolunteer());
        overdueReportRepository.save(overdueReport);
    }

    public OverdueReport getOverdueReport(Long id) {
        return overdueReportRepository.findById(id).orElse(null);
    }

    public void deleteOverdueReport(Probation probation) {
        OverdueReport overdueReport = getOverdueReport(probation.getId());
        if (overdueReport != null) {
            overdueReportRepository.delete(overdueReport);
        }
    }

    public List<OverdueReport> getAll() {
        return overdueReportRepository.findAll();
    }

    public List<OverdueReport> getOverdueReportsByVolunteerId(Long volunteerId) {
        return overdueReportRepository.findByVolunteerIdOrderByDaysDesc(volunteerId);
    }
}

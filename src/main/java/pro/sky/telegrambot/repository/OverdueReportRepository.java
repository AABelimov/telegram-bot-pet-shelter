package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.OverdueReport;

import java.util.List;

public interface OverdueReportRepository extends JpaRepository<OverdueReport, Long> {
    List<OverdueReport> findByVolunteerIdOrderByDaysDesc(Long volunteerId);
}

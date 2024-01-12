package pro.sky.telegrambot.dto;

import java.time.LocalDateTime;

public class ProbationDtoOut {

    private UserDtoOut user;
    private PetDtoOut pet;
    private LocalDateTime probationEndDate;
    private LocalDateTime lastReportDate;
    private VolunteerDtoOut volunteer;

    public UserDtoOut getUser() {
        return user;
    }

    public void setUser(UserDtoOut user) {
        this.user = user;
    }

    public PetDtoOut getPet() {
        return pet;
    }

    public void setPet(PetDtoOut pet) {
        this.pet = pet;
    }

    public LocalDateTime getProbationEndDate() {
        return probationEndDate;
    }

    public void setProbationEndDate(LocalDateTime probationEndDate) {
        this.probationEndDate = probationEndDate;
    }

    public LocalDateTime getLastReportDate() {
        return lastReportDate;
    }

    public void setLastReportDate(LocalDateTime lastReportDate) {
        this.lastReportDate = lastReportDate;
    }

    public VolunteerDtoOut getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(VolunteerDtoOut volunteer) {
        this.volunteer = volunteer;
    }
}

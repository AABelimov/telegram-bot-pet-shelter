package pro.sky.telegrambot.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProbationDtoOut {

    private Long id;
    private UserDtoOut user;
    private PetDtoOut pet;
    private LocalDateTime probationEndDate;
    private LocalDateTime lastReportDate;
    private VolunteerDtoOut volunteer;

    public ProbationDtoOut() {

    }

    public ProbationDtoOut(Long id,
                           UserDtoOut user,
                           PetDtoOut pet,
                           LocalDateTime probationEndDate,
                           LocalDateTime lastReportDate,
                           VolunteerDtoOut volunteer) {
        this.id = id;
        this.user = user;
        this.pet = pet;
        this.probationEndDate = probationEndDate;
        this.lastReportDate = lastReportDate;
        this.volunteer = volunteer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProbationDtoOut that = (ProbationDtoOut) o;
        return Objects.equals(user, that.user) && Objects.equals(pet, that.pet) && Objects.equals(probationEndDate, that.probationEndDate) && Objects.equals(lastReportDate, that.lastReportDate) && Objects.equals(volunteer, that.volunteer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, pet, probationEndDate, lastReportDate, volunteer);
    }
}

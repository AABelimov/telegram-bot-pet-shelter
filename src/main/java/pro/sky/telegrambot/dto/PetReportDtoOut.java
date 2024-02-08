package pro.sky.telegrambot.dto;

import java.time.LocalDateTime;

public class PetReportDtoOut {

    private Long id;
    private UserDtoOut user;
    private PetDtoOut pet;
    private VolunteerDtoOut volunteer;
    private LocalDateTime timeSendingReport;
    private String diet;
    private String wellBeing;
    private String changeInBehavior;

    public PetReportDtoOut() {

    }

    public PetReportDtoOut(Long id,
                           UserDtoOut user,
                           PetDtoOut pet,
                           VolunteerDtoOut volunteer,
                           LocalDateTime timeSendingReport,
                           String diet,
                           String wellBeing,
                           String changeInBehavior) {
        this.id = id;
        this.user = user;
        this.pet = pet;
        this.volunteer = volunteer;
        this.timeSendingReport = timeSendingReport;
        this.diet = diet;
        this.wellBeing = wellBeing;
        this.changeInBehavior = changeInBehavior;
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

    public VolunteerDtoOut getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(VolunteerDtoOut volunteer) {
        this.volunteer = volunteer;
    }

    public LocalDateTime getTimeSendingReport() {
        return timeSendingReport;
    }

    public void setTimeSendingReport(LocalDateTime timeSendingReport) {
        this.timeSendingReport = timeSendingReport;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getWellBeing() {
        return wellBeing;
    }

    public void setWellBeing(String wellBeing) {
        this.wellBeing = wellBeing;
    }

    public String getChangeInBehavior() {
        return changeInBehavior;
    }

    public void setChangeInBehavior(String changeInBehavior) {
        this.changeInBehavior = changeInBehavior;
    }
}

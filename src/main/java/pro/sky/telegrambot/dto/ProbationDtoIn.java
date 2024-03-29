package pro.sky.telegrambot.dto;

public class ProbationDtoIn {

    private Long userId;
    private Long petId;
    private Long volunteerId;

    public ProbationDtoIn() {

    }

    public ProbationDtoIn(Long userId, Long petId, Long volunteerId) {
        this.userId = userId;
        this.petId = petId;
        this.volunteerId = volunteerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }
}

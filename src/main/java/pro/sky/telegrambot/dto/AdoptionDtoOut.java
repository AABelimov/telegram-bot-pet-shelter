package pro.sky.telegrambot.dto;

import java.time.LocalDateTime;

public class AdoptionDtoOut {

    private Long id;
    private UserDtoOut user;
    private PetDtoOut pet;
    private LocalDateTime adoptionTime;

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

    public LocalDateTime getAdoptionTime() {
        return adoptionTime;
    }

    public void setAdoptionTime(LocalDateTime adoptionTime) {
        this.adoptionTime = adoptionTime;
    }
}

package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adopted")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    private Pet pet;
    private LocalDateTime adoptionTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public LocalDateTime getAdoptionTime() {
        return adoptionTime;
    }

    public void setAdoptionTime(LocalDateTime adoptionTime) {
        this.adoptionTime = adoptionTime;
    }
}

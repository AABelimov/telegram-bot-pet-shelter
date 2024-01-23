package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Probation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    private Pet pet;
    private String shelterType;
    private LocalDateTime probationEndDate;
    private LocalDateTime lastReportDate;

    @ManyToOne
    private Volunteer volunteer;
    private String state;

    public Probation() {

    }

    public Probation(Long id, User user,
                     Pet pet,
                     String shelterType,
                     LocalDateTime probationEndDate,
                     LocalDateTime lastReportDate,
                     Volunteer volunteer,
                     String state) {
        this.id = id;
        this.user = user;
        this.pet = pet;
        this.shelterType = shelterType;
        this.probationEndDate = probationEndDate;
        this.lastReportDate = lastReportDate;
        this.volunteer = volunteer;
        this.state = state;
    }

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

    public String getShelterType() {
        return shelterType;
    }

    public void setShelterType(String shelterType) {
        this.shelterType = shelterType;
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

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Probation probation = (Probation) o;
        return Objects.equals(id, probation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

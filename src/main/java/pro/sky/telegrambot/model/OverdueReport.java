package pro.sky.telegrambot.model;

import javax.persistence.*;

@Entity
@Table(name = "overdue_reports")
public class OverdueReport {

    @Id
    private Long id;

    private int days;

    @ManyToOne
    private User user;

    @OneToOne
    private Pet pet;

    @ManyToOne
    private Volunteer volunteer;

    public OverdueReport() {

    }

    public OverdueReport(Long id, int days, User user, Pet pet, Volunteer volunteer) {
        this.id = id;
        this.days = days;
        this.user = user;
        this.pet = pet;
        this.volunteer = volunteer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
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

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }
}

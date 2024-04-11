package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;

    private String name;

    private String phoneNumber;

    private String selectedShelter;

    private String state;

    public User() {

    }

    public User(Long id, String name, String phoneNumber, String selectedShelter, String state) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.selectedShelter = selectedShelter;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSelectedShelter() {
        return selectedShelter;
    }

    public void setSelectedShelter(String selectedShelter) {
        this.selectedShelter = selectedShelter;
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
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

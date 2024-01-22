package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The users table will store users who interacted with the bot
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * The id will be equal to the telegram user id value
     */
    @Id
    private Long id;

    /**
     * Username
     */
    private String name;

    /**
     * User phone number
     */
    private String phoneNumber;

    /**
     * User selected pet shelter
     */
    private String selectedShelter;

    /**
     * Indicator by which the bot determines what stage the user is currently at
     */
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
}

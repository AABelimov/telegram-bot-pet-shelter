package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Volunteers will be stored in the volunteers table
 */
@Entity
@Table(name = "volunteers")
public class Volunteer {

    /**
     * The id will be equal to the telegram user id value
     */
    @Id
    private Long id;

    /**
     * Volunteer name
     */
    private String name;

    /**
     * Field in which the user will be stored while the volunteer will correspond with him through the bot
     */
    @OneToOne
    private User user;

    /**
     * Indicator by which the bot determines what stage the volunteer is currently at
     */
    private String state;

    public Volunteer() {

    }

    public Volunteer(Long id, String name, User user, String state) {
        this.id = id;
        this.name = name;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(id, volunteer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package pro.sky.telegrambot.model;

import javax.persistence.*;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kindOfPet;

    private String name;

    private String aboutPet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKindOfPet() {
        return kindOfPet;
    }

    public void setKindOfPet(String kindOfPet) {
        this.kindOfPet = kindOfPet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutPet() {
        return aboutPet;
    }

    public void setAboutPet(String aboutPet) {
        this.aboutPet = aboutPet;
    }
}

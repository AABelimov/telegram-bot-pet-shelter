package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kindOfPet;
    private String name;
    private String photoPath;
    private String aboutPet;
    private String state;

    public Pet() {

    }

    public Pet(Long id, String kindOfPet, String name, String photoPath, String aboutPet, String state) {
        this.id = id;
        this.kindOfPet = kindOfPet;
        this.name = name;
        this.photoPath = photoPath;
        this.aboutPet = aboutPet;
        this.state = state;
    }

    public Pet(Pet original) {
        this.id = original.getId();
        this.kindOfPet = original.getKindOfPet();
        this.name = original.getName();
        this.photoPath = original.getPhotoPath();
        this.aboutPet = original.getAboutPet();
        this.state = original.getState();
    }

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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getAboutPet() {
        return aboutPet;
    }

    public void setAboutPet(String aboutPet) {
        this.aboutPet = aboutPet;
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
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

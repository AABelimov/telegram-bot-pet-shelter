package pro.sky.telegrambot.dto;

import java.util.Objects;

public class PetDtoOut {

    private Long id;
    private String kindOfPet;
    private String name;
    private String aboutPet;
    private String photoPath;

    public PetDtoOut() {

    }

    public PetDtoOut(Long id, String kindOfPet, String name, String aboutPet, String photoPath) {
        this.id = id;
        this.kindOfPet = kindOfPet;
        this.name = name;
        this.aboutPet = aboutPet;
        this.photoPath = photoPath;
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

    public String getAboutPet() {
        return aboutPet;
    }

    public void setAboutPet(String aboutPet) {
        this.aboutPet = aboutPet;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetDtoOut petDtoOut = (PetDtoOut) o;
        return Objects.equals(id, petDtoOut.id) && Objects.equals(kindOfPet, petDtoOut.kindOfPet) && Objects.equals(name, petDtoOut.name) && Objects.equals(aboutPet, petDtoOut.aboutPet) && Objects.equals(photoPath, petDtoOut.photoPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kindOfPet, name, aboutPet, photoPath);
    }
}

package pro.sky.telegrambot.dto;

public class PetDtoIn {

    private String name;
    private String kindOfPet;
    private String aboutPet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKindOfPet() {
        return kindOfPet;
    }

    public void setKindOfPet(String kindOfPet) {
        this.kindOfPet = kindOfPet;
    }

    public String getAboutPet() {
        return aboutPet;
    }

    public void setAboutPet(String aboutPet) {
        this.aboutPet = aboutPet;
    }
}

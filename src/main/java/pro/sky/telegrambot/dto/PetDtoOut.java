package pro.sky.telegrambot.dto;

public class PetDtoOut {

    private Long id;
    private String kindOfPet;
    private String name;

    public PetDtoOut() {

    }

    public PetDtoOut(Long id, String kindOfPet, String name) {
        this.id = id;
        this.kindOfPet = kindOfPet;
        this.name = name;
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
}

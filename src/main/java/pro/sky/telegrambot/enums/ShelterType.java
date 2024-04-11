package pro.sky.telegrambot.enums;

public enum ShelterType {

    CAT_SHELTER("CAT"),
    DOG_SHELTER("DOG");

    private final String kindOfPet;

    ShelterType(String kindOfPet) {
        this.kindOfPet = kindOfPet;
    }

    public String getKindOfPet() {
        return kindOfPet;
    }
}

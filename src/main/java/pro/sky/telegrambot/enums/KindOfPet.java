package pro.sky.telegrambot.enums;

public enum KindOfPet {

    CAT("CAT_SHELTER"),
    DOG("DOG_SHELTER");

    private final String shelterType;

    KindOfPet(String shelterType) {
        this.shelterType = shelterType;
    }

    public String getShelterType() {
        return shelterType;
    }
}

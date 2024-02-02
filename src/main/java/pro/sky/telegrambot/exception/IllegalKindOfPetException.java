package pro.sky.telegrambot.exception;

public class IllegalKindOfPetException extends BadRequestException{

    private final String kindOfPet;

    public IllegalKindOfPetException(String kindOfPet) {
        this.kindOfPet = kindOfPet;
    }

    @Override
    public String getMessage() {
        return "We don't keep " + kindOfPet + " in our shelters";
    }
}

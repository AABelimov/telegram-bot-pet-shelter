package pro.sky.telegrambot.exception;

public class PetNotFoundException extends NotFoundException {

    private final Long id;

    public PetNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Pet with id = " + id + " not found!";
    }
}

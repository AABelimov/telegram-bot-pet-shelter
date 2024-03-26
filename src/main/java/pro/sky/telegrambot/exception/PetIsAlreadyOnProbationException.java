package pro.sky.telegrambot.exception;

public class PetIsAlreadyOnProbationException extends BadRequestException {

    private final Long id;

    public PetIsAlreadyOnProbationException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Pet with id = " + id + " is already on probation";
    }
}

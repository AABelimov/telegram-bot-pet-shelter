package pro.sky.telegrambot.exception;

public class ProbationNotFoundException extends NotFoundException {

    private final Long id;

    public ProbationNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Probation with id = " + id + " not found!";
    }
}

package pro.sky.telegrambot.exception;

public class UserNotFoundException extends NotFoundException {

    private final Long id;

    public UserNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "User with id = " + id + " not found!";
    }
}

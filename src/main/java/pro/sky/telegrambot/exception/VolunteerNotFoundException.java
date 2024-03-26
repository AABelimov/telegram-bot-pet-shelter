package pro.sky.telegrambot.exception;

public class VolunteerNotFoundException extends NotFoundException {

    private final Long id;

    public VolunteerNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Volunteer with id = " + id + " not found!";
    }
}

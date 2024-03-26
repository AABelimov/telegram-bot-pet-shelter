package pro.sky.telegrambot.exception;

public class PetReportNotFoundException extends NotFoundException {

    private final Long id;

    public PetReportNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Report with id = " + id + " not found!";
    }
}

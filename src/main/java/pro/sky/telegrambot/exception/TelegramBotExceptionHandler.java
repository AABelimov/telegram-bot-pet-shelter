package pro.sky.telegrambot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TelegramBotExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotExceptionHandler.class);

    @ExceptionHandler({
            PetNotFoundException.class,
            PetReportNotFoundException.class,
            ProbationNotFoundException.class,
            UserNotFoundException.class,
            VolunteerNotFoundException.class,
    })
    public ResponseEntity<?> handleNotFound(NotFoundException e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}

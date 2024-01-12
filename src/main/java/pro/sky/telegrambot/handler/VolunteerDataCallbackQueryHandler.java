package pro.sky.telegrambot.handler;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.VolunteerCommand;

@Component
public class VolunteerDataCallbackQueryHandler {


    public void handleMainMenu(Long volunteerId, Integer messageId, String data) {
        VolunteerCommand volunteerCommand = VolunteerCommand.valueOf(data);

        switch (volunteerCommand) {
            case CHECK_REPORTS:

                break;
        }
    }
}

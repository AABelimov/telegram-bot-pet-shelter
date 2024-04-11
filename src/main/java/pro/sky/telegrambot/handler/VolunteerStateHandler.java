package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.VolunteerState;
import pro.sky.telegrambot.service.VolunteerService;

@Component
public class VolunteerStateHandler {

    private final VolunteerService volunteerService;
    private final VolunteerTextMessageHandler volunteerTextMessageHandler;
    private final VolunteerDataCallbackQueryHandler volunteerDataCallbackQueryHandler;

    public VolunteerStateHandler(VolunteerService volunteerService,
                                 VolunteerTextMessageHandler volunteerTextMessageHandler,
                                 VolunteerDataCallbackQueryHandler volunteerDataCallbackQueryHandler) {
        this.volunteerService = volunteerService;
        this.volunteerTextMessageHandler = volunteerTextMessageHandler;
        this.volunteerDataCallbackQueryHandler = volunteerDataCallbackQueryHandler;
    }

    public void handleState(Long volunteerId, CallbackQuery callbackQuery, Message message) {
        VolunteerState volunteerState = volunteerService.getVolunteerState(volunteerId);

        if (callbackQuery != null) {
            Integer messageId = callbackQuery.message().messageId();
            String data = callbackQuery.data();

            switch (volunteerState) {
                case AT_WORK:
                case NOT_AT_WORK:
                case MAIN_MENU:
                    volunteerDataCallbackQueryHandler.handleMainMenu(volunteerId, messageId, data);
                    break;
                case CHECK_REPORTS:
                    volunteerDataCallbackQueryHandler.handleCheckReports(volunteerId, messageId, data);
                    break;
                case OVERDUE_REPORTS:
                    volunteerDataCallbackQueryHandler.handleOverdueReports(volunteerId, messageId, data);
                    break;
                case DECIDE_ON_PROBATION:
                    volunteerDataCallbackQueryHandler.handleDecideOnProbation(volunteerId, messageId, data);
                    break;
            }

        } else {
            String text = message.text();

            if (text != null) {
                switch (volunteerState) {
                    case START:
                        volunteerTextMessageHandler.handleStart(volunteerId, text, null);
                        break;
                    case CONVERSATION:
                        volunteerTextMessageHandler.sendMessageToUser(volunteerId, text);
                        break;
                    case COMMENTARY_ON_THE_REPORT:
                        volunteerTextMessageHandler.handleCommentaryOnTheReport(volunteerId, text);
                        break;
                }
            }
        }
    }
}

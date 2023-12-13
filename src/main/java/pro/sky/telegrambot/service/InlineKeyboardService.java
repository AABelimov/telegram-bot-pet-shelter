package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserCommand;

/**
 * This service create inline keyboards
 */
@Service
public class InlineKeyboardService {

    /**
     * This method create inline keyboard for choose shelter menu
     * @return
     */
    public InlineKeyboardMarkup getChooseShelterKeyboard() {
        InlineKeyboardButton catButton = new InlineKeyboardButton("Приют с кошками")
                .callbackData(ShelterType.CAT.name());
        InlineKeyboardButton dogButton = new InlineKeyboardButton("Приют с собаками")
                .callbackData(ShelterType.DOG.name());

        return new InlineKeyboardMarkup(catButton, dogButton);
    }

    /**
     * This method create inline keyboard for main menu
     * @return
     */
    public InlineKeyboardMarkup getMainMenuKeyboard() {
        InlineKeyboardButton infoAboutShelterButton = new InlineKeyboardButton("Информация о приюте")
                .callbackData(UserCommand.INFO_ABOUT_SHELTER.name());
        InlineKeyboardButton howAdoptPetButton = new InlineKeyboardButton("Как взять животное из приюта")
                .callbackData(UserCommand.HOW_ADOPT_PET.name());
        InlineKeyboardButton sendReportButton = new InlineKeyboardButton("Отправить отчет")
                .callbackData(UserCommand.SEND_REPORT.name());
        InlineKeyboardButton callVolunteerButton = new InlineKeyboardButton("Позвать волонтера")
                .callbackData(UserCommand.CALL_VOLUNTEER.name());
        InlineKeyboardButton backButton = new InlineKeyboardButton("Назад")
                .callbackData(UserCommand.BACK.name());

        return new InlineKeyboardMarkup(infoAboutShelterButton)
                .addRow(howAdoptPetButton)
                .addRow(sendReportButton)
                .addRow(callVolunteerButton)
                .addRow(backButton);
    }
}

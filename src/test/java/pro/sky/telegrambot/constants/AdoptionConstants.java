package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.model.Adoption;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.model.User;

import java.time.LocalDateTime;

import static pro.sky.telegrambot.constants.UserConstants.*;
import static pro.sky.telegrambot.constants.PetConstants.*;

public class AdoptionConstants {

    public static final Long ADOPTION_ID_1 = 1L;
    public static final User ADOPTION_USER_1 = USER_1;
    public static final Pet ADOPTION_PET_1 = PET_1;
    public static final LocalDateTime ADOPTION_LOCAL_DATE_TIME_1 = LocalDateTime.MAX;
    public static final Adoption ADOPTION_1 = new Adoption(ADOPTION_ID_1, ADOPTION_USER_1, ADOPTION_PET_1, ADOPTION_LOCAL_DATE_TIME_1);
}

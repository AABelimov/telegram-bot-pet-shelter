package pro.sky.telegrambot.constants;

import pro.sky.telegrambot.dto.PetDtoOut;
import pro.sky.telegrambot.model.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetConstants {

    public static final Long PET_ID_1 = 1L;
    public static final Long PET_ID_2 = 2L;
    public static final Long PET_ID_3 = 2L;
    public static final Long NOT_FOUND_PET_ID = 100L;
    public static final String KIND_OF_PET_1 = "CAT";
    public static final String KIND_OF_PET_2 = "CAT";
    public static final String KIND_OF_PET_3 = "DOG";
    public static final String PET_NAME_1 = "PET_NAME_1";
    public static final String PET_NAME_2 = "PET_NAME_2";
    public static final String PET_NAME_3 = "PET_NAME_3";
    public static final String PET_PHOTO_PATH_1 = "PET_PHOTO_PATH_1";
    public static final String PET_PHOTO_PATH_2 = "PET_PHOTO_PATH_2";
    public static final String PET_PHOTO_PATH_3 = "PET_PHOTO_PATH_3";
    public static final String PET_ABOUT_PET_1 = "PET_ABOUT_PET_1";
    public static final String PET_ABOUT_PET_2 = "PET_ABOUT_PET_2";
    public static final String PET_ABOUT_PET_3 = "PET_ABOUT_PET_3";
    public static final String PET_STATE_1 = "";
    public static final String PET_STATE_2 = "";
    public static final String PET_STATE_3 = "";
    public static final Pet PET_1 = new Pet(PET_ID_1, KIND_OF_PET_1, PET_NAME_1, PET_PHOTO_PATH_1, PET_ABOUT_PET_1, PET_STATE_1);
    public static final Pet PET_2 = new Pet(PET_ID_2, KIND_OF_PET_2, PET_NAME_2, PET_PHOTO_PATH_2, PET_ABOUT_PET_2, PET_STATE_2);
    public static final Pet PET_3 = new Pet(PET_ID_3, KIND_OF_PET_3, PET_NAME_3, PET_PHOTO_PATH_3, PET_ABOUT_PET_3, PET_STATE_3);
    public static final PetDtoOut PET_DTO_OUT_1 = new PetDtoOut(PET_ID_1, KIND_OF_PET_1, PET_NAME_1);
    public static final PetDtoOut PET_DTO_OUT_2 = new PetDtoOut(PET_ID_2, KIND_OF_PET_2, PET_NAME_2);
    public static final PetDtoOut PET_DTO_OUT_3 = new PetDtoOut(PET_ID_3, KIND_OF_PET_3, PET_NAME_3);
    public static final List<Pet> PET_LIST = new ArrayList<>(List.of(
            PET_1,
            PET_2,
            PET_3
    ));
}

package pro.sky.telegrambot.mapper;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.dto.AdoptionDtoOut;
import pro.sky.telegrambot.model.Adoption;

@Component
public class AdoptionMapper {

    private final UserMapper userMapper;
    private final PetMapper petMapper;

    public AdoptionMapper(UserMapper userMapper, PetMapper petMapper) {
        this.userMapper = userMapper;
        this.petMapper = petMapper;
    }

    public AdoptionDtoOut toDto(Adoption adoption) {
        AdoptionDtoOut adoptionDtoOut = new AdoptionDtoOut();

        adoptionDtoOut.setId(adoption.getId());
        adoptionDtoOut.setUser(userMapper.toDto(adoption.getUser()));
        adoptionDtoOut.setPet(petMapper.toDto(adoption.getPet()));
        adoptionDtoOut.setAdoptionTime(adoption.getAdoptionTime());

        return adoptionDtoOut;
    }
}

package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.dto.PetDtoIn;
import pro.sky.telegrambot.dto.PetDtoOut;
import pro.sky.telegrambot.enums.PetState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.exception.PetNotFoundException;
import pro.sky.telegrambot.mapper.PetMapper;
import pro.sky.telegrambot.model.Pet;
import pro.sky.telegrambot.repository.PetRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final String photosDir;

    public PetService(PetRepository petRepository,
                      PetMapper petMapper,
                      @Value("${path.to.folder.with.pets.photos}") String photosDir) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
        this.photosDir = photosDir;
    }

    public PetDtoOut createPet(PetDtoIn petDtoIn) {
        Pet pet;
        petDtoIn.setKindOfPet(petDtoIn.getKindOfPet().toUpperCase());
        pet = petMapper.toEntity(petDtoIn);

        return petMapper.toDto(petRepository.save(pet));
    }

    public PetDtoOut uploadAvatar(Long id, MultipartFile file) throws IOException {
        Pet pet = getPet(id);
        ShelterType shelterType = pet.getKindOfPet().equals("CAT") ? ShelterType.CAT_SHELTER : ShelterType.DOG_SHELTER;
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path filePath = Path.of(photosDir, shelterType.name().toLowerCase(), pet.hashCode() + "." + extension);
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            pet.setPhotoPath(filePath.toString());
            petRepository.save(pet);
            return petMapper.toDto(pet);
        }
    }

    public Pet getPet(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    }

    public List<Pet> getListOfAnimals(ShelterType shelterType, PetState state, PageRequest pageRequest) {
        String kindOfPet = shelterType.equals(ShelterType.DOG_SHELTER) ? "DOG" : "CAT";

        return petRepository.findByKindOfPetAndStateOrderByName(kindOfPet, state.name(), pageRequest);
    }

    public long countPetsByKindOfPet(ShelterType shelterType) {
        String kindOfPet = shelterType.equals(ShelterType.DOG_SHELTER) ? "DOG" : "CAT";

        return petRepository.countByKindOfPetAndState(kindOfPet, PetState.WAITING_TO_BE_ADOPTED.name());
    }

    public void setPetState(Long id, PetState state) {
        Pet pet = getPet(id);
        pet.setState(state.name());
        petRepository.save(pet);
    }

}

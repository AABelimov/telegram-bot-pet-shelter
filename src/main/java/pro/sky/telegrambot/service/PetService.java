package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.dto.PetDtoEdit;
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
import java.util.stream.Collectors;

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

    public void createPet(PetDtoIn petDtoIn, MultipartFile file) throws IOException {
        Pet pet;
        petDtoIn.setKindOfPet(petDtoIn.getKindOfPet().toUpperCase());
        pet = petRepository.save(petMapper.toEntity(petDtoIn));

        uploadAvatar(pet, file);
    }

    private void uploadAvatar(Pet pet, MultipartFile file) throws IOException {
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
        }
    }

    public Pet getPet(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    }

    public PetDtoOut getDtoPet(Long id) {
        return petMapper.toDto(getPet(id));
    }

    public List<Pet> getListOfPets(ShelterType shelterType, PetState state, int page) {
        PageRequest pageRequest = PageRequest.of(page, 1);
        String kindOfPet = shelterType.equals(ShelterType.DOG_SHELTER) ? "DOG" : "CAT";
        return petRepository.findAllByStateAndKindOfPetOrderByIdDesc(state.name(), kindOfPet, pageRequest);
    }

    public List<PetDtoOut> getPets(String shelterType, String state, Integer page) {

        if (shelterType.equals("all") && state.equals("all")) {
            return getAllPets(page);
        } else if (shelterType.equals("all")) {
            return getAllPetsByState(state, page);
        }
        ShelterType typeOfShelter = ShelterType.valueOf(shelterType.toUpperCase());

        return getAllPetsByShelterTypeAndState(typeOfShelter, state, page);
    }

    private List<PetDtoOut> getAllPets(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        List<Pet> pets = petRepository.findAll(pageRequest).getContent();
        return pets.stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<PetDtoOut> getAllPetsByState(String state, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        PetState petState = PetState.valueOf(state.toUpperCase());
        return petRepository.findAllByState(petState.name(), pageRequest).stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<PetDtoOut> getAllPetsByShelterTypeAndState(ShelterType shelterType, String state, Integer page) {
        String kindOfPet = ShelterType.valueOf(shelterType.name().toUpperCase()).equals(ShelterType.CAT_SHELTER) ? "CAT" : "DOG";
        PageRequest pageRequest = PageRequest.of(page, 10);

        if (state.equals("all")) {
            return petRepository.findAllByKindOfPet(kindOfPet, pageRequest).stream()
                    .map(petMapper::toDto)
                    .collect(Collectors.toList());
        }
        PetState petState = PetState.valueOf(state.toUpperCase());

        return petRepository.findAllByStateAndKindOfPetOrderByIdDesc(petState.name(), kindOfPet, pageRequest).stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }

    public byte[] getPhoto(Long id) throws IOException {
        Pet pet = getPet(id);
        return Files.readAllBytes(Path.of(pet.getPhotoPath()));
    }

    public void editPet(Long id, PetDtoEdit petDtoEdit, MultipartFile file) throws IOException {
        Pet pet = getPet(id);
        if (!(petDtoEdit.getName().isEmpty() || petDtoEdit.getName().isBlank())) {
            pet.setName(petDtoEdit.getName());
        }
        if (!(petDtoEdit.getAboutPet().isEmpty() || petDtoEdit.getAboutPet().isBlank())) {
            pet.setAboutPet(petDtoEdit.getAboutPet());
        }
        if (!file.isEmpty()) {
            uploadAvatar(pet, file);
        }
    }

    public void setPetState(Long id, PetState state) {
        Pet pet = getPet(id);
        pet.setState(state.name());
        petRepository.save(pet);
    }

    public void deletePet(Long id) {
        Pet pet = getPet(id);
        petRepository.delete(pet);
    }
}

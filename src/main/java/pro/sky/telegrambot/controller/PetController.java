package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.dto.PetDtoIn;
import pro.sky.telegrambot.dto.PetDtoOut;
import pro.sky.telegrambot.service.PetService;

import java.io.IOException;

@RestController
@RequestMapping("pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(
            summary = "Create pet",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pet created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Kind of pet isn't correct",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public PetDtoOut createPet(@RequestBody PetDtoIn petDtoIn) {
        return petService.createPet(petDtoIn);
    }

    @Operation(
            summary = "Upload a photo to pet's avatar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avatar uploaded",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetDtoOut.class)
                            )
                    )
            }
    )
    @PatchMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PetDtoOut uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile file) {
        try {
            return petService.uploadAvatar(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package pro.sky.telegrambot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.model.PetReport;
import pro.sky.telegrambot.service.PetReportService;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Component
public class UserPhotoMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPhotoMessageHandler.class);
    private final TelegramBot telegramBot;
    private final PetReportService petReportService;
    private final UserService userService;
    private final TelegramBotService telegramBotService;
    private final String reportPhotosDir;

    public UserPhotoMessageHandler(TelegramBot telegramBot,
                                   PetReportService petReportService,
                                   UserService userService,
                                   TelegramBotService telegramBotService,
                                   @Value("${path.to.folder.with.report.photos}") String reportPhotosDir) {
        this.telegramBot = telegramBot;
        this.petReportService = petReportService;
        this.userService = userService;
        this.telegramBotService = telegramBotService;
        this.reportPhotosDir = reportPhotosDir;
    }

    public void handlePhoto(Long userId, PhotoSize photoSize) {
        GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
        PetReport petReport = petReportService.getReportByUserIdAndState(userId, PetReportState.FILLING);

        try {
            String extension = StringUtils.getFilenameExtension(getFileResponse.file().filePath());
            byte[] data = telegramBot.getFileContent(getFileResponse.file());
            savePhotoToReport(userId, petReport, data, extension);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void savePhotoToReport(Long userId, PetReport petReport, byte[] data, String extension) throws IOException {
        Path filePath = Path.of(reportPhotosDir, petReport.getShelterType().toLowerCase(), petReport.hashCode() + "." + extension);
        Files.createDirectories(filePath.getParent());

        try (
                InputStream is = new ByteArrayInputStream(data);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            petReportService.setPhotoPath(petReport.getId(), filePath.toString());
            userService.setUserState(userId, UserState.FILL_OUT_THE_REPORT_DIET);
            telegramBotService.sendMessage(userId, "Опишите рацион животного");
        }
    }
}

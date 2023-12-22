package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public String getMessage(String title) {
        return messageRepository.findByTitle(title).getMessage();
    }

    public String getMessage(String title, ShelterType shelterType) {
        return messageRepository.findByTitleAndShelterType(title, shelterType.name()).getMessage();
    }
}

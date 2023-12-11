package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.enums.UserState;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            user = new User(userId, UserState.NEW_USER.name());
            userRepository.save(user);
        }
        return user;
    }

    public UserState getUserState(Long userId) {
        User user = getUser(userId);
        return UserState.valueOf(user.getState());
    }

    public void setUserName(Long userId, String name) {
        User user = getUser(userId);
        user.setName(name);
        userRepository.save(user);
    }

    public void setUserState(Long userId, UserState userState) {
        User user = getUser(userId);
        user.setState(userState.name());
        userRepository.save(user);
    }

    public void setSelectedShelter(Long userId, ShelterType shelterType) {
        User user = getUser(userId);
        user.setSelectedShelter(shelterType.name());
        userRepository.save(user);
    }
}

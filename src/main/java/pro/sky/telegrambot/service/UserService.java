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

    public void createUser(Long userId, String userName) {
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setState(UserState.NEW_USER.name());
        userRepository.save(user);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserState getUserState(Long userId) {
        User user = getUser(userId);
        if (user == null) {
            return null;
        }
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

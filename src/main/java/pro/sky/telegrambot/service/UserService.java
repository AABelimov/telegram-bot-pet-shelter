package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            return UserState.START;
        }
        return UserState.valueOf(user.getState());
    }

    @Transactional
    public void setUserState(Long userId, UserState userState) {
        User user = getUser(userId);
        user.setState(userState.name());
        userRepository.save(user);
    }

    @Transactional
    public void setSelectedShelter(Long userId, ShelterType shelterType) {
        User user = getUser(userId);
        user.setSelectedShelter(shelterType.name());
        userRepository.save(user);
    }

    public void startConversation(Long userId) {
        setUserState(userId, UserState.CONVERSATION);
    }

    public void stopConversation(Long userId) {
        setUserState(userId, UserState.MAIN_MENU);
    }

    public String getSelectedShelter(Long userId) {
        User user = getUser(userId);
        return user.getSelectedShelter();
    }

    @Transactional
    public void setPhoneNumber(Long userId, String phoneNumber) {
        User user = getUser(userId);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }
}

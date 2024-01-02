package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enums.VolunteerState;
import pro.sky.telegrambot.model.User;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer getVolunteer(Long id) {
        return volunteerRepository.findById(id).orElse(null);
    }

    public Volunteer getFreeVolunteer() {
        return volunteerRepository.findByUserIdAndState(null, VolunteerState.AT_WORK.name()); // TODO: Доделать
    }

    public void setVolunteerState(Long volunteerId, VolunteerState volunteerState) {
        Volunteer volunteer = getVolunteer(volunteerId);
        volunteer.setState(volunteerState.name());
        volunteerRepository.save(volunteer);
    }

    public void startConversation(Volunteer volunteer, User user) {
        volunteer.setUser(user);
        volunteer.setState(VolunteerState.CONVERSATION.name());
        volunteerRepository.save(volunteer);
    }

    public void stopConversation(Long volunteerId) {
        Volunteer volunteer = getVolunteer(volunteerId);
        volunteer.setUser(null);
        volunteer.setState(VolunteerState.AT_WORK.name());
        volunteerRepository.save(volunteer);
    }

    public Volunteer getVolunteerByUserId(Long userId) {
        return volunteerRepository.findByUserId(userId);
    }

    public VolunteerState getVolunteerState(Long volunteerId) {
        Volunteer volunteer = getVolunteer(volunteerId);
        return VolunteerState.valueOf(volunteer.getState());
    }

    public Long getUserIdByVolunteerId(Long volunteerId) {
        Volunteer volunteer = getVolunteer(volunteerId);
        User user = volunteer.getUser();
        return user.getId();
    }
}

package pro.sky.telegrambot.dto;

import java.util.Objects;

public class UserDtoOut {

    private Long id;
    private String name;
    private String phoneNumber;

    public UserDtoOut() {

    }

    public UserDtoOut(Long id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDtoOut that = (UserDtoOut) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber);
    }
}

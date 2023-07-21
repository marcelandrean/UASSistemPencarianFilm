package model;

import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private int gender;
    private LocalDate birthday;
    private String photo;

    public User() {
    }

    public User(int id, String name, String email, String password, int gender, LocalDate birthday, String photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public String getGenderAsString() {
        if (gender == GenderInterface.PRIA) {
            return "Pria";
        } else {
            return "Wanita";
        }
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
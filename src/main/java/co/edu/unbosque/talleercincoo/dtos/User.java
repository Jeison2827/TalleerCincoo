package co.edu.unbosque.talleercincoo.dtos;

import com.opencsv.bean.CsvBindByName;

public class User {

    @CsvBindByName
    private String username;

    @CsvBindByName
    private String email;

    @CsvBindByName
    private String password;

    @CsvBindByName
    private String role;

    public User() {
    }

    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email= email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

package ku.cs.models;

import javafx.scene.image.Image;

public class User {
    private String username;
    private String password;
    private String role; // 3 roles { student, teacher, admin }
    private Image profileImage;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

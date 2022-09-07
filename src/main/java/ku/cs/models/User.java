package ku.cs.models;

import javafx.scene.image.Image;

import java.util.UUID;

public class User {
    private final String id; // a random unique id generate with UUID
    private String username;
    private String name;
    private String password;
    private final String role; // 3 roles { student, teacher, admin }
    private Agency agency;
    private String theme;
    private String font;
    private int fontSize;
    private Image profileImage;
    private String status; // online , offline
    private boolean isSuspend;

    public User(String id, String username, String name, String password, String role, Agency agency, String theme, String font,
                int fontSize, Image profileImage, String status, boolean isSuspend) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
        this.agency = agency;
        this.status = status;
        this.profileImage = profileImage;
        this.theme = theme;
        this.font = font;
        this.fontSize = fontSize;
        this.isSuspend = isSuspend;
    }

    public User(String username, String name, String password, String role, Agency agency) {
        this(UUID.randomUUID().toString(), username, name, password, role, agency, "dark", "Kanit",
                16, null, "online", false);
    }

    public User(String username, String name, String password, String role) {
        this(UUID.randomUUID().toString(), username, name, password, role, null, "dark", "Kanit",
                16, null, "online", false);
    }

    public User(String username, String name, String password) {
        this(UUID.randomUUID().toString(), username, name, password, "student", null, "dark", "Kanit",
                16, null, "online", false);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() { return name; }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Agency getAgency() {
        return agency;
    }

    public String getTheme() {
        return theme;
    }

    public String getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public boolean isSuspend() {
        return isSuspend;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSuspend(boolean isSuspend) {
        this.isSuspend = isSuspend;
    }

    public String[] toStringArray() {
        return new String[] {
                id,
                username,
                name,
                password,
                role,
                (agency != null ?  agency.getId() : "null"),
                theme,
                font,
                Integer.toString(fontSize),
                "null",
                status,
                Boolean.toString(isSuspend),
        };
    }
}

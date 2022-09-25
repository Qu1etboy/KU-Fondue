package ku.cs.models;

import javafx.scene.image.Image;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class User {
    private final String id; // a random unique id generate with UUID
    private String username;
    private String name;
    private String password;
    private Role role; // 3 roles { student, teacher, admin }
    private Agency agency;
    private String theme;
    private String font;
    private int fontSize;
    private Image profileImage;
    private String status; // online , offline
    private boolean isSuspend;
    private Date lastOnline;

    public User(String id, String username, String name, String password, Role role, Agency agency, String theme, String font,
                int fontSize, Image profileImage, String status, boolean isSuspend, Date lastOnline) {
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
        this.lastOnline = lastOnline;
    }

    public User(String username, String name, String password, Role role, Agency agency) {
        this(UUID.randomUUID().toString(), username, name, password, role, agency, "dark", "Kanit",
                16, null, "online", false, new Date());
    }

    public User(String username, String name, String password, Role role) {
        this(UUID.randomUUID().toString(), username, name, password, role, null, "dark", "Kanit",
                16, null, "online", false, new Date());
    }

    public User(String username, String name, String password) {
        this(UUID.randomUUID().toString(), username, name, password, Role.STUDENT, null, "dark", "Kanit",
                16, null, "online", false, new Date());
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

    public Role getRole() {
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

    public String getStatus() {
        return status;
    }

    public boolean isSuspend() {
        return isSuspend;
    }
    public String getLastOnline() {
        long diff = new Date().getTime() - lastOnline.getTime();
        return TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS) + " sec";
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
    public void setLastOnline(Date date) {
        this.lastOnline = date;
    }


    public void setAgency(Agency agency) {
        this.agency = agency;
    }
    public String[] toStringArray() {
        return new String[] {
                id,
                username,
                name,
                password,
                role.toString(),
                (agency != null ?  agency.getId() : "null"),
                theme,
                font,
                Integer.toString(fontSize),
                "null",
                status,
                Boolean.toString(isSuspend),
                new SimpleDateFormat().format(lastOnline)
        };
    }
}

package ku.cs.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.UUID;

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

    private ImageView profileImageView;
    private String status; // online , offline
    private boolean isSuspend;
    private LocalDateTime loginTime;

    public User(String id, String username, String name, String password, Role role, Agency agency, String theme, String font,
                int fontSize, Image profileImage, String status, boolean isSuspend, LocalDateTime loginTime) {
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
        this.loginTime = loginTime;
    }

    public User(String username, String name, String password, Role role, Agency agency) {
        this(UUID.randomUUID().toString(), username, name, password, role, agency, "dark", "Kanit",
                16, new Image("file:images/default.png"), "online", false, LocalDateTime.now());
    }

    public User(String username, String name, String password, Role role) {
        this(UUID.randomUUID().toString(), username, name, password, role, null, "dark", "Kanit",
                16, new Image("file:images/default.png"), "online", false, LocalDateTime.now());
    }

    public User(String username, String name, String password) {
        this(UUID.randomUUID().toString(), username, name, password, Role.STUDENT, null, "dark", "Kanit",
                16, new Image("file:images/default.png"), "online", false, LocalDateTime.now());
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

    public ImageView getProfileImageView() {
        Circle circle = new Circle(50, 50, 50);
        ImageView imageView = new ImageView(profileImage);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setClip(circle);

        return imageView;
    }

    public boolean isSuspend() {
        return isSuspend;
    }
    public String getLoginTime() {
        return loginTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT));
//        long diff = new Date().getTime() - lastOnline.getTime();
//        return TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS) + " sec";
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

    public LocalDateTime getDate() {
        return loginTime;
    }
    public void setLoginTime(LocalDateTime date) {
        this.loginTime = date;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image image) {
        this.profileImage = image;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
    public String[] toStringArray() {
        String[] fileSplit = profileImage.getUrl().split("/");
        String imagePath = fileSplit[fileSplit.length - 1];

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
                imagePath,
                status,
                Boolean.toString(isSuspend),
                loginTime.toString()
        };
    }

    public boolean isRole(Role role) {
        return this.role == role;
    }

    public boolean checkUsername(String username) {
        return this.username.equals(username);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof User)) {
            return false;
        }

        User u = (User) obj;
        return id.equals(u.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role=" + role +
                ", agency=" + agency +
                ", isSuspend=" + isSuspend +
                '}';
    }
}

package ku.cs.models;

public class SuspendUser {

    private User user;
    private String reason;
    private int loginCount;

    private boolean isRequest;

    public SuspendUser(User user, String reason, int loginCount, boolean isRequest) {
        this.user = user;
        this.reason = reason;
        this.loginCount = loginCount;
        this.isRequest = isRequest;
    }

    public SuspendUser(User user) {
        this.user = user;
        reason = "";
        loginCount = 0;
        isRequest = false;
    }

    public User getUser() {
        return user;
    }

    public String getReason() {
        return reason;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setRequest(boolean isRequest) {
        this.isRequest = isRequest;
    }

    public void incrementLoginCount() {
        loginCount++;
    }

    public String[] toStringArray() {
        return new String[] {
                user.getId(),
                reason,
                Integer.toString(loginCount),
                Boolean.toString(isRequest)
        };
    }

    @Override
    public String toString() {
        return "SuspendUser{" +
                "user=" + user +
                ", reason='" + reason + '\'' +
                ", loginCount=" + loginCount +
                ", isRequest=" + isRequest +
                '}';
    }
}

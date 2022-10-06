package ku.cs.models;

import java.util.UUID;

public class Report {
    private String id;
    private User user;
    private Complaint complaint;
    private String type;
    private String detail;

    public Report(String id, User user, Complaint complaint, String type, String detail) {
        this.id = id;
        this.user = user;
        this.complaint = complaint;
        this.type = type;
        this.detail = detail;
    }

    public Report(User user, String type, String detail) {
        id = UUID.randomUUID().toString();
        this.user = user;
        this.type = type;
        this.detail = detail;
        complaint = null;
    }

    public Report(Complaint complaint, String type, String detail) {
        id = UUID.randomUUID().toString();
        this.complaint = complaint;
        this.type = type;
        this.detail = detail;
        user = null;
    }

    public String getId() {
        return id;
    }


    public User getUser() {
        return user;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public String getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }

    public String[] toStringArray() {
        return new String[] {
                id,
                user == null ? "null" : user.getId(),
                complaint == null ? "null" : complaint.getId(),
                type,
                detail
        };
    }
}

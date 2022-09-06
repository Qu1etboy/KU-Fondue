package ku.cs.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Complaint {
    private String id;
    private User user;
    private String topic;
    private String detail;
    private ComplaintCategory complaintCategory;

    private Map<String,String> additionalDetail;
    private int vote;
    private List<User> userVote;

    public Complaint(String id,User user,String topic, String detail, ComplaintCategory complaintCategory, int vote, List<User> userVote) {
        this.id = id;
        this.user = user;
        this.topic = topic;
        this.detail = detail;
        this.complaintCategory = complaintCategory;
        this.vote = vote;
        this.userVote = userVote;

        // setAdditionalDetail();

    }

//    public void setAdditionalDetail () {
//        additionalDetail = new HashMap<>();
//        for( String question : complaintCategory.getInputData()){
//            additionalDetail.put(question, "");
//        }
//    }

    public void addAdditionalDetail(String question, String answer) {
        additionalDetail.replace(question, answer);
    }

    // Constructor
    public Complaint(String topic, String detail) {
        this(UUID.randomUUID().toString(),null,topic,detail,null,0,null);
    }

    public Complaint(String topic, String detail, ComplaintCategory complaintCategory){
        this(UUID.randomUUID().toString(),null,topic,detail,complaintCategory,0,null);
    }

    public Complaint(User user, String topic, String detail, ComplaintCategory complaintCategory) {
        this(UUID.randomUUID().toString(),user,topic,detail,complaintCategory,0,null);
    }

    public Complaint(User user, String topic, String detail) {
        this(UUID.randomUUID().toString(),user,topic,detail,null,0,null);
    }



    public String[] toStringArray(){
        return new String[]{user.getUsername(),topic,detail};
    }

    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getDetail() {
        return detail;
    }

    public ComplaintCategory getComplaintCategory() {
        return complaintCategory;
    }

    public int getVote() {
        return vote;
    }

    public List<User> getUserVote() {
        return userVote;
    }
}

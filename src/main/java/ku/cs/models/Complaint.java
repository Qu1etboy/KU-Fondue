package ku.cs.models;

import java.util.*;

public class Complaint {
    private String id;
    private User user;
    private String topic;
    private String detail;
    private ComplaintCategory complaintCategory;
    private String status;
    private Date date;
    private String answerTeacher;
    private Map<String,String> additionalDetail;
    private int vote;
    private List<User> userVote;

    public Complaint(String id,User user,String topic, String detail, ComplaintCategory complaintCategory, String status , Date date , String answerTeacher,int vote, List<User> userVote) {
        this.id = id;
        this.user = user;
        this.topic = topic;
        this.detail = detail;
        this.complaintCategory = complaintCategory;
        this.status = status;
        this.date = date;
        this.answerTeacher = answerTeacher;
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
    public Complaint(String topic, String detail, String status,Date date) {
        this(UUID.randomUUID().toString(),null,topic,detail,null,status,date,"",0,null);
    }

    public Complaint(String topic, String detail, ComplaintCategory complaintCategory,String status,Date date){
        this(UUID.randomUUID().toString(),null,topic,detail,complaintCategory,status,date,"",0,null);
    }

    public Complaint(User user, String topic, String detail, ComplaintCategory complaintCategory,String status,Date date) {
        this(UUID.randomUUID().toString(),user,topic,detail,complaintCategory,status,date,"",0,null);
    }

    public Complaint(User user, String topic, String detail,String status,Date date) {
        this(UUID.randomUUID().toString(),user,topic,detail,null,status,date,"",0,null);
    }

    public Complaint(User user, String topic, String detail) {
        this(UUID.randomUUID().toString(),user,topic,detail,null,"default",new Date(),"",0,null);
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

    public String getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }

    public String getAnswerTeacher() {
        return answerTeacher;
    }
}

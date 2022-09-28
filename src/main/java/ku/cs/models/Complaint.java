package ku.cs.models;

import javafx.scene.image.Image;
import ku.cs.datastructure.ListMap;

import java.text.SimpleDateFormat;
import java.util.*;

public class Complaint {
    private String id;
    private User user;
    private String topic;
    private String detail;
    private String complaintCategoryName;
    private ComplaintCategory complaintCategory;
    private String status;
    private Date date;
    private String answerTeacher;
    private ListMap<String,String> additionalDetail;
    private int vote;
    private List<User> userVote;
    private List<Image> imagesAnswer;

    public Complaint(String id, User user, String topic, String detail, String complaintCategoryName, String status, Date date, String answerTeacher, int vote, List<User> userVote) {
        this.id = id;
        this.user = user;
        this.topic = topic;
        this.detail = detail;
        this.complaintCategoryName = complaintCategoryName;
        this.status = status;
        this.date = date;
        this.answerTeacher = answerTeacher;
        this.vote = vote;
        if (userVote == null) {
            this.userVote = new ArrayList<>();
        } else {
            this.userVote = userVote;
        }
        if(imagesAnswer == null){
            this.imagesAnswer= new ArrayList<>();
        }else{
            this.imagesAnswer = imagesAnswer;
        }
        additionalDetail = new ListMap<>();

    }

    public void addQuestionAnswer(String q,String a){
        additionalDetail.put(q,a);
    }
    // Constructor
    public Complaint(User user, String topic, String detail, String complaintCategoryName, String status,Date date) {
        this(UUID.randomUUID().toString(),user,topic,detail,complaintCategoryName,status,date,"",0,new ArrayList<>());
    }

//    public Complaint(User user, String topic, String detail) {
//        this(UUID.randomUUID().toString(),user,topic,detail,"","new",new Date(),"",0,new ArrayList<>());
//    }

    public Complaint(User user, String topic, String detail,String complaintCategoryName) {
        this(UUID.randomUUID().toString(),user,topic,detail,complaintCategoryName,"new",new Date(),"",0,new ArrayList<>());
    }

    public String[] toStringArray(){
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        for (String question : additionalDetail.keyList()) {
            questions.add(question);
            answers.add(additionalDetail.get(question));
        }

        List<String> usersId = new ArrayList<>();
        for (User user : userVote) {
            usersId.add(user.getId());
        }

        return new String[]{
                id,
                user.getId(),
                topic,
                detail,
                status,
                complaintCategoryName,
                String.join(",", questions),
                String.join(",", answers),
                new SimpleDateFormat().format(date),
                answerTeacher,
                Integer.toString(vote),
                String.join(",", usersId),
                "",
        };
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

    public String getComplaintCategoryName() {
        return complaintCategoryName;
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

    private void setStatus(String status) {
        this.status = status;
    }

    public void done() {
        setStatus("done");
    }

    public void inProgress(){
        setStatus("In Progress");
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Image> getImagesAnswer() {
        return imagesAnswer;
    }
}

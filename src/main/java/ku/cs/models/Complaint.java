package ku.cs.models;

import javafx.scene.image.Image;
import ku.cs.datastructure.ListMap;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Complaint {
    private String id;
    private User user;
    private String topic;
    private String detail;
    private String complaintCategoryName;
    private ComplaintCategory complaintCategory;
    private String status;
    private LocalDateTime date;
    private String answerTeacher;
    private ListMap<String,String> additionalDetail;
    private int vote;
    private List<User> userVote;
    private List<Image> imagesAnswer;
    private User teacher;

    public Complaint(String id, User user, String topic, String detail, String complaintCategoryName, String status, LocalDateTime date, String answerTeacher, int vote, List<User> userVote, User teacher, List<Image> imagesAnswer) {
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
        this.teacher = teacher;

    }

    public Complaint(User user, String topic, String detail,String complaintCategoryName) {
        this(UUID.randomUUID().toString(),user,topic,detail,complaintCategoryName,"report", LocalDateTime.now(),"",0,new ArrayList<>(), null, new ArrayList<>());
    }
    public void addQuestionAnswer(String q,String a){
        additionalDetail.put(q,a);
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
        List<String> imagePath = new ArrayList<>();
        for (Image image : imagesAnswer) {
            String[] fileSplit = image.getUrl().split("/");
            imagePath.add(fileSplit[fileSplit.length - 1]);
        }

        return new String[]{
                id,
                user.getId(),
                topic,
                detail,
                complaintCategoryName,
                status,
                String.join(",", questions),
                String.join(",", answers),
                date.toString(),
                answerTeacher,
                Integer.toString(vote),
                String.join(",", usersId),
                "",
                (teacher == null ? "null" : teacher.getId()),
                String.join(",", imagePath),
        };
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
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

    public ListMap<String, String> getAdditionalDetail() {
        return additionalDetail;
    }
    public int getVote() {
        return vote;
    }

    public List<User> getUserVote() {
        return userVote;
    }

    public String getStatus() {
        if (status.equals("report")) {
            return "รอรับเรื่อง";
        } else if (status.equals("In Progress")) {
            return "รอดําเนินการ";
        }

        return "เสร็จสิ้น";
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getSimpleDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
        return date.format(formatter);
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

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Image> getImagesAnswer() {
        return imagesAnswer;
    }

    public void addImageAnswer(Image image) {
        imagesAnswer.add(image);
    }

    public void setAnswerTeacher(String answerTeacher) {
        this.answerTeacher = answerTeacher;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
    public boolean addUserVote(User user) {
        boolean isAdd = false;
        if (userVote.contains(user)) {
            userVote.remove(user);

        } else {
            userVote.add(user);
            isAdd = true;
        }
        vote = userVote.size();
        return isAdd;
    }

    public boolean containUserVote(User user) {
        return userVote.contains(user);
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "user=" + user.getName() +
                ", topic='" + topic + '\'' +
                ", detail='" + detail + '\'' +
                ", complaintCategoryName='" + complaintCategoryName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof Complaint)) {
            return false;
        }

        Complaint c = (Complaint) obj;
        return id.equals(c.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

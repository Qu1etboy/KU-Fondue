package ku.cs.datastructure;

import ku.cs.models.Complaint;
import ku.cs.models.ComplaintList;
import ku.cs.models.User;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;

public class TestListMap {
    public static void main(String[] args) {
//        ListMap<String, String> qanda = new ListMap<>();
//        String question, answer;
//        question = "สถานที่";
//        answer = "ศ.ร.1";
//        qanda.put(question, answer);
//        qanda.put("ชั้น", "1");
//        qanda.put("ห้อง", "125");
//
//        for (String q : qanda.keyList()) {
//            System.out.println(q + " => " + qanda.get(q));
//        }
//
//        ComplaintList complaintList = new ComplaintList();
//            Complaint complaint = new Complaint( new User("1", "username", "password"), "topic", "detail");
//            for (String q : qanda.keyList()) {
//                complaint.addQuestionAnswer(q, qanda.get(q));
//            }
//            complaint.done();
//        complaintList.addComplaint(complaint);
//
//        System.out.println(new Date());




        SimpleDateFormat sdf = new SimpleDateFormat();
        Calendar calendar = Calendar.getInstance();
        System.out.println(sdf.format(calendar.getTime()));

//        LocalDateTime date = LocalDateTime.now();

//
//        DateTimeFormatter formatter =
//                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
//        LocalDateTime date2 = LocalDateTime.parse("ก.ย. 29, 2022. 6:36 หลังเที่ยง", formatter);
//
//
//        System.out.println(date);
//        System.out.println(formatter);
//        System.out.println(date2);
//        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);

        Date date = new  Date();
        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy HH:mm:ss");
        System.out.println(formatter.format(date));


    }

}

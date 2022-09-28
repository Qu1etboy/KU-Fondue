package ku.cs.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorter {
    private List<String> sortList;

    public Sorter() {
        sortList = new ArrayList<>();
        addSortList();
    }

    public void addSortList() {
        sortList.add("ล่าสุด");
        sortList.add("เก่าสุด");
        sortList.add("โหวตมากสุด");
        sortList.add("โหวตน้อยสุด");
    }

    public List<String> getAllTSortList() {
        return sortList;
    }

    // sort complaint list by date
//    public void sort(List<Complaint> complaintList, String sortType) {
//        if (sortType.equals("ล่าสุด")) {
//            complaintList.sort(new DateComparator());
//        } else if (sortType.equals("เก่าสุด")) {
//            complaintList.sort(new DateComparator().reversed());
//        } else if (sortType.equals("โหวตมากสุด")) {
//            complaintList.sort(new VoteComparator());
//        } else if (sortType.equals("โหวตน้อยสุด")) {
//            complaintList.sort(new VoteComparator().reversed());
//        }
//    }

    public void sortByMost(ComplaintList complaintList, Comparator comparator)  {
        for (int i = 0; i < complaintList.getComplaintList().size(); i++) {// i แบ่งส่วนเรียงแล้วกับยังไม่เรียง
            int minPos = i;
            for (int j = i + 1; j < complaintList.getComplaintList().size(); j++) { // วนลูปหาค่าน้อยสุด
                if (comparator.compare(complaintList.getComplaintList().get(j), complaintList.getComplaintList().get(minPos)) < 0) {
                    minPos = j;
                }
            }
// สลับข้อมูลใน minPos และ i ทําให้ข้อมูลใน minPos ไปอยู่ส่วนที่เรียงแล้ว
            Complaint temp = complaintList.getComplaintList().get(i);
            complaintList.getComplaintList().set(i, complaintList.getComplaintList().get(minPos));
            complaintList.getComplaintList().set(minPos, temp);
        }
    }

    public void sortByLow(ComplaintList complaintList, Comparator comparator)  {
        for (int i = 0; i < complaintList.getComplaintList().size(); i++) {// i แบ่งส่วนเรียงแล้วกับยังไม่เรียง
            int maxPos = i;
            for (int j = i + 1; j < complaintList.getComplaintList().size(); j++) { // วนลูปหาค่ามากสุด
                if (comparator.compare(complaintList.getComplaintList().get(j), complaintList.getComplaintList().get(maxPos)) > 0) {
                    maxPos = j;
                }
            }
// สลับข้อมูลใน maxPos และ i ทําให้ข้อมูลใน maxPos ไปอยู่ส่วนที่เรียงแล้ว
            Complaint temp = complaintList.getComplaintList().get(i);
            complaintList.getComplaintList().set(i, complaintList.getComplaintList().get(maxPos));
            complaintList.getComplaintList().set(maxPos, temp);
        }
    }


    public Comparator<Complaint> getDateComparator() {
        return new DateComparator();
    }
}

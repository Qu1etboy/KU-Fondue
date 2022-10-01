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
        for (int i = 0; i < complaintList.getComplaintList().size(); i++) {
            int minimum = i;
            for (int j = i + 1; j < complaintList.getComplaintList().size(); j++) {
                if (comparator.compare(complaintList.getComplaintList().get(j), complaintList.getComplaintList().get(minimum)) < 0) {
                    minimum = j;
                }
            }
            Complaint temp = complaintList.getComplaintList().get(i);
            complaintList.getComplaintList().set(i, complaintList.getComplaintList().get(minimum));
            complaintList.getComplaintList().set(minimum, temp);
        }
    }

    public void sortByLow(UserList userList, Comparator comparator)  {
        for (int i = 0; i < userList.getUserList().size(); i++) {
            int minimum = i;
            for (int j = i + 1; j < userList.getUserList().size(); j++) {
                if (comparator.compare(userList.getUserList().get(j), userList.getUserList().get(minimum)) > 0) {
                    minimum = j;
                }
            }
            User temp = userList.getUserList().get(i);
            userList.getUserList().set(i, userList.getUserList().get(minimum));
            userList.getUserList().set(minimum, temp);
        }
    }

    public void sortByLow(ComplaintList complaintList, Comparator comparator)  {
        for (int i = 0; i < complaintList.getComplaintList().size(); i++) {
            int maximum = i;
            for (int j = i + 1; j < complaintList.getComplaintList().size(); j++) {
                if (comparator.compare(complaintList.getComplaintList().get(j), complaintList.getComplaintList().get(maximum)) > 0) {
                    maximum = j;
                }
            }
            Complaint temp = complaintList.getComplaintList().get(i);
            complaintList.getComplaintList().set(i, complaintList.getComplaintList().get(maximum));
            complaintList.getComplaintList().set(maximum, temp);
        }
    }
}

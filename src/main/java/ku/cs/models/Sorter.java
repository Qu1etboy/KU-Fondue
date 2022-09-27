package ku.cs.models;

import java.util.ArrayList;
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
    public void sort(List<Complaint> complaintList, String sortType) {
        if (sortType.equals("ล่าสุด")) {
            complaintList.sort(new DateComparator());
        } else if (sortType.equals("เก่าสุด")) {
            complaintList.sort(new DateComparator().reversed());
        } else if (sortType.equals("โหวตมากสุด")) {
            complaintList.sort(new VoteComparator());
        } else if (sortType.equals("โหวตน้อยสุด")) {
            complaintList.sort(new VoteComparator().reversed());
        }
    }
    
}

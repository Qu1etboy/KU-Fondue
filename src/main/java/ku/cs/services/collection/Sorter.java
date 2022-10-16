package ku.cs.services.collection;

import ku.cs.models.Complaint;
import ku.cs.models.ComplaintList;
import ku.cs.models.User;
import ku.cs.models.UserList;

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

    public void sortByMost(List list, Comparator comparator)  {
        for (int i = 0; i < list.size(); i++) {
            int minimum = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (comparator.compare(list.get(j), list.get(minimum)) < 0) {
                    minimum = j;
                }
            }
            Object temp = list.get(i);
            list.set(i, list.get(minimum));
            list.set(minimum, temp);
        }
    }

    public void sortByLow(List list, Comparator comparator)  {
        for (int i = 0; i < list.size(); i++) {
            int minimum = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (comparator.compare(list.get(j), list.get(minimum)) > 0) {
                    minimum = j;
                }
            }
            Object temp = list.get(i);
            list.set(i, list.get(minimum));
            list.set(minimum, temp);
        }
    }

}

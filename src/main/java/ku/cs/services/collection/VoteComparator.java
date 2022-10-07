package ku.cs.services.collection;

import ku.cs.models.Complaint;

import java.util.Comparator;

public class VoteComparator implements Comparator<Complaint> {
    @Override
    public int compare(Complaint o1, Complaint o2) {
        return o2.getVote() - o1.getVote();
    }
}

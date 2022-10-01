package ku.cs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SuspendUserList {

    private List<SuspendUser> suspendUserList;

    public SuspendUserList() {
        suspendUserList = new ArrayList<>();
    }

    public void addSuspendUser(SuspendUser suspendUser) {
        suspendUserList.add(suspendUser);
    }

    public SuspendUser findSuspendUser(User user) {
        for (SuspendUser suspendUser : suspendUserList) {
            if (suspendUser.getUser().getId().equals(user.getId())) {
                return suspendUser;
            }
        }
        return null;
    }

    public void removeSuspendUser(User user) {
        suspendUserList = suspendUserList
                .stream()
                .filter(su -> !su.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    public void updateSuspendUser(SuspendUser suspendUser) {
        suspendUserList = suspendUserList
                .stream()
                .map(su -> su.getUser().getId().equals(suspendUser.getUser().getId()) ? suspendUser : su)
                .collect(Collectors.toList());
    }

    public SuspendUserList filterBy(Filterer<SuspendUser> filterer) {
        SuspendUserList filteredSuspendUserList = new SuspendUserList();
        for (SuspendUser suspendUser : suspendUserList) {
            if (filterer.filter(suspendUser)) {
                filteredSuspendUserList.addSuspendUser(suspendUser);
            }
        }
        return filteredSuspendUserList;
    }

    public void incrementSuspendUserLoginCount(User user) {
        SuspendUser suspendUser = findSuspendUser(user);
        suspendUser.incrementLoginCount();
    }

    public List<SuspendUser> getSuspendUserList() {
        return suspendUserList;
    }
}

package ku.cs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AgencyList {
    private List<Agency> agencyList;

    public AgencyList() {
        agencyList = new ArrayList<>();
    }

    public void addAgency(Agency agency) {
        agencyList.add(agency);
    }

    public Agency findAgencyById(String id) {
        for (Agency agency : agencyList) {
            if (agency.getId().equals(id)) {
                return agency;
            }
        }
        return null;
    }

    public void removeAgency(Agency agency) {
        agencyList = agencyList
                .stream()
                .filter(a -> !a.getId().equals(agency.getId()))
                .collect(Collectors.toList());
    }

    public List<Agency> getAgencyList() {
        return agencyList;
    }
}

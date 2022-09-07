package ku.cs.models;

import java.util.ArrayList;
import java.util.List;

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

    public List<Agency> getAgencyList() {
        return agencyList;
    }
}

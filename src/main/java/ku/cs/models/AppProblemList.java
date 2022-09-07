package ku.cs.models;

import java.util.ArrayList;
import java.util.List;

public class AppProblemList {

    private List<AppProblem> appProblemList;

    public AppProblemList() {
        appProblemList = new ArrayList<>();
    }

    public List<AppProblem> getAppProblemList() { return appProblemList;
    }

    public void addAppProblem(AppProblem appProblem){ appProblemList.add(appProblem);
    }
}

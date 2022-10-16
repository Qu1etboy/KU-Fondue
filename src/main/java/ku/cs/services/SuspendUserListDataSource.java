package ku.cs.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import ku.cs.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SuspendUserListDataSource implements DataSource<SuspendUserList> {
    private String directoryName;
    private String fileName;

    public SuspendUserListDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    public void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public SuspendUserList readData() {
        SuspendUserList suspendUserList = new SuspendUserList();
        String filePath = directoryName + File.separator + fileName;

        try {
            CSVReader reader = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8));
            List<String[]> allData = reader.readAll();

            DataSource<UserList> userData = new UserListDataSource("data", "user.csv");
            UserList userList = userData.readData();

            for (String[] data : allData) {
                if (data.length == 1 && data[0].isEmpty()) {
                    continue;
                }

                User user = userList.findUserById(data[0]);

                suspendUserList.addSuspendUser(
                        new SuspendUser(user, data[1], Integer.parseInt(data[2]), Boolean.parseBoolean(data[3]))
                );

            }

        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return suspendUserList;
    }

    @Override
    public void writeData(SuspendUserList suspendUserList) {
        String filePath = directoryName + File.separator + fileName;
        CSVWriter writer = null;
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer = new CSVWriter(osw);

            for (SuspendUser suspendUser : suspendUserList.getSuspendUserList()) {
                String[] row = suspendUser.toStringArray();
                writer.writeNext(row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

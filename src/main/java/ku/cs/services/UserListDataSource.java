package ku.cs.services;

import com.opencsv.*;

import com.opencsv.exceptions.CsvException;
import ku.cs.models.User;
import ku.cs.models.UserList;

import java.io.*;
import java.util.List;

public class UserListDataSource implements DataSource<UserList> {
    // read and write csv file using opencsv library
    String directoryName;
    String fileName;

    public UserListDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    /**
     * Check if file is existed. if not create it
     */
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
    public UserList readData() {
        UserList userList = new UserList();
        String filePath = directoryName + File.separator + fileName;
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));

            List<String[]> allData = reader.readAll();
            for (String[] data : allData) {
                userList.addUser(new User(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5],
                        data[6],
                        Integer.parseInt(data[7]),
                        null,
                        data[9],
                        Boolean.parseBoolean(data[10]))
                );
            }

        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    @Override
    public void writeData(UserList userList) {
        String filePath = directoryName + File.separator + fileName;
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(filePath));
            for (User user : userList.getUserList()) {
                String[] row = user.toStringArray();
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

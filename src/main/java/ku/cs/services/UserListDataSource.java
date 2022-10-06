package ku.cs.services;

import com.opencsv.*;

import com.opencsv.exceptions.CsvException;
import javafx.scene.image.Image;
import ku.cs.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class UserListDataSource implements DataSource<UserList> {
    // read and write csv file using opencsv library
    private String directoryName;
    private String fileName;

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
            CSVReader reader = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8));

            List<String[]> allData = reader.readAll();
            DataSource<AgencyList> agencyData = new AgencyListDataSource("data", "agency.csv");
            AgencyList agencyList = agencyData.readData();

            for (String[] data : allData) {
                Agency agency = agencyList.findAgencyById(data[5]);
//                SimpleDateFormat formatter = new SimpleDateFormat();
                LocalDateTime lastOnline = LocalDateTime.parse(data[12]);
                userList.addUser(new User(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        Role.valueOf(data[4]),
                        agency,
                        data[6],
                        data[7],
                        Integer.parseInt(data[8]),
                        new Image("file:images/" + data[9]),
                        data[10],
                        Boolean.parseBoolean(data[11]),
                        lastOnline
                        )
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
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer = new CSVWriter(osw);

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

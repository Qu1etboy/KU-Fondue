package ku.cs;

import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.File;
import java.net.URISyntaxException;

public class TestDataSource {
    public static void main(String[] args) {
//        DataSource<UserList> data = new UserListDataSource("data", "user.csv");
//        UserList userList = data.readData();
//
//        for (User user : userList.getUserList()) {
//            System.out.println(user.getId() + " " + user.getUsername() + " " + user.getPassword() + " " + user.getRole());
//        }

//        userList.updateUser(new User("non za", "newPassword", "student"));
//        data.writeData(userList);

        // Read all file in the directory
        File folder = null;
        try {
            folder = new File(TestDataSource.class.getResource("/ku/cs/css/themes").toURI());
            File[] files = folder.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.isFile()) {
                    // print file without fil extension
                    System.out.println("File: " + file.getName().substring(0, file.getName().length() - 4));
                }
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}

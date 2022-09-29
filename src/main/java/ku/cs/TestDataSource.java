package ku.cs;

import ku.cs.models.*;
import ku.cs.services.ComplaintCategoryListDataSource;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

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
//        File folder = null;
//        try {
//            folder = new File(TestDataSource.class.getResource("/ku/cs/css/themes").toURI());
//            File[] files = folder.listFiles();
//            if (files == null) {
//                return;
//            }
//            for (File file : files) {
//                if (file.isFile()) {
//                    // print file without fil extension
//                    System.out.println("File: " + file.getName().substring(0, file.getName().length() - 4));
//                }
//            }
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }

//        DataSource<ComplaintList> data = new ComplaintListDataSource("data", "complaint.csv");
//        ComplaintList complaintList = data.readData();
//
//        for (Complaint complaint : complaintList.getComplaintList()) {
//            System.out.println(complaint.getTopic() + " " + complaint.getDetail());
//        }
//        complaintList.addComplaint(new Complaint("123","456"));
//        data.writeData(complaintList);

//        DataSource<ComplaintCategoryList> data = new ComplaintCategoryListDataSource("data", "complaint_category.csv");
//        ComplaintCategoryList complaintCategoryList = data.readData();
//
//        DataSource<ComplaintList> complaintData = new ComplaintListDataSource("data", "complaint.csv");
//        ComplaintList complaintList = complaintData.readData();
//
//        for (Complaint complaint : complaintList.getComplaintList()) {
//            System.out.println(complaint.getComplaintCategoryName());
//        }
        // อาคารสถานที่ชำรุด
        // อาคารสถานที่ชํารุด
        String name = "อาคารสถานที่ชํารุด";
        System.out.println(name.equals("อาคารสถานที่ชํารุด"));
//        for (ComplaintCategory category : complaintCategoryList.getComplaintCategoryList()) {
//            System.out.println(category.getName() + " : " + complaintList.countCategory(category));
//        }


//        for (ComplaintCategory complaintCategory : complaintCategoryList.getComplaintCategoryList()) {
//            System.out.println(complaintCategory.getId() + " " + complaintCategory.getName());
//        }
//        complaintCategoryList.addComplaintCatergory(new ComplaintCategory("123","I","e,r,o"));
//        data.writeData(complaintCategoryList);

    }
}

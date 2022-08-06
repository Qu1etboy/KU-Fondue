# CS211 - Project (LoveAndJava)
## ความก้าวหน้าครั้งที่ 1

- ออกแบบโครงสร้างโปรเจค
- วางแผนว่าตัวโปรแกรมจะมีหน้าอะไรบ้าง
- ออกแบบ view สําหรับตัวโปรแกรม
- ออกแบบ controller ที่ใช้ควบคุมในส่วนของ view
- สร้าง class และคิด field กับ method แต่ยังไม่ implement หลักการใดๆ

## Project structure
*can be change in the future

```
src/main 
    ├── java
           ├── ku/cs
		├── controllers
		       ├── LoginDetailController.java
		       ├── RegisterDetailController.java
		       ├── MainApplicationDetailController.java
		       ├── HomeDetailController.java
		       ├── SettingDetailController.java
		       ├── DashboardDetailController.java
		├── models
		       ├── Login.java
		       ├── Register.java
		       ├── User.java
	   ├── module-info.java
     ├── resources/ku/cs
	   ├── view
		├── Login.fxml
		├── Register.fxml
		├── MainApplication.fxml
		├── Home.fxml
		├── About.fxml
		├── Help.fxml
		├── Setting.fxml
		├── StudentDashboard.fxml
		├── TeacherDashboard.fxml
		├── AdminDashboard.fxml
	   ├── css
		├── main.css
		├── dark.css
		├── light.css
	   ├── images
		├── profile.png
```
## Setup
**วิธีทดสอบการ RUN**
1. Main
   - `run Main Class`
2. javafx plugin
   - `MVN Clean`
   - `javafx -> javafx:run`

**วิธีสร้าง Jar**<br>
MVN Clean<br>
MVN install<br><br>
file จะอยู่ใน target เป็น shade.jar 




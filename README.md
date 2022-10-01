# CS211 - Project (LoveAndJava)
# KU-Complaint
โปรแกรมแจ้งเรื่องร้องเรียนภายในมหาวิทยาลัยเกษตรศาสตร์
## ความก้าวหน้าครั้งที่ 1

- ออกแบบโครงสร้างโปรเจค
- วางแผนว่าตัวโปรแกรมจะมีหน้าอะไรบ้าง
- ออกแบบ view สําหรับตัวโปรแกรม
- ออกแบบ controller ที่ใช้ควบคุมในส่วนของ view
- สร้าง class และคิด field กับ method แต่ยังไม่ implement หลักการใดๆ

## ความก้าวหน้าครั้งที่ 2
- อ่านเขียนข้อมูลในไฟล์ csv ได้
- ทําระบบสมัครสมาชิกของนิสิต/ครู ระบบล็อกอิน
- ทําระบบแจ้งเรื่องร้องเรียน
- แสดงผลเรื่องร้องเรียนทั้งหมด
- ทําหน้าแสดงรายละเอียดเรื่องร้องเรียน
- แสดงผลผู้ใช้ทั้งหมด
- ทําหน้า setting ให้เปลี่ยนธีม/ฟอนต์/ขนาดฟอนด์ เปลี่ยนชื่อ/รหัส ได้

## ความก้าวหน้าครั้งที่ 3
- ทําระบบเพิ่มหมวดหมู่
- ทําระบบเพิ่มหน่วยงาน
- เก็บคําตอบของเรื่องร้องเรียนที่ผู้ใช้กรอกเข้ามา
- แสดงผลรายละเอียดเรื่องร้องเรียน
- ทําระบบ sort เรื่องร้องเรียนโดยเวลาและจํานวนโหวต
- ทําระบบ filter แสดงผลเรื่องร้องเรียนที่สนใจ

## ความก้าวหน้าครั้งที่ 4
- อัพโหลดรูปภาพและแสดงผลได้
- รายงานผู้ใช้ รายงานเรื่องร้องเรียนได้
- ระงับผู้ใช้และคืนสิทธิ์การใช้งานได้
- ตกแต่งตัวโปรแกรมด้วย css เพิ่มเติม
- ทดสอบระบบ

## Project structure

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

## Library used
- FontawesomeFX
- OpenCSV

## Installation

1. clone project repository.
   ```
   git clone https://github.com/CS211-651/project211-loveandjava.git
   ```
2. executable file locate in jar folder.
3. choose your version to run.
4. run the file
   - 4.1. Windows : double-click the jar file to run the program.
   - 4.2. Macs    : run this command in your terminal or write `java -jar` and drag the file into terminal. Make sure that you are in the same directory as the program locate.
   ```
   java -jar path/to/ku-complaint.jar
   ```
5. or download the zip file in releases.
   
## Contributors
- [Qu1etboy](https://github.com/Qu1etboy) (Weerawong Vonggatunyu)
- [ong22280](https://github.com/ong22280) (Sittipong Hemloun)
- [IceSarun](https://github.com/IceSarun)
- [nachpolRN](https://github.com/nachpolRN)

[//]: # (**วิธีทดสอบการ RUN**)

[//]: # (1. Main)

[//]: # (   - `run Main Class`)

[//]: # (2. javafx plugin)

[//]: # (   - `MVN Clean`)

[//]: # (   - `javafx -> javafx:run`)

[//]: # ()
[//]: # (**วิธีสร้าง Jar**<br>)

[//]: # (MVN Clean<br>)

[//]: # (MVN install<br><br>)

[//]: # (file จะอยู่ใน target เป็น shade.jar )




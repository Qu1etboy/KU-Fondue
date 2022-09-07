package ku.cs.models;

public class Complaint {
    private  String number;
    private  String category;
    private  String detail;
    private  String status;

    public Complaint(String number, String category, String details, String status) {
        this.number = number;
        this.category = category;
        this.detail = details;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String details) {
        this.detail = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}

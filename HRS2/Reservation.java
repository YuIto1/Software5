public class Reservation {
    public String date;
    public String customerName;
    public String customerEmail;
    public int roomNumber;
    public String roomType;

    public Reservation(String date, String customerName, String customerEmail, int roomNumber, String roomType) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getCustomerEmail(){
        return customerEmail;
    }

    public String getDate(){
        return date;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void create() {
        System.out.println("予約を作成しました: " + customerName + "（部屋番号: " + roomNumber + "）");
    }

    public void info() {
        System.out.println(date + ", " + customerName + ", " + customerEmail + ", " + roomNumber);
    }
}

public class Reservation {
    public String date;
    public String customerName;
    public String customerEmail;
    public int roomNumber;

    public Reservation(String date, String customerName, String customerEmail, int roomNumber) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.roomNumber = roomNumber;
    }

    public void create() {
        System.out.println("予約を作成しました: " + customerName + "（部屋番号: " + roomNumber + "）");
    }
}

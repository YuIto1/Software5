public class Reservation {
    private String date;
    private String customerName;
    private String customerEmail;
    private int roomNumber;

    public Reservation(String date, String customerName, String customerEmail, int roomNumber) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.roomNumber = roomNumber;
    }

    public void create() {
        System.out.println("予約を作成しました: " + customerName + "（部屋番号: " + roomNumber + "）");
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}

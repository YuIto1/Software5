import java.util.Random;
public class Reservation {
    public String date;
    public String customerName;
    public String customerEmail;
    public int roomNumber;
    public String roomType;
    public int checkIn;
    public int reservationId;

    public Reservation(String date, String customerName, String customerEmail, int roomNumber, String roomType, int reservationId) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.checkIn = 0;
        this.reservationId = reservationId;
    }

    public Reservation(String date, String customerName, String customerEmail, int roomNumber, String roomType){
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.checkIn = 0;
        setReservationNumber();
    }

    public void setReservationNumber(){
        String roomNumberString = String.valueOf(roomNumber)+customerEmail+customerEmail+date;
        long seed = roomNumberString.hashCode();
        Random rand = new Random(seed);
        this.reservationId = rand.nextInt(1000000);
    }

    public void setCheckIn(int checkIn){
        this.checkIn = checkIn;
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

    public String checkInStr() {
        if (checkIn == 0) {
            return "チェックイン前";
        } else if(checkIn == 1) {
            return "チェックイン済";
        } else if(checkIn == 2) {
            return "チェックアウト済";
        }
        return null;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void create() {
        System.out.println("予約を作成しました: " + customerName + "（部屋番号: " + roomNumber + "）");
    }

    public void info() {
        if (checkIn != 2) {
            System.out.println(date + ", " + customerName + ", " + customerEmail + ", " + roomNumber + "," + checkInStr() + "," + reservationId);
        }
    }
}

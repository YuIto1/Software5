import java.util.Random;
import java.util.regex.*;
public class Reservation {
    public String date;
    public int dateStart;
    public int dateEnd;
    public String customerName;
    public String customerEmail;
    public int roomNumber;
    public String roomType;
    public int checkIn;
    public int reservationId;

    public Reservation(String date, String customerName, String customerEmail, int roomNumber, String roomType, int reservationId) {
        this.date = date;
        setDateNum(date);
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.checkIn = 0;
        this.reservationId = reservationId;
    }

    public Reservation(String date, String customerName, String customerEmail, int roomNumber, String roomType){
        this.date = date;
        setDateNum(date);
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.checkIn = 0;
        setReservationNumber();
    }

    public void setDateNum(String date){
        String pattern = "(\\d{4})/(\\d{2})/(\\d{2})-(\\d{4})/(\\d{2})/(\\d{2})";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(date);

        if (matcher.matches()) {
            int y1 = Integer.parseInt(matcher.group(1));                
            int m1 = Integer.parseInt(matcher.group(2));
            int d1 = Integer.parseInt(matcher.group(3));
            int y2 = Integer.parseInt(matcher.group(4));
            int m2 = Integer.parseInt(matcher.group(5));
            int d2 = Integer.parseInt(matcher.group(6));

            this.dateStart = y1 * 32 * 32 + m1 * 32 + d1;
            this.dateEnd   = y2 * 32 * 32 + m2 * 32 + d2;
            } 
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

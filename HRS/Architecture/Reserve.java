import java.util.Date;

public class Reserve {
    private Date date;
    private String guestName;
    private String guestMailAddress;
    private int roomNumber;

    public void createReservation(Date date, String guestName, String guestMailAddress, int roomNumber) {
        this.date = date;
        this.guestName = guestName;
        this.guestMailAddress = guestMailAddress;
        this.roomNumber = roomNumber;

        // create reservation logic
    }
}
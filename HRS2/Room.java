import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Room {
    private int roomNumber = 0;
    private List<int[]> reservedDate;
    private String roomType;
    private String roomInformation = "";

    public Room(int roomNumber, String roomType, String roomInformation) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomInformation = roomInformation;
        this.reservedDate = new ArrayList<>();
    }

    public Room(int roomNumber, String roomType){
        this(roomNumber, roomType, "");
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType){
        this.roomType = roomType;
    }

    public void setReserve(String date){
        this.reservedDate.add(calcDateNum(date));
    }

    public void setReserve(int[] dateNum){
        this.reservedDate.add(dateNum);
    }

    public void unsetReserve(String date){
        this.reservedDate.remove(calcDateNum(date));
    }

    public List<int[]> getReserved(){
        return reservedDate;
    }

    public boolean getIsEmpty(String date) {
        boolean isEmpty = true;
        int[] datePair = calcDateNum(date);
        int dateStart = datePair[0];
        int dateEnd = datePair[1];
        for (int[] pair : reservedDate) {
            int start = pair[0];
            int end = pair[1];
            int maxStart = Math.max(start, dateStart);
            int minEnd = Math.min(end, dateEnd);
            if (maxStart <= minEnd) {
                isEmpty = false;
                break;
            }
        }

        return isEmpty;
    }

    public String getInformation(){
        return roomInformation;
    }

    public void setInformation(String roomInformation){
        this.roomInformation = roomInformation;
    }

    public void create(){
        System.out.println(roomNumber);
    }

    public void info(){
        System.out.println(roomNumber + ", " + roomType + ", " + roomInformation);
    }

    public int[] calcDateNum(String date){
        int[] dateNum = new int[2];

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

            dateNum[0] = y1 * 32 * 32 + m1 * 32 + d1;
            dateNum[1] = y2 * 32 * 32 + m2 * 32 + d2;
        } 
        return dateNum;
    }
}

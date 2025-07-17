public class Room {
    private int roomNumber = 0;
    private boolean isReserved = false;
    private String roomType;
    private String roomInformation = "";

    public Room(int roomNumber, String roomType, String roomInformation) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomInformation = roomInformation;
        this.isReserved = false;
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

    public void setReserve(){
        isReserved = true;
    }

    public void unsetReserve(){
        isReserved = false;
    }

    public boolean getIsReserved(){
        return isReserved;
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
}

public class Room {
    private int roomNumber = 0;
    private RoomType roomType;
    private boolean isReserved = false;
    private String roomTypeString;
    private String roomInformation = "";

    public Room(int roomNumber, RoomType roomType, String roomInformation) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomInformation = roomInformation;
        this.isReserved = false;
        if(roomType.getClass().toString().contains("Standard")){
            this.roomTypeString = "standard";
        }else{
            this.roomTypeString = "suite";
        }
    }

    public Room(int roomNumber, RoomType roomType){
        this(roomNumber, roomType, "");
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType){
        this.roomType = roomType;
        if(roomType.getClass().toString().contains("Standard")){
            this.roomTypeString = "standard";
        }else{
            this.roomTypeString = "suite";
        }
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
        System.out.println(roomNumber + ", " + roomTypeString + ", " + roomInformation);
    }
}

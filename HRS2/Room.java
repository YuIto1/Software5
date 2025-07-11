public class Room {
    private int roomNumber = 0;
    private RoomType roomType;
    private boolean isReserved = false;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
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

    public void create(){
        System.out.println(roomNumber);
    }
}

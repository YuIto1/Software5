public class RoomTypes {
    private int price;
    private int availableRoomsNumber;
    private String information;
    private int capacity;

    public int getAvailableRoomsNumber() {
        return availableRoomsNumber;
    }

    public void decrementAvailableRoomsNumber() {
        availableRoomsNumber -= 1;
    }

    public int getPrice() {
        return price;
    }
}
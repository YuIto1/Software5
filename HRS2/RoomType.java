import java.util.List;
import java.util.ArrayList;
public abstract class RoomType {
    protected List<Room> rooms = new ArrayList<>();
    protected int price;
    protected int roomCount;
    protected String description;
    protected int capacity;

    public RoomType(int price, int roomCount, String description, int capacity, List<Room> rooms) {
        this.price = price;
        this.roomCount = roomCount;
        this.description = description;
        this.capacity = capacity;
        if (rooms != null) {
            this.rooms = rooms;
        }
    }
    

    public Room assignAvailableRoom(int[] dateNum) {
        int dateStart = dateNum[0];
        int dateEnd = dateNum[1];
        for (Room room : rooms) {
            boolean isEmpty = true;

            for (int[] pair : room.getReserved()) {
                int start = pair[0];
                int end = pair[1];
                int maxStart = Math.max(start, dateStart);
                int minEnd = Math.min(end, dateEnd);
                if (maxStart <= minEnd) {
                    isEmpty = false;
                    break;
                }
            }

            if (isEmpty) {
                room.setReserve(dateNum);
                return room;
            }
        }
        return null;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void add(Room room) {
        rooms.add(room);
    }

    public abstract String getTypeName();

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }
}

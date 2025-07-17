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

    public Room assignAvailableRoom() {
        for (Room room : rooms) {
            if (!room.getIsReserved()) {
                room.setReserve();
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

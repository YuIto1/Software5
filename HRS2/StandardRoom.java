import java.util.List;
public class StandardRoom extends RoomType {
    public StandardRoom(List<Room> standardRooms) {
        super(5000, 10, "WiFiあり・20㎡", 2, standardRooms);
    }

    public String getTypeName() {
        return "standard";
    }
}

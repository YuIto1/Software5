import java.util.List;
public class SuiteRoom extends RoomType {
    public SuiteRoom(List<Room> standardRooms) {
        super(12000, 5, "ジャグジー・40㎡", 4, standardRooms);
    }

    public String getTypeName() {
        return "suite";
    }
}

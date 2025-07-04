public class RoomReservationControl {
    public RoomType selectRoomType(String type) {
        if (type.equalsIgnoreCase("standard")) {
            return new StandardRoom();
        } else if (type.equalsIgnoreCase("suite")) {
            return new SuiteRoom();
        }
        return null;
    }

    public Reservation makeReservation(String date, String name, String email, Room room) {
        return new Reservation(date, name, email, room.getRoomNumber());
    }

    public void displayPrice(RoomType roomType) {
        System.out.println("料金は " + roomType.getPrice() + " 円です。");
    }
}

import java.util.ArrayList;
import java.util.List;

public class RoomReservationControl {

    private List<Reservation> reservations;

    public RoomType selectRoomType(String type) {
        if (type.equalsIgnoreCase("standard")) {
            return new StandardRoom();
        } else if (type.equalsIgnoreCase("suite")) {
            return new SuiteRoom();
        }
        return null;
    }

    public Reservation makeReservation(String date, String name, String email, Room room) {
        Reservation reservation = new Reservation(date, name, email, room.getRoomNumber());
        reservations.add(reservation); // 予約をリストに追加
        reservation.create();          // 作成時にメッセージ表示
        return reservation;
    }

    public void displayPrice(RoomType roomType) {
        System.out.println("料金は " + roomType.getPrice() + " 円です。");
    }

    public void displayAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("現在、予約はありません。");
            return;
        }

        System.out.println("予約一覧：");
        for (Reservation r : reservations) {
            System.out.println("- " + r.getRoomNumber() + "号室：" + r.customerName + "（" + r.date + "）");
        }
    }
}

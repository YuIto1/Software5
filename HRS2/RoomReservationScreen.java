import java.util.Scanner;

public class RoomReservationScreen {
    private RoomReservationControl control = new RoomReservationControl();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("管理者ですか（y / [n]）: ");
        String isAdminString = scanner.nextLine();
        boolean isAdmin = 

        System.out.print("部屋タイプを選んでください（standard / suite）: ");
        String roomTypeStr = scanner.nextLine();

        RoomType roomType = control.selectRoomType(roomTypeStr);
        if (roomType == null) {
            System.out.println("無効な部屋タイプが指定されました。");
            scanner.close();
            return;
        }

        control.displayPrice(roomType);

        System.out.print("名前を入力してください: ");
        String name = scanner.nextLine();

        System.out.print("メールアドレスを入力してください: ");
        String email = scanner.nextLine();

        System.out.print("予約日を入力してください（例: 2025/06/27）: ");
        String date = scanner.nextLine();

        System.out.print("部屋番号を入力してください（例: 501）: ");
        int roomNumber = scanner.nextInt();

        Room room = new Room(roomNumber);
        Reservation reservation = control.makeReservation(date, name, email, room);
        reservation.create();

        scanner.close();
    }

    public static void main(String[] args) {
        new RoomReservationScreen().start();
    }
}

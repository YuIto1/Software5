import java.util.Scanner;

public class RoomReservationScreen {
    private RoomReservationControl control = new RoomReservationControl();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("管理者ですか（y / [n]）: ");
        String isAdminString = scanner.nextLine();
        boolean isAdmin = isAdminString.equals("y");

        if (isAdmin) {
            System.out.print("操作を選択してください。（0:部屋登録, 1: 予約情報, 2: 部屋情報変更, 3: 予約情報変更, 4: チェックイン, 5: チェックアウト）");
            int op = scanner.nextInt();
            if (op == 0) {
                System.out.print("登録する部屋の部屋番号 : ");
                int roomNumber = scanner.nextInt();
                System.out.print("登録する部屋の種別（standard または suite）: ");
                String roomType = scanner.next();
                System.out.print("登録する部屋の情報 : ");
                String roomInformation = scanner.next();

                if (control.isValidRoomType(roomType)) {
                    if (roomInformation != null && roomInformation != ""){
                        control.addRoom(roomNumber, roomType, roomInformation);
                    }else{
                        control.addRoom(roomNumber, roomType);
                    }
                    System.out.println("部屋が登録されました。");
                } else {
                    System.out.println("無効な部屋種別です。standard または suite を入力してください。");
                }
            }else if(op == 1){
                control.displayAllReservations();
            }else if(op == 2){
                System.out.print("変更する部屋の部屋番号 : ");
                int roomNumber = scanner.nextInt();
                Room room = control.findRoomByNumber(roomNumber);
                if(room != null){
                    room.info();
                    System.out.print("部屋を削除しますか(y / [n]) : ");
                    String isDeleteString = scanner.next();
                    boolean isDelete = isDeleteString.equals("y");
                    if(isDelete){
                        control.deleteRoom(roomNumber);
                    }else{
                        System.out.println("変更を行ってください");
                        System.out.print("部屋の種別（standard または suite）: ");
                        String roomType = scanner.next();
                        System.out.print("部屋の情報 : ");
                        String roomInformation = scanner.next();

                        control.editRoom(roomNumber, roomType, roomInformation);
                    }
                }else{
                    System.out.println("無効な部屋が指定されました。");
                }
            }else if(op == 3){
                System.out.print("変更する予約の予約番号 : ");
                int reservationId = scanner.nextInt();
                Reservation reservation = control.findReservation(reservationId);
                if (reservation != null){
                    reservation.info();
                    System.out.println("予約を削除しますか(y / [n])");
                    String isDeleteString = scanner.next();
                    boolean isDelete = isDeleteString.equals("y");
                    if(isDelete){
                        control.deleteReservation(reservation);
                    }else{
                        System.out.println("変更を行ってください");
                        System.out.print("予約者の名前: ");
                        String customerName = scanner.next();
                        System.out.print("予約者のメールアドレス : ");
                        String customerMail = scanner.next();
                        System.out.print("予約する日付 : ");
                        String date = scanner.next();
                        System.out.print("予約する部屋の部屋番号 : ");
                        int roomNumber = scanner.nextInt();

                        control.editReservation(reservation, customerName, customerMail, date, roomNumber);
                    }
                }
            }else if (op == 4){
                System.out.print("変更する予約の予約番号 : ");
                int reservationId = scanner.nextInt();
                Reservation reservation = control.findReservation(reservationId);
                if (reservation != null){
                    reservation.info();
                    control.checkIn(reservation);
                    System.out.println(reservation.checkInStr());
                }
            }else if (op == 5){
                System.out.print("変更する予約の予約番号 : ");
                int reservationId = scanner.nextInt();
                Reservation reservation = control.findReservation(reservationId);
                if (reservation != null){
                    reservation.info();
                    control.checkOut(reservation);
                    System.out.println(reservation.checkInStr());
                }
            }else{
                    System.out.println("名前か部屋番号が正しくありません");
            }

            scanner.close();
            return;
        }

        
            
        System.out.println("何をしますか（1: 予約, 2: キャンセル）");
        int type = scanner.nextInt();
        if(type == 1) {
            System.out.print("部屋タイプを選んでください（standard / suite）: ");
            String roomType = scanner.next();

            if (!control.isValidRoomType(roomType)) {
                System.out.println("無効な部屋タイプが指定されました。");
                scanner.close();
                return;
            }

            control.displayPrice(roomType);

            System.out.print("名前を入力してください: ");
            String name = scanner.next();

            System.out.print("メールアドレスを入力してください: ");
            String email = scanner.next();

            System.out.print("予約日を入力してください（例: 2025/06/27）: ");
            String date = scanner.next();

            Reservation reservation = control.makeReservation(date, name, email, roomType);
            if (reservation != null) {
                reservation.info();
            }
        }else{
            System.out.print("名前を入力してください: ");
            String name = scanner.next();

            System.out.print("部屋番号を入力してください: ");
            int roomNumber = scanner.nextInt();

            Reservation reservation = control.findReservation(name, roomNumber);

            reservation.info();

            System.out.println("本当にキャンセルしまか(true/[false])");
            boolean cancel = scanner.nextBoolean();
            if(cancel){
                control.deleteReservation(reservation);
            }
        }

            scanner.close();
    }

    public static void main(String[] args) {
        new RoomReservationScreen().start();
    }
}

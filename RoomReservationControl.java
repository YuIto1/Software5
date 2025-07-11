import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomReservationControl {

    private List<Reservation> reservations;
    private List<Room> rooms;

    public RoomReservationControl() {
        reservations = new ArrayList<>();
        loadReservationsFromFile("reservation.csv");
        loadRoomsFromFile("room.csv");
    }

    public RoomType selectRoomType(String type) {
        if (type.equalsIgnoreCase("standard")) {
            return new StandardRoom();
        } else if (type.equalsIgnoreCase("suite")) {
            return new SuiteRoom();
        }
        return null;
    }

    public Reservation makeReservation(String date, String name, String email) {
        Reservation reservation = new Reservation(date, name, email, 0); //roomNumber
        reservations.add(reservation);
        reservation.create();
        saveReservationsToFile("reservation.csv");
        return reservation;
    }

    public void addRoom(int roomNumber){
        Room room = new Room(roomNumber);
        rooms.add(room);
        room.create();
        saveRoomsToFile("room.csv");
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
            System.out.println("- " + r.roomNumber + "号室：" + r.customerName + "（" + r.date + "）");
        }
    }

    // 予約リストをファイルに保存
    public void saveReservationsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Reservation r : reservations) {
                // CSV形式で書き出し（カンマ区切り）
                writer.write(r.date + "," + r.customerName + "," + r.customerEmail + "," + r.roomNumber);
                writer.newLine();
            }
            System.out.println("予約をファイルに保存しました: " + filename);
        } catch (IOException e) {
            System.err.println("ファイル保存中にエラーが発生しました: " + e.getMessage());
        }
    }

    // ファイルから予約リストを読み込む
    public void loadReservationsFromFile(String filename) {
        reservations.clear(); // 読み込み前にクリア

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // CSVをカンマで分割
                String[] parts = line.split(",", -1);
                if (parts.length == 4) {
                    String date = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    int roomNumber = Integer.parseInt(parts[3]);
                    Reservation r = new Reservation(date, name, email, new Room(roomNumber).getRoomNumber());
                    reservations.add(r);
                }
            }
            System.out.println("予約をファイルから読み込みました: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("ファイルが見つかりません。新規ファイルが作成されます: " + filename);
        } catch (IOException e) {
            System.err.println("ファイル読み込み中にエラーが発生しました: " + e.getMessage());
        }
    }

    public void loadRoomsFromFile(String filename) {
        rooms.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 2) {
                    int roomNumber = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    RoomType roomType = selectRoomType(type);
                    if (roomType != null) {
                        rooms.add(new Room(roomNumber));
                    }
                }
            }
            System.out.println("部屋情報を読み込みました: " + filename);
        } catch (IOException e) {
            System.err.println("部屋ファイルの読み込みエラー: " + e.getMessage());
        }
    }

    public void saveRoomsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Room room : rooms) {
                writer.write(room.getRoomNumber() + "," + room.getRoomType().getClass().getSimpleName().toLowerCase());
                writer.newLine();
            }
            System.out.println("部屋情報を保存しました: " + filename);
        } catch (IOException e) {
            System.err.println("部屋ファイルの保存エラー: " + e.getMessage());
        }
    }
}

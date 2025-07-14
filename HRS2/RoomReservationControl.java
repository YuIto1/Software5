import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class RoomReservationControl {

    private List<Reservation> reservations;
    private List<Room> rooms;

    public RoomReservationControl() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        loadRoomsFromFile("room.csv");
        loadReservationsFromFile("reservation.csv");
    }

    public RoomType selectRoomType(String type) {
        if (type.equalsIgnoreCase("standard")) {
            return new StandardRoom();
        } else if (type.equalsIgnoreCase("suite")) {
            return new SuiteRoom();
        }
        return null;
    }

    public Reservation makeReservation(String date, String name, String email, RoomType requestedType) {
        Room room = assignAvailableRoom(requestedType);
        if (room == null) {
            System.out.println("空室がありません。予約できませんでした。");
            return null;
        }
        Reservation reservation = new Reservation(date, name, email, room.getRoomNumber(), room.getRoomType()); 
        reservations.add(reservation);
        reservation.create();
        saveReservationsToFile("reservation.csv");
        saveRoomsToFile("room.csv");
        return reservation;
    }

    public void addRoom(int roomNumber, RoomType roomType, String roomInformation){
        Room room = new Room(roomNumber, roomType, roomInformation);
        rooms.add(room);
        room.create();
        saveRoomsToFile("room.csv");
    }

    public void addRoom(int roomNumber, RoomType roomType){
        addRoom(roomNumber, roomType, "");
    }

    public Room assignAvailableRoom(RoomType requestedType) {
        for (Room room : rooms) {
            if (!room.getIsReserved() && room.getRoomType().getClass() == requestedType.getClass()) {
                room.setReserve();
                return room;
            }
        }
        return null;
    }

    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
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

    public void deleteRoom(int roomNumber){
        Iterator<Room> iterator = rooms.iterator();
        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (room.getRoomNumber() == roomNumber) {
                if (room.getIsReserved()){
                    System.out.println("予約されているため削除できません");
                    return;
                }
                iterator.remove();
                saveRoomsToFile("room.csv");
            }
        }
    }

    public void editRoom(int roomNumber, RoomType roomType, String roomInformation){
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber){
                if (room.getIsReserved()){
                    System.out.println("予約されているため削除できません");
                    return;
                }
                room.setRoomType(roomType);
                room.setInformation(roomInformation);
                saveRoomsToFile("room.csv");
            }
        }
    }

    public Reservation findReservation(String customerName, int roomNumber){
        for (Reservation reservation : reservations){
            if(reservation.getCustomerName().equals(customerName) && reservation.getRoomNumber() == roomNumber){
                return reservation;
            }
        }
        return null;
    }

    public void deleteReservation(Reservation reservation){
        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation reservation0 = iterator.next();
            if (reservation0.getRoomNumber() == reservation.getRoomNumber() && reservation0.getCustomerName().equals(reservation.getCustomerName())) {
                Room room = findRoomByNumber(reservation0.getRoomNumber());
                room.unsetReserve();
                iterator.remove();
                saveReservationsToFile("reservation.csv");
                saveRoomsToFile("room.csv");
            }
        }
    }
    public void editReservation(Reservation reservation, String customerName, String customerEmail, String date, int roomNumber){
        for (Reservation reservation0 : reservations) {
            if (reservation0.getRoomNumber() == reservation.getRoomNumber() && reservation0.getCustomerName().equals(reservation.getCustomerName())){
                int oldRoomNumber = reservation0.getRoomNumber();
                reservation0.date = date;
                reservation0.customerName = customerName;
                reservation0.customerEmail = customerEmail;
                Room room = findRoomByNumber(roomNumber);
                if (room != null){
                    if (oldRoomNumber != roomNumber) {
                        Room oldRoom = findRoomByNumber(oldRoomNumber);
                        if (room.getIsReserved()){
                            System.out.println("その部屋は予約されています");
                        }else{
                            reservation0.roomNumber = roomNumber;
                            reservation0.roomType = room.getRoomType();
                            oldRoom.unsetReserve();
                            room.setReserve();
                            saveRoomsToFile("room.csv");
                        }
                    }
                }
                saveReservationsToFile("reservation.csv");
            }
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
                    Room room = findRoomByNumber(roomNumber);
                    if (room != null){
                        RoomType roomType = room.getRoomType();
                        Reservation r = new Reservation(date, name, email, roomNumber, roomType);
                        reservations.add(r);
                    }
                }
            }
            System.out.println("予約をファイルから読み込みました: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("ファイルが見つかりません。新規ファイルが作成されます: " + filename);
        } catch (IOException e) {
            System.err.println("ファイル読み込み中にエラーが発生しました: " + e.getMessage());
        }
    }


    public void saveRoomsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Room room : rooms) {
                String type = "unknown";
                if (room.getRoomType() instanceof StandardRoom) {
                    type = "standard";
                } else if (room.getRoomType() instanceof SuiteRoom) {
                    type = "suite";
                }
                writer.write(room.getRoomNumber() + "," + type + "," + room.getIsReserved() + "," + room.getInformation());
                writer.newLine();
            }
            System.out.println("部屋情報を保存しました: " + filename);
        } catch (IOException e) {
            System.err.println("部屋ファイルの保存エラー: " + e.getMessage());
        }
    }

    public void loadRoomsFromFile(String filename) {
        rooms.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    int roomNumber = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    boolean isReserved = Boolean.parseBoolean(parts[2]);
                    String roomInformation = parts[3];
                    RoomType roomType = selectRoomType(type);
                    if (roomType != null) {
                        Room room = new Room(roomNumber, roomType, roomInformation);
                        if (isReserved) {
                            room.setReserve();
                        }
                        rooms.add(room);
                    } else {
                        System.err.println("不正な部屋タイプ: " + type);
                    }
                } else {
                    System.err.println("不正な部屋データ形式: " + line);
                }
            }
            reader.close();
            System.out.println("部屋情報を読み込みました: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("部屋ファイルが見つかりません。新規作成されます: " + filename);
        } catch (IOException e) {
            System.err.println("部屋ファイルの読み込みエラー: " + e.getMessage());
        }
    }


}

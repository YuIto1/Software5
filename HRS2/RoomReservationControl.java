import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.regex.*;

public class RoomReservationControl {

    private List<Reservation> reservations;
    private StandardRoom standardRoom;
    private SuiteRoom suiteRoom;
    private String standardRoomFile = "standardRoom.csv";
    private String suiteRoomFile = "suiteRoom.csv";

    public RoomReservationControl() {
        reservations = new ArrayList<>();
        standardRoom = new StandardRoom(loadRoomsFromFile(standardRoomFile));
        suiteRoom = new SuiteRoom(loadRoomsFromFile(suiteRoomFile));
        loadReservationsFromFile("reservation.csv");
    }

    public RoomType selectRoomType(String type) {
        if (type.equalsIgnoreCase("standard")) {
            return standardRoom;
        } else if (type.equalsIgnoreCase("suite")) {
            return suiteRoom;
        }
        return null;
    }

    public boolean isValidRoomType(String roomType) {
        if (roomType.equals("standard") || roomType.equals("suite")) {
            return true;
        }
        return false;
    }

    public Reservation makeReservation(String date, String name, String email, String requestedType) {
        RoomType roomType;
        String file;
        if (requestedType.equals("standard")) {
            roomType = standardRoom;
            file = standardRoomFile;
        }else if(requestedType.equals("suite")) {
            roomType = suiteRoom;
            file = suiteRoomFile;
        }else{
            roomType = null;
            file = null;
            System.out.println("無効な部屋種別です");
        }

        Room room = roomType.assignAvailableRoom(calcDateNum(date));

        if (room == null) {
            System.out.println("空室がありません。予約できませんでした。");
            return null;
        }
        Reservation reservation = new Reservation(date, name, email, room.getRoomNumber(), room.getRoomType()); 
        reservations.add(reservation);
        reservation.create();
        saveReservationsToFile("reservation.csv");
        saveRoomsToFile(file, roomType);
        return reservation;
    }

    public int[] calcDateNum(String date){
        int[] dateNum = new int[2];

        String pattern = "(\\d{4})/(\\d{2})/(\\d{2})-(\\d{4})/(\\d{2})/(\\d{2})";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(date);

        if (matcher.matches()) {
            int y1 = Integer.parseInt(matcher.group(1));                
            int m1 = Integer.parseInt(matcher.group(2));
            int d1 = Integer.parseInt(matcher.group(3));
            int y2 = Integer.parseInt(matcher.group(4));
            int m2 = Integer.parseInt(matcher.group(5));
            int d2 = Integer.parseInt(matcher.group(6));

            dateNum[0] = y1 * 32 * 32 + m1 * 32 + d1;
            dateNum[1] = y2 * 32 * 32 + m2 * 32 + d2;
        } 
        return dateNum;
    }

    public void addRoom(int roomNumber, String requestedType, String roomInformation){
        RoomType roomType;
        String file;
        if (requestedType.equals("standard")) {
            roomType = standardRoom;
            file = standardRoomFile;
        }else if(requestedType.equals("suite")) {
            roomType = suiteRoom;
            file = suiteRoomFile;
        }else{
            roomType = null;
            file = null;
            System.out.println("無効な部屋種別です");
        }

        Room room = new Room(roomNumber, requestedType, roomInformation);
        roomType.add(room);
        room.create();
        saveRoomsToFile(file, roomType);
    }

    public void addRoom(int roomNumber, String requestedType){
        addRoom(roomNumber, requestedType, "");
    }

    public Room findRoomByNumber(int roomNumber) {
        for (Room room : standardRoom.getRooms()) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        
        for (Room room : suiteRoom.getRooms()) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }

    public void displayPrice(String requestedType) {
        RoomType roomType;
        if (requestedType.equals("standard")) {
            roomType = standardRoom;
        }else if(requestedType.equals("suite")) {
            roomType = suiteRoom;
        }else{
            roomType = null;
            System.out.println("無効な部屋種別です");
        }
        System.out.println("料金は " + roomType.getPrice() + " 円です。");
    }

    public void displayAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("現在、予約はありません。");
            return;
        }

        System.out.println("予約一覧：");
        for (Reservation r : reservations) {
            r.info();
        }
    }

    public void deleteRoom(int roomNumber){
        List<Room> targetRooms = standardRoom.getRooms();
        String file = standardRoomFile;

        Iterator<Room> iterator = targetRooms.iterator();
        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (room.getRoomNumber() == roomNumber) {
                if (!room.getReserved().isEmpty()){
                    System.out.println("予約されているため削除できません");
                    return;
                }
                iterator.remove();
                saveRoomsToFile(file, standardRoom);
                return;
            }
        }


        targetRooms = suiteRoom.getRooms();
        file = suiteRoomFile;

        iterator = targetRooms.iterator();
        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (room.getRoomNumber() == roomNumber) {
                if (!room.getReserved().isEmpty()){
                    System.out.println("予約されているため削除できません");
                    return;
                }
                iterator.remove();
                saveRoomsToFile(file, suiteRoom);
                return;
            }
        }
    }

    public void editRoom(int roomNumber, String requestedType, String roomInformation){
        List<RoomType> roomTypes = new ArrayList<>();
        String file = "";
        roomTypes.add(standardRoom);
        roomTypes.add(suiteRoom);
        
        for (RoomType roomType : roomTypes) {
            if (roomType.getTypeName().equals("standard")){
                file = standardRoomFile;
            }else{
                file = suiteRoomFile;
            }

            Iterator<Room> iterator = roomType.getRooms().iterator();
            while (iterator.hasNext()) {
                Room room = iterator.next();
                if (room.getRoomNumber() == roomNumber){
                    if (!room.getReserved().isEmpty()){
                        System.out.println("予約されているため削除できません");
                        return;
                    }
                    
                    room.setRoomType(requestedType);
                    room.setInformation(roomInformation);
                    if (roomType.getTypeName().equals(requestedType)){
                        saveRoomsToFile(file, roomType);
                        return;
                    }else{
                        RoomType newRoomType = selectRoomType(requestedType);
                        String newfile = "";
                        if (newRoomType.getTypeName().equals("standard")){
                            newfile = standardRoomFile;
                        }else{
                            newfile = suiteRoomFile;
                        }
                        newRoomType.getRooms().add(room);
                        iterator.remove();
                        saveRoomsToFile(file, roomType);
                        saveRoomsToFile(newfile, newRoomType);
                        return;
                    }
                }
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

    public Reservation findReservation(int reservationId){
        for (Reservation reservation : reservations) {
            if(reservation.reservationId == reservationId) {
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
                room.unsetReserve(reservation.getDate());
                iterator.remove();
                saveReservationsToFile("reservation.csv");
                saveRoomsToFile(standardRoomFile, standardRoom);
                saveRoomsToFile(suiteRoomFile, suiteRoom);
            }
        }
    }

    public void editReservation(Reservation reservation, String customerName, String customerEmail, String date, int roomNumber){
        for (Reservation reservation0 : reservations) {
            if (reservation0.getRoomNumber() == reservation.getRoomNumber() && reservation0.getCustomerName().equals(reservation.getCustomerName())){
                int oldRoomNumber = reservation0.getRoomNumber();
                String oldDate = reservation0.date;
                reservation0.date = date;
                reservation0.customerName = customerName;
                reservation0.customerEmail = customerEmail;
                Room room = findRoomByNumber(roomNumber);
                if (room != null){
                    if (oldRoomNumber != roomNumber) {
                        Room oldRoom = findRoomByNumber(oldRoomNumber);
                        if (!room.getIsEmpty(date)){
                            System.out.println("その部屋は予約されています");
                        }else{
                            reservation0.roomNumber = roomNumber;
                            reservation0.roomType = room.getRoomType();
                            oldRoom.unsetReserve(oldDate);
                            room.setReserve(date);
                            saveRoomsToFile(standardRoomFile, standardRoom);
                            saveRoomsToFile(suiteRoomFile, suiteRoom);
                        }
                    }
                } else {
                    System.out.println("部屋番号が無効です");
                }
                saveReservationsToFile("reservation.csv");
            }
        }
    }

    public void checkIn(Reservation reservation){
        reservation.setCheckIn(1);
        saveReservationsToFile("reservation.csv");
    }

    public void checkOut(Reservation reservation){
        reservation.setCheckIn(2);
        Room room = findRoomByNumber(reservation.getRoomNumber());
        String date = reservation.getDate();
        room.unsetReserve(date);
        saveReservationsToFile("reservation.csv");
    }


    // 予約リストをファイルに保存
    public void saveReservationsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Reservation r : reservations) {
                // CSV形式で書き出し（カンマ区切り）

                writer.write(r.date + "," + r.customerName + "," + r.customerEmail + "," + r.roomNumber + "," + r.checkIn + "," + r.reservationId);
                writer.newLine();
            }
            System.out.println("予約をファイルに保存しました: " + filename);
        } catch (IOException e) {
            System.err.println("ファイル保存中にエラーが発生しました: " + e.getMessage());
        }
    }

    public void loadReservationsFromFile(String filename) {
        reservations.clear(); // 読み込み前にクリア

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 6) {
                    String date = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    int roomNumber = Integer.parseInt(parts[3]);
                    int checkIn = Integer.parseInt(parts[4]);
                    int reservationId = Integer.parseInt(parts[5]);
                    Room room = findRoomByNumber(roomNumber);
                    if (room != null){
                        String roomType = room.getRoomType();
                        Reservation r = new Reservation(date, name, email, roomNumber, roomType, reservationId);
                        r.setCheckIn(checkIn);
                        reservations.add(r);
                        room.setReserve(date);
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


    public void saveRoomsToFile(String filename, RoomType roomType) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Room room : roomType.getRooms()) {
                writer.write(room.getRoomNumber() + "," + room.getRoomType() + "," + "reservation" + "," + room.getInformation());
                writer.newLine();
            }
            System.out.println("部屋情報を保存しました: " + filename);
        } catch (IOException e) {
            System.err.println("部屋ファイルの保存エラー: " + e.getMessage());
        }
    }

    public List<Room> loadRoomsFromFile(String filename) {
        List<Room> roomsOut = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 4) {
                    int roomNumber = Integer.parseInt(parts[0]);
                    String roomType = parts[1];
                    String roomInformation = parts[3];

                    if (isValidRoomType(roomType)) {
                        Room room = new Room(roomNumber, roomType, roomInformation);
                        roomsOut.add(room);
                    } else {
                        System.err.println("不正な部屋タイプ: " + roomType);
                    }
                } else {
                    System.err.println("不正な部屋データ形式: " + line);
                }
            }
            reader.close();
            System.out.println("部屋情報を読み込みました: " + filename);
            return roomsOut;
        } catch (FileNotFoundException e) {
            System.out.println("部屋ファイルが見つかりません。新規作成されます: " + filename);
        } catch (IOException e) {
            System.err.println("部屋ファイルの読み込みエラー: " + e.getMessage());
        }
        return null;
    }
}

public abstract class RoomType {
    protected int price;
    protected int roomCount;
    protected String description;
    protected int capacity;

    public RoomType(int price, int roomCount, String description, int capacity) {
        this.price = price;
        this.roomCount = roomCount;
        this.description = description;
        this.capacity = capacity;
    }

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

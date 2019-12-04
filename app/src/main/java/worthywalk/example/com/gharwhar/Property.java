package worthywalk.example.com.gharwhar;

public class Property {


    String Property;
    int rooms;
    int baths;
    String location;
    int price;
    String Url ;
    boolean buy;

    public Property(String property, int rooms, int baths, String location, int price, String url,boolean buy) {
        Property = property;
        this.rooms = rooms;
        this.baths = baths;
        this.location = location;
        this.price = price;
        Url = url;
        this.buy=buy;
    }
}

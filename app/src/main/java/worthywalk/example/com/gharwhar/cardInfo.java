package worthywalk.example.com.gharwhar;

public class cardInfo {
    public String name;
    public double value;
    public String url=" ";
    public boolean like;

    public cardInfo(boolean like, String name, double value, String url) {
        this.like=like;
        this.name = name;

        this.value = value;
        this.url = url;
    }
}

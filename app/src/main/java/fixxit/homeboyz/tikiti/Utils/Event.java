package fixxit.homeboyz.tikiti.Utils;

/**
 * Created by homeboyz on 3/24/16.
 */
public class Event {
    private String title, image;
    private String date;
    private int id;

    public Event(int id, String name, String date, String image, Object o, String university_name, String s, Object o1) {
        this.id = id;
        this.title = name;
        this.image = image;
        this.date = date;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

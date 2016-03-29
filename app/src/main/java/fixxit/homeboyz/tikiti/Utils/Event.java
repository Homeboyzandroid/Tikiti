package fixxit.homeboyz.tikiti.Utils;

import java.util.ArrayList;

/**
 * Created by homeboyz on 3/24/16.
 */
public class Event {
    private String title, thumbnailUrl;
    private int date;

    public Event(){}

    public Event(String name, String thumbnailUrl, int date) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

}

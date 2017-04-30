package menezes.paulo.bomdebola.models;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Paulo Menezes on 23/07/2015.
 */
public class Post implements Serializable {
    private String id;
    private String idUser;
    private String title;
    private String text;
    private String place;
    private String image;
    private String link;
    private String type;
    private String[] poll;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getPoll() {
        return poll;
    }

    public void setPoll(String[] poll) {
        this.poll = poll;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void load(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                setId(jsonObject.getString("id"));
            } else {
                setId(jsonObject.getString("_id"));
            }
            setIdUser(jsonObject.getString("idUser"));
            setTitle(jsonObject.getString("title"));
            setText(jsonObject.getString("text"));
            setPlace(jsonObject.getString("place"));
            setImage(jsonObject.getString("image"));
            setType(jsonObject.getString("type"));
            setDate(jsonObject.getString("date"));
        } catch (Exception e) {

        }
    }
}

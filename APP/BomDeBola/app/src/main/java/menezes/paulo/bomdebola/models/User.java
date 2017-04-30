package menezes.paulo.bomdebola.models;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Paulo Menezes on 22/07/2015.
 */
public class User implements Serializable {
    private String id;
    private String name;
    private String email;
    private String googlePlusProfile;
    private String imageProfile;
    private String imageCover;
    private String number;
    private String district;
    private String city;
    private String birthday;
    private String idTeam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGooglePlusProfile() {
        return googlePlusProfile;
    }

    public void setGooglePlusProfile(String googlePlusProfile) {
        this.googlePlusProfile = googlePlusProfile;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String player) {
        this.idTeam = player;
    }

    public void load(String user) {
        try {
            JSONObject jsonObject = new JSONObject(user);

            load(jsonObject);
        } catch (Exception e) {

        }
    }

    public void load(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                setId(jsonObject.getString("id"));
            } else {
                setId(jsonObject.getString("_id"));
            }
            setName(jsonObject.getString("name"));
            setEmail(jsonObject.getString("email"));
            setGooglePlusProfile(jsonObject.getString("googlePlusProfile"));
            setImageProfile(jsonObject.getString("imageProfile"));
            setImageCover(jsonObject.getString("imageCover"));
            setNumber(jsonObject.getString("number"));
            setDistrict(jsonObject.getString("district"));
            setCity(jsonObject.getString("city"));
            setBirthday(jsonObject.getString("birthday"));
            setIdTeam(jsonObject.getString("idTeam"));
        } catch (Exception e) {

        }
    }
}

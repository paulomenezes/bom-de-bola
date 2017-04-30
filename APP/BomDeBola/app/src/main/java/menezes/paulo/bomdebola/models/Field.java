package menezes.paulo.bomdebola.models;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class Field {
    private String id;
    private String name;
    private String address;
    private String district;
    private String latitude;
    private String longitude;
    private String type;
    private String rpa;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRpa() {
        return rpa;
    }

    public void setRpa(String rpa) {
        this.rpa = rpa;
    }
}

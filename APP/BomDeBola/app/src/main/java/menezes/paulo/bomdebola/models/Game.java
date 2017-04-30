package menezes.paulo.bomdebola.models;

import java.io.Serializable;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class Game implements Serializable {
    private String id;
    private String idTeam01;
    private String idTeam02;
    private String idChampionship;
    private String idField;
    private String idManager;
    private String score01;
    private String score02;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTeam01() {
        return idTeam01;
    }

    public void setIdTeam01(String idTeam01) {
        this.idTeam01 = idTeam01;
    }

    public String getIdTeam02() {
        return idTeam02;
    }

    public void setIdTeam02(String idTeam02) {
        this.idTeam02 = idTeam02;
    }

    public String getIdChampionship() {
        return idChampionship;
    }

    public void setIdChampionship(String idChampionship) {
        this.idChampionship = idChampionship;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getIdManager() {
        return idManager;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
    }

    public String getScore01() {
        return score01;
    }

    public void setScore01(String score01) {
        this.score01 = score01;
    }

    public String getScore02() {
        return score02;
    }

    public void setScore02(String score02) {
        this.score02 = score02;
    }
}

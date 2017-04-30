package menezes.paulo.bomdebola.models;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class Team {
    private String id;
    private String name;
    private String idManager;
    private String rpa;
    private String idChampionship;

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

    public String getIdManager() {
        return idManager;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
    }

    public String getRpa() {
        return rpa;
    }

    public void setRpa(String rpa) {
        this.rpa = rpa;
    }

    public String getIdChampionship() {
        return idChampionship;
    }

    public void setIdChampionship(String idTeam) {
        this.idChampionship = idTeam;
    }
}

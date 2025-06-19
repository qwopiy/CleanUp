package com.wi3uplus2.cleanup;

public class PlayerAchievementElement {
    private int id;
    private String name;
    private String dateAchieved;

    public PlayerAchievementElement(int id, String name, String dateAchieved) {
        this.id = id;
        this.name = name;
        this.dateAchieved = dateAchieved;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateAchieved() {
        return dateAchieved;
    }

}

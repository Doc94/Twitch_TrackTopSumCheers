package me.mrdoc.twitchtopcheers.classes;

/**
 * Created on 20-07-2017 for TwitchGOTH_TOPSumCheers.
 *
 * @author Doc
 */
public class Cheering {

    private String username;
    private int totalCheers;

    public Cheering(String username, int totalCheers) {
        this.username = username;
        this.totalCheers = totalCheers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalCheers() {
        return totalCheers;
    }

    public void setTotalCheers(int totalCheers) {
        this.totalCheers = totalCheers;
    }
}

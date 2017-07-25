package me.mrdoc.twitchtopcheers.classes;

import java.util.Map;

/**
 * Created on 20-07-2017 for TwitchGOTH_TOPSumCheers.
 *
 * @author Doc
 */
public class Config {

    private String out_message;
    private String empty_message;
    private Map<String, String> channel_config;
    private int top_size;

    public String getOut_message() {
        return out_message;
    }

    public void setOut_message(String out_message) {
        this.out_message = out_message;
    }

    public Map<String, String> getChannel_config() {
        return channel_config;
    }

    public void setChannel_config(Map<String, String> channel_config) {
        this.channel_config = channel_config;
    }

    public int getTop_size() {
        return top_size;
    }

    public void setTop_size(int top_size) {
        this.top_size = top_size;
    }

    public String getEmpty_message() {
        return empty_message;
    }

    public void setEmpty_message(String empty_message) {
        this.empty_message = empty_message;
    }
}

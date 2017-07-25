package me.mrdoc.twitchtopcheers.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import me.mrdoc.twitchtopcheers.classes.Config;
import me.mrdoc.twitchtopcheers.utils.PrintConsole;

import java.io.File;
import java.io.IOException;

/**
 * Created on 20-07-2017 for TwitchGOTH_TOPSumCheers.
 *
 * @author Doc
 */
public class ConfigManager {

    private static final String CONFIG_FILE = "config.yml";

    private static Config configData;

    public static void loadConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            configData = mapper.readValue(new File(CONFIG_FILE), Config.class);
            configData.getChannel_config().put("channel_name","#"+configData.getChannel_config().get("channel_name"));
            if(!configData.getChannel_config().get("bot_token").contains("oauth:")) {
                PrintConsole.sendError("El login del BOT no contiene oauth: por lo que la conexion no es posible");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getConfig() {
        return configData;
    }

}

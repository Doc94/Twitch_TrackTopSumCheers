package me.mrdoc.twitchtopcheers.bot;

import me.mrdoc.twitchtopcheers.Core;
import me.mrdoc.twitchtopcheers.bot.listener.GeneralListener;
import me.mrdoc.twitchtopcheers.bot.listener.ManageCheerListener;
import me.mrdoc.twitchtopcheers.managers.ConfigManager;
import me.mrdoc.twitchtopcheers.utils.PrintConsole;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.exception.IrcException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created on 19-07-2017 for twitchtopcheers.
 *
 * @author Doc
 */
public class TwitchBOT {

    private PircBotX botInstance;

    private ArrayList<String> messages = new ArrayList<String>();

    public TwitchBOT() {
        botInstance = new PircBotX(generateConfiguration().buildConfiguration());
        PrintConsole.sendInfo("Cargando modulo de mensajes del BOT (Este sistema no esta operando correctamente)");
        processMessages();
        PrintConsole.sendInfo("Iniciando BOT");
        startSystem();
    }

    private void processMessages() {
        Core.exec.scheduleAtFixedRate(() -> {
            if(Core.jaideBOTInstance.getBOTInstance().isConnected()) {
                if(!messages.isEmpty()) {
                    PrintConsole.sendInfo("Se enviaran " + messages.size() + " mensajes via BOT.");
                    for(String message : messages) {
                        botInstance.send().message(ConfigManager.getConfig().getChannel_config().get("channel_name"),"RUNNING");
                        messages.remove(message);
                    }
                } else {
                    PrintConsole.sendInfo("No hay mensajes pendientes de ser enviados por el BOT");
                }
            } else {
                PrintConsole.sendError("El BOT esta desconectado y no puede enviar mensajes");
            }
        }, 10, 5, TimeUnit.SECONDS);
    }

    private void startSystem() {
        try {
            botInstance.startBot();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }
    }

    private Configuration.Builder generateConfiguration() {
        return new Configuration.Builder()

                .setEncoding(Charset.forName("UTF-8")) //Support UTF-8

                .setAutoNickChange(false) //Twitch doesn't support multiple users
                .setOnJoinWhoEnabled(false) //Twitch doesn't support WHO command

                .setCapEnabled(true)
                .addCapHandler(new EnableCapHandler("twitch.tv/membership")) //Twitch by default doesn't send JOIN, PART, and NAMES unless you request it, see https://github.com/justintv/Twitch-API/blob/master/IRC.md#membership
                .addCapHandler(new EnableCapHandler("twitch.tv/tags"))
                .addCapHandler(new EnableCapHandler("twitch.tv/commands"))

                .addServer("irc.twitch.tv")

                .setName(ConfigManager.getConfig().getChannel_config().get("bot_username")) //Your twitch.tv username
                .setServerPassword(ConfigManager.getConfig().getChannel_config().get("bot_token")) //Your oauth password from http://twitchapps.com/tmi
                .addAutoJoinChannel(ConfigManager.getConfig().getChannel_config().get("channel_name"))

                .addListener(new ManageCheerListener())
                .addListener(new GeneralListener())

                ;
    }

    public void sendMessage(String message) {
        messages.add(message);
        PrintConsole.sendInfo("Se ha cargado un mensaje para ser enviado por el BOT");
    }

    public PircBotX getBOTInstance() {
        return botInstance;
    }
}

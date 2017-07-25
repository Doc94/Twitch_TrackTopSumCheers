package me.mrdoc.twitchtopcheers.bot.listener;

import me.mrdoc.twitchtopcheers.utils.PrintConsole;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectAttemptFailedEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.ServerResponseEvent;

/**
 * Created on 24-07-2017 for TwitchGOTH_TOPSumCheers.
 *
 * @author Doc
 */
public class GeneralListener extends ListenerAdapter {

    @Override
    public void onConnectAttemptFailed(ConnectAttemptFailedEvent event) {
        PrintConsole.sendError(event.toString());
        System.exit(0);
    }

    @Override
    public void onServerResponse(ServerResponseEvent event) {
        if(event.getCode() == 446 || event.getCode() == 339) {
            PrintConsole.sendError("Error de conexion al canal IRC para trackear cheers. \nDetalles: " + event.getRawLine());
            System.exit(0);
        }
    }

    @Override
    public void onConnect(ConnectEvent event) {
        PrintConsole.sendInfo("Servidor conectado correctamente.");
        event.respond("Conectado correctamente, trackeando envio de cheers.");
    }



}

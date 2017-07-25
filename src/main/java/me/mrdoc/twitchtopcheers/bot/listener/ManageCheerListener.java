package me.mrdoc.twitchtopcheers.bot.listener;

import me.mrdoc.twitchtopcheers.classes.Cheering;
import me.mrdoc.twitchtopcheers.managers.ConfigManager;
import me.mrdoc.twitchtopcheers.managers.DBCheerManagent;
import me.mrdoc.twitchtopcheers.managers.FileCheerManagent;
import me.mrdoc.twitchtopcheers.utils.PrintConsole;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;

/**
 * Created on 19-07-2017 for twitchtopcheers.
 *
 * @author Doc
 */
public class ManageCheerListener extends ListenerAdapter {

    @Override
    public void onMessage(MessageEvent event) {
        for(String tag : event.getV3Tags().keySet()) {
            if(tag.contains("bits")) {
                System.out.println("==========================================]");
                PrintConsole.sendInfo("Se detecto un mensaje con TAG de cheers, enviando a evento custom.");
                onCheerEvent(event);
            }
        }
    }

    private void onCheerEvent(MessageEvent event) {
        PrintConsole.sendInfo("El usuario " + event.getUser().getNick() + " envio " + event.getV3Tags().get("bits") + " cheers.");
        int cheerInMsg = Integer.parseInt(event.getV3Tags().get("bits")); //Obtengo cantidad de bits
        DBCheerManagent.addCheer(event.getUser().getNick(),cheerInMsg);

        //Recargamos TOP
        HashMap<Integer,Cheering> cheersTOP = DBCheerManagent.getTOPCheers(ConfigManager.getConfig().getTop_size());

        FileCheerManagent.setTOP(cheersTOP);
    }

}

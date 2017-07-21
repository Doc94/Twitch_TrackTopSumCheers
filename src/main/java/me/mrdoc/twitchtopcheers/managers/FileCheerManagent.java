package me.mrdoc.twitchtopcheers.managers;

import me.mrdoc.twitchtopcheers.classes.Cheering;
import me.mrdoc.twitchtopcheers.utils.PrintConsole;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created on 20-07-2017 for TwitchGOTH_TOPSumCheers.
 *
 * @author Doc
 */
public class FileCheerManagent {

    private static File filetop;


    public static void setTOP(Cheering cheer) {
        try {
            FileWriter fw = new FileWriter(getFileTOP());
            BufferedWriter bw = new BufferedWriter(fw);

            String message = ConfigManager.getConfig().getOut_message();

            message = message.replace("{username}",cheer.getUsername());
            message = message.replace("{cheers}",cheer.getTotalCheers() + "");

            PrintConsole.sendInfo("Mensaje en top.txt fue reemplazado a: " + message);

            bw.write(message);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadFileCheerManagent() {
        cheeckFile();
    }

    private static File getFileTOP() {
        cheeckFile();
        return filetop;
    }

    private static void cheeckFile() {
        filetop = new File("top.txt");
        try {
            filetop.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

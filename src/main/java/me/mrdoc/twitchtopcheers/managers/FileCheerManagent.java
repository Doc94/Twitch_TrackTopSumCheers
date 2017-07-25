package me.mrdoc.twitchtopcheers.managers;

import me.mrdoc.twitchtopcheers.classes.Cheering;
import me.mrdoc.twitchtopcheers.utils.PrintConsole;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created on 20-07-2017 for TwitchGOTH_TOPSumCheers.
 *
 * @author Doc
 */
public class FileCheerManagent {

    private static HashMap<Integer,File> filetops = new HashMap<>();

    public static void setTOP(HashMap<Integer,Cheering> top) {
        if(top.isEmpty()) {
            for(int pos = 1; pos <= ConfigManager.getConfig().getTop_size(); pos++) {
                setTOP(pos,null);
            }
            return;
        }
        for(int pos : top.keySet()) {
            setTOP(pos,top.get(pos));
        }
    }

    public static void setTOP(int pos, Cheering cheer) {
        try {
            FileWriter fw = new FileWriter(filetops.get(pos));
            BufferedWriter bw = new BufferedWriter(fw);

            String message = ConfigManager.getConfig().getEmpty_message();

            if(cheer != null) {
                message = ConfigManager.getConfig().getOut_message();
                message = message.replace("{pos}",Integer.toString(pos));
                message = message.replace("{username}",cheer.getUsername());
                message = message.replace("{cheers}",cheer.getTotalCheers() + "");
            }

            PrintConsole.sendInfo("Mensaje en top_" + pos + ".txt fue reemplazado a: " + message);

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

    private static File getFileTOP(int pos) {
        if(filetops.containsKey(pos)) {
            PrintConsole.sendError("Error al cargar archivo de TOP. La posicion es incorrecta.");
        }
        return filetops.get(pos);
    }

    public static void purgeFiles() {
        PrintConsole.sendInfo("Eliminando archivos txt de TOP.");
        try {
            FileUtils.deleteDirectory(new File("cheers"));
        } catch (IOException e) {
            PrintConsole.sendWarning("Ocurrio un problema al momento de purgar la carpeta de cheers");
            e.printStackTrace();
        }
    }

    private static void cheeckFile() {
        try {
            filetops.clear(); //Para evitar problemas borramos todo lo registrado en caso de.

            new File("cheers").mkdir(); //Creamos carpeta de txt

            for(int pos = 1; pos <= ConfigManager.getConfig().getTop_size(); pos++) {
                File filepos = new File("cheers/top_" + pos +".txt");
                if(filepos.createNewFile()) {
                    FileWriter fw = new FileWriter(filepos);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(ConfigManager.getConfig().getEmpty_message());
                    bw.flush();
                    bw.close();
                }
                filetops.put(pos,filepos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

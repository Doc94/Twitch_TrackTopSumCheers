package me.mrdoc.twitchtopcheers;

import me.mrdoc.twitchtopcheers.bot.TwitchBOT;
import me.mrdoc.twitchtopcheers.classes.Cheering;
import me.mrdoc.twitchtopcheers.db.SQLiteSystem;
import me.mrdoc.twitchtopcheers.managers.ConfigManager;
import me.mrdoc.twitchtopcheers.managers.DBCheerManagent;
import me.mrdoc.twitchtopcheers.managers.FileCheerManagent;
import me.mrdoc.twitchtopcheers.utils.PrintConsole;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created on 19-07-2017 for twitchtopcheers.
 *
 * @author Doc
 */
public class Core {

    public static TwitchBOT jaideBOTInstance;

    public static final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws IOException {
        PrintConsole.sendStatus("Iniciando sistema de TOP por session de cheers.");
        PrintConsole.sendStatus("Version: " + Core.class.getPackage().getImplementationVersion());

        loadConfig();
        loadFileTop();
        loadDB();

        Scanner scanner = new Scanner(System.in);
        PrintConsole.sendInfo("Antes de iniciar el BOT y el trackeo, debe escribir (Y/N) si desea o no reiniciar el TOP en el sistema");
        String response = scanner.nextLine();

        if(!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
            PrintConsole.sendError("Ha ingresado una respuesta que no se esperaba (ud no lee).");
            System.exit(0);
        }

        if(response.equalsIgnoreCase("y")) {
            PrintConsole.sendInfo("Borrando registros de TOP");
            DBCheerManagent.purgeTable();
            FileCheerManagent.purgeFiles();
            loadFileTop(); //reloadFile again
        } else {
            //Como no se borran registros, actualizamos los archivos con TOP.
            HashMap<Integer,Cheering> cheersTOP = DBCheerManagent.getTOPCheers(ConfigManager.getConfig().getTop_size());
            FileCheerManagent.setTOP(cheersTOP);
        }

        //testALL();

        loadBOT();

        Core.exec.schedule(() -> {
            PrintConsole.sendInfo("Sistema operando. trackeando mensajes en busca de cheers.");
        }, 5, TimeUnit.SECONDS);

    }

    private static void testALL() {
        PrintConsole.sendInfo("Corriendo test...");
        DBCheerManagent.addCheer("test",200);
        DBCheerManagent.addCheer("test2",800);
        DBCheerManagent.addCheer("test3",100);

        HashMap<Integer,Cheering> cheers = DBCheerManagent.getTOPCheers(ConfigManager.getConfig().getTop_size());

        PrintConsole.sendInfo("CANTIDAD TOP: " + cheers.size());

        FileCheerManagent.setTOP(DBCheerManagent.getTOPCheers(ConfigManager.getConfig().getTop_size()));

        //FileCheerManagent.setTOP(DBCheerManagent.getTOPCheers(1).get(0));
    }

    private static void loadConfig() {
        //Cargamos archivo de TOP
        PrintConsole.sendInfo("Iniciando carga del modulo de configuracion");
        ConfigManager.loadConfig();
        PrintConsole.sendInfo("Modulo cargado");
    }

    private static void loadFileTop() {
        //Cargamos archivo de TOP
        PrintConsole.sendInfo("Iniciando carga del modulo de registro de TOP en top.txt");
        FileCheerManagent.loadFileCheerManagent();
        PrintConsole.sendInfo("Modulo cargado");
    }

    private static void loadDB() {
        //Cargamos DB
        PrintConsole.sendInfo("Iniciando carga del modulo de registro de datos en DB");
        SQLiteSystem.run();
        PrintConsole.sendInfo("Modulo cargado");
    }

    private static void loadBOT() {
        //Cargamos BOT
        PrintConsole.sendInfo("Iniciando carga del modulo de trackeo usando BOT_IRC");
        jaideBOTInstance = new TwitchBOT();
        PrintConsole.sendInfo("Modulo cargado");
    }

}

package me.mrdoc.twitchtopcheers.utils;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

/**
 * Created on 20-07-2017 for TwitchGOTH_TOPSumCheers.
 *
 * @author Doc
 */
public class PrintConsole {

    private static final ColoredPrinter colorPrinter = new ColoredPrinter.Builder(1, false)
            .foreground(Ansi.FColor.WHITE)   //setting format
            .build();

    public static void sendWarning(String message) {
        colorPrinter.print("[Advertencia] ", Ansi.Attribute.BOLD, Ansi.FColor.YELLOW, Ansi.BColor.NONE);
        colorPrinter.print(message, Ansi.Attribute.NONE, Ansi.FColor.WHITE, Ansi.BColor.NONE);
        colorPrinter.println("");
        colorPrinter.clear();
    }

    public static void sendError(String message) {
        colorPrinter.print("[Error] ", Ansi.Attribute.BOLD, Ansi.FColor.RED, Ansi.BColor.NONE);
        colorPrinter.print(message, Ansi.Attribute.NONE, Ansi.FColor.RED, Ansi.BColor.NONE);
        colorPrinter.println("");
        colorPrinter.clear();
    }

    public static void sendInfo(String message) {
        colorPrinter.print("[Info] ", Ansi.Attribute.BOLD, Ansi.FColor.CYAN, Ansi.BColor.NONE);
        colorPrinter.print(message, Ansi.Attribute.NONE, Ansi.FColor.WHITE, Ansi.BColor.NONE);
        colorPrinter.println("");
        colorPrinter.clear();
    }

    public static void sendStatus(String message) {
        colorPrinter.print("[Status] ", Ansi.Attribute.BOLD, Ansi.FColor.MAGENTA, Ansi.BColor.NONE);
        colorPrinter.print(message, Ansi.Attribute.NONE, Ansi.FColor.WHITE, Ansi.BColor.NONE);
        colorPrinter.println("");
        colorPrinter.clear();
    }

}

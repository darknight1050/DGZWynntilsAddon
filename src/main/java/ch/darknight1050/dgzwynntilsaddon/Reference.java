package ch.darknight1050.dgzwynntilsaddon;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {

    public static final String MOD_ID = "dgzwynntilsaddon";
    public static final String NAME = "DGZWynntilsAddon";
    public static final String MINECRAFT_VERSIONS = "1.12,1.12.2";
    public static String VERSION = "";
    public static int BUILD_NUMBER = -1;
    public static final Logger LOGGER = LogManager.getFormatterLogger(MOD_ID);
    public static final ConfigManager CONFIG = new ConfigManager(new File("config", MOD_ID + ".cfg"));

}

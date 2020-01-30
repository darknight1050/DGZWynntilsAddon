package ch.darknight1050.dgzwynntilsaddon;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigManager {

    private Configuration config;
    private String uploadURL;
    private String downloadURL;
    private String password;

    public ConfigManager(File file) {
        config = new Configuration(file);
        config.load();
        uploadURL = config.getString("UploadURL", "server", "", "Upload URL (http://example.com/upload.php)");
        downloadURL = config.getString("DownloadURL", "server", "", "Download URL (http://example.com/download.php)");
        password = config.getString("Password", "server", "", "Password");
        config.save();
    }

    public String getUploadURL() {
        return uploadURL;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public String getPassword() {
        return password;
    }

}

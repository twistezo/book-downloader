package com.twistezo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;

/**
 * @author twistezo (09.03.2017)
 */

class Settings {
    private static Settings instance = null;
    private static final Logger LOG = LogManager.getLogger(Settings.class);
    private final String DRIVER_FILE_PATH = "src/main/resources/chromedriver.exe";
    private final String DRIVER_NAME = "webdriver.chrome.driver";
    private final String PAGE_URL = "https://www.packtpub.com/packt/offers/free-learning";
    private String login = ""; // = "luk89@outlook.com";
    private String pass = ""; // = "twistezo";
    private String downloadFolder = ""; // = "C:\\Users\\luk89\\Desktop\\Java\\Książki";
    private WebDriver driver;
    private Actions builder;

    private Settings() { }

    static Settings getInstance() {
        if(instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public void setUp() {
        File file = new File(DRIVER_FILE_PATH);
        System.setProperty(DRIVER_NAME, file.getAbsolutePath());
        HashMap<String, Object> chromeOptions = new HashMap<>();
        chromeOptions.put("profile.default_content_settings.popups", 0);
        chromeOptions.put("download.default_directory", downloadFolder);
        chromeOptions.put("download.prompt_for_download", false);
        chromeOptions.put("--no-startup-window", true);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromeOptions);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(cap);
        builder = new Actions(driver);
        LOG.info("Driver setUp complete.");
    }

    WebDriver getDriver() {
        return driver;
    }

    Actions getBuilder() {
        return builder;
    }

    String getPAGE_URL() {
        return PAGE_URL;
    }

    String getLogin() {
        return login;
    }

    String getPass() {
        return pass;
    }

    String getDownloadFolder() {
        return downloadFolder;
    }

    void setLogin(String login) {
        this.login = login;
    }

    void setPass(String pass) {
        this.pass = pass;
    }

    void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }
}
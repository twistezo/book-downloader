package com.twistezo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author twistezo (09.03.2017)
 */

class BookDownloader {
    private static WebDriver driver;
    private static BookDownloader instance = null;
    private static final Logger LOG = LogManager.getLogger(BookDownloader.class);
    private static String filePath;
    private static SiteNavigator siteNavigator;
    private static boolean run;

    private BookDownloader() {  }

    static BookDownloader getInstance() {
        if(instance == null) {
            instance = new BookDownloader();
        }
        return instance;
    }

    public void startDownload() {
        Settings settings = Settings.getInstance();
        filePath = settings.getDownloadFolder();
        settings.setUp();
        driver = settings.getDriver();
        BookDownloader app = new BookDownloader();
        siteNavigator = SiteNavigator.getInstance();

        if(!app.checkFileExists(siteNavigator.getBookTitle(), "pdf")){
            app.downloadPDF();
            app.waitFor(10000);
        } else {
            LOG.warn("PDF file already exists. It means that second file should also exists.");
            app.setEnd();
        }
        File lastModified = app.getLastModifiedFile(filePath);
        LOG.info("PDF file name before: " + lastModified);
        app.renameDownloadedFile(lastModified, siteNavigator.getBookTitle(), "pdf");
        lastModified = app.getLastModifiedFile(filePath);
        LOG.info("PDF file name after: " + lastModified);
        app.waitFor(10000);
        app.downloadEPUB();
        app.waitFor(10000);
        lastModified = app.getLastModifiedFile(filePath);
        LOG.info("EPUB file name before: " + lastModified);
        app.renameDownloadedFile(lastModified, siteNavigator.getBookTitle(), "epub");
        lastModified = app.getLastModifiedFile(filePath);
        LOG.info("EPUB file name after: " + lastModified);

        app.setEnd();
    }

    private void setEnd() {
        driver.close();
        LOG.warn("Shutdown app after 5 seconds.");
        waitFor(5000);
        System.exit(0);
    }

    private void downloadPDF() {
        driver.findElement(By.xpath("//a[contains(@href, '"+siteNavigator.getBookId()+"/pdf' )]")).click();
    }

    private void downloadEPUB() {
        driver.findElement(By.xpath("//a[contains(@href, '"+siteNavigator.getBookId()+"/epub' )]")).click();
    }

    private void waitFor(long ms) {
        try {
            LOG.info("Wait for " +ms+ "ms");
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long countFilesInDir() {
        long counter = 0;
        try {
            counter = Files.list(Paths.get(filePath)).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    private File getLastModifiedFile(String dir) {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }

    private void renameDownloadedFile(File old, String newName, String extension){
        File file = new File(filePath+ "\\" +newName+ "."+ extension);
        if(file.exists())
            System.out.println("Sorry this file already exist. There is nothing to do.");
        else {
            old.renameTo(file);
        }
    }

    private boolean checkFileExists(String newName, String extension) {
        File file = new File(filePath+ "\\" +newName+ "."+ extension);
        if(file.exists()) {
            return true;
        } else {
            return false;
        }
    }

}

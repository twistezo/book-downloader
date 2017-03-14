package com.twistezo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


/**
 * @author twistezo (13.03.2017)
 */
public class SceneController {
    @FXML private TextField login;
    @FXML private TextField password;
    @FXML private TextField downloadPath;
    @FXML private TextArea stackField;

    @FXML
    private void initialize() {
        Console console = new Console(stackField);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Settings settings = Settings.getInstance();
        settings.setLogin(login.getText());
        settings.setPass(password.getText());
        settings.setDownloadFolder(downloadPath.getText());
        new Thread(() -> {
            BookDownloader bookDownloader = BookDownloader.getInstance();
            bookDownloader.startDownload();
        }).start();
    }

    public static class Console extends OutputStream {
        private TextArea output;

        public Console(TextArea ta) {
            this.output = ta;
        }

        @Override
        public void write(final int i) throws IOException {
            Platform.runLater(new Runnable() {
                public void run() {
                    output.appendText(String.valueOf((char) i));
                }
            });
        }
    }
}

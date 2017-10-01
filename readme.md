## Book Downloader

### Description
Automatic tool for downloading packtpub.com daily free e-book (PDF, ePub)

### Tools
Java, JavaFX, Selenium, ChromeDriver

### Features
- automatic login to website
- automatic downloading daily e-books
- automatic renaming files
- PDF and ePub formats
- JVM console logs in JavaFX window
- storing user data in config.properties file

### Build/Run
```
1. Build:
   mvn jfx:jar
2. Run:
   cd target\jfx\app\ && start book-downloader-1.0-SNAPSHOT-jfx.jar
3. Change default config by hand (after first run):
   cd target\jfx\app\ && start config.properties
```

### Screenshots
<img src="http://i.imgur.com/47LB6R4.png">

### Update
19.06.2017 - project temporary not working - they add captcha :)
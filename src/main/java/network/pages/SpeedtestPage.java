package network.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class SpeedtestPage {

    private final SelenideElement acceptCookieButton = $("#onetrust-accept-btn-handler");
    private final SelenideElement startButton = $(".start-button");
    private final SelenideElement closeNotificationButton = $(".notification-dismiss.close-btn");

    private final SelenideElement downloadSpeedElement = $(".download-speed");
    private final SelenideElement uploadSpeedElement = $(".upload-speed");
    private final SelenideElement pingSpeedElement = $(".ping-speed");
    private final SelenideElement activeSpeedContainer = $(".result-container-speed.result-container-speed-active");

    private int reportCount = 0;

    public void startSpeedtestAndPrintResults() {
        acceptCookieButton.click();
        closeNotificationButton.click();

        startButton.click();
        waitForResults();
        printResults();
    }

    public String getDownloadSpeed() {
        return downloadSpeedElement.getText();
    }

    public String getUploadSpeed() {
        return uploadSpeedElement.getText();
    }

    public String getPingSpeed() {
        return pingSpeedElement.getText();
    }

    private void waitForResults() {
        System.out.println("Waiting for result...");
        activeSpeedContainer.shouldBe(Condition.visible, Duration.ofSeconds(120));
    }

    private void printResults() {
        String downloadSpeed = getDownloadSpeed();
        String uploadSpeed = getUploadSpeed();
        String pingSpeed = getPingSpeed();
        String formattedTime = getCurrentFormattedTime();
        String currentUrl = WebDriverRunner.url();

        reportCount++;

        System.out.println("_______________________________");
        System.out.println("Report " + reportCount + ".");
        System.out.println("Speedtest Results:");
        System.out.println("Time: " + formattedTime);
        System.out.println("Download Speed: " + downloadSpeed + " Mbps");
        System.out.println("Upload Speed: " + uploadSpeed + " Mbps");
        System.out.println("Ping: " + pingSpeed + " ms");
        System.out.println("URL: " + currentUrl);
    }

    public void writeReportToFile() {
        String formattedTime = getCurrentFormattedTime();
        String currentUrl = WebDriverRunner.url();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/resources/speedtest-report.txt", true))) {
            writer.write("№" + reportCount + " Speedtest Results:");
            writer.newLine();
            writer.write("Time: " + formattedTime);
            writer.newLine();
            writer.write("Download Speed: " + getDownloadSpeed() + " Mbps");
            writer.newLine();
            writer.write("Upload Speed: " + getUploadSpeed() + " Mbps");
            writer.newLine();
            writer.write("Ping: " + getPingSpeed() + " ms");
            writer.newLine();
            writer.write("URL: " + currentUrl);
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private String getCurrentFormattedTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd (EEE) HH:mm:ss", Locale.ENGLISH);
        return now.format(formatter);
    }
}
